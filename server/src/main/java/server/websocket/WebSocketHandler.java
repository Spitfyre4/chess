package server.websocket;

import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import exception.ServerException;
import model.GameData;
import model.GameID;
import model.GamesData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.GameHandler;
import service.GameService;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import java.io.IOException;
import java.util.Collection;

@WebSocket
public class WebSocketHandler {
    private final ConnectionManager connections = new ConnectionManager();

    private GameService gameService;

    public WebSocketHandler(GameService myGameService) {
        this.gameService = myGameService;
    }


    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, DataAccessException, InvalidMoveException {
//        System.out.println("in wsHandler");
        UserGameCommand cmd = new Gson().fromJson(message, UserGameCommand.class);
        switch (cmd.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(new Gson().fromJson(message, JoinPlayerCommand.class), session);
            case JOIN_OBSERVER -> joinObserver(new Gson().fromJson(message, JoinObserverCommand.class), session);
            case MAKE_MOVE -> makeMove(new Gson().fromJson(message, MakeMoveCommand.class));
            case LEAVE -> leave(new Gson().fromJson(message, LeaveCommand.class));
            case RESIGN -> resign(new Gson().fromJson(message, ResignCommand.class));
        }
    }

    private void resign(ResignCommand cmd) throws IOException {
        String op = "WHITE";
        if (cmd.playerColor.equals("WHITE")) {
            op = "BLACK";
        }
        String phrase = cmd.username + " has resigned\n" + op + " has won!";
        NotificationMessage message =
                new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, phrase);
        connections.broadcast(cmd.getAuthString(), message, cmd.gameID);
        LoadGameMessage message2 =
                new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, null, true);
        connections.broadcast(cmd.getAuthString(), message2, cmd.gameID);

    }

    private void leave(LeaveCommand cmd) throws IOException {
        connections.remove(cmd.getAuthString());
        String phrase = cmd.username + " left the game";
        NotificationMessage message =
                new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, phrase);
        connections.broadcast(cmd.getAuthString(), message, cmd.gameID);
    }

    private void makeMove(MakeMoveCommand cmd) throws IOException, DataAccessException, InvalidMoveException {
        GamesData games = new GamesData(gameService.listGames(cmd.getAuthString()));
        GameData game = games.getGame(new GameID(cmd.gameID));
        game.game().makeMove(cmd.move);
        gameService.makeMove(game.game(), cmd.gameID);
        LoadGameMessage message =
                new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game.game(), false);
        System.out.println("got the move: " + cmd.move);
        connections.broadcast(cmd.getAuthString(), message, cmd.gameID);
        connections.sendMessage(cmd.getAuthString(), message);
    }


    private void joinObserver(JoinObserverCommand cmd, Session session) throws IOException, DataAccessException {
        connections.add(cmd.getAuthString(), cmd.gameID, session);
        String phrase = cmd.username + " joined as an observer";
        NotificationMessage message =
                new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, phrase);
        connections.broadcast(cmd.getAuthString(), message, cmd.gameID);

        GamesData games = new GamesData(gameService.listGames(cmd.getAuthString()));
        GameData game = games.getGame(new GameID(cmd.gameID));
        LoadGameMessage gMessage =
                new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game.game(), false);
        connections.sendMessage(cmd.getAuthString(), gMessage);
    }

    private void joinPlayer(JoinPlayerCommand cmd, Session session) throws IOException, DataAccessException {
//        System.out.println("in joinPlayer");
        connections.add(cmd.getAuthString(), cmd.gameID, session);
        String phrase = cmd.username + " joined as " + cmd.playerColor + " player";
        NotificationMessage message =
                new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, phrase);
        connections.broadcast(cmd.getAuthString(), message, cmd.gameID);

        GamesData games = new GamesData(gameService.listGames(cmd.getAuthString()));
        GameData game = games.getGame(new GameID(cmd.gameID));
        LoadGameMessage gMessage =
                new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game.game(), false);
        connections.sendMessage(cmd.getAuthString(), gMessage);
    }
}
