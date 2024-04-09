package server.websocket;

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
    public void onMessage(Session session, String message) throws IOException, DataAccessException {
        System.out.println("in handler");
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
        NotificationMessage message =
                new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, "resign");
        connections.broadcast(cmd.getAuthString(), message, cmd.gameID);
    }

    private void leave(LeaveCommand cmd) throws IOException {
        connections.remove(cmd.getAuthString());
        NotificationMessage message =
                new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, "leave");
        connections.broadcast(cmd.getAuthString(), message, cmd.gameID);
    }

    private void makeMove(MakeMoveCommand cmd) throws IOException {
        LoadGameMessage message =
                new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, null);
        connections.broadcast(cmd.getAuthString(), message, cmd.gameID);
    }


    private void joinObserver(JoinObserverCommand cmd, Session session) throws IOException, DataAccessException {
        connections.add(cmd.getAuthString(), cmd.gameID, session);
        NotificationMessage message =
                new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, "joinObs");
        connections.broadcast(cmd.getAuthString(), message, cmd.gameID);

        GamesData games = new GamesData(gameService.listGames(cmd.getAuthString()));
        GameData game = games.getGame(new GameID(cmd.gameID));
        assert game != null;
        LoadGameMessage gMessage =
                new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game.game());
        connections.sendMessage(cmd.getAuthString(), gMessage);
    }

    private void joinPlayer(JoinPlayerCommand cmd, Session session) throws IOException, DataAccessException {
        System.out.println("in joinPlayer");
        connections.add(cmd.getAuthString(), cmd.gameID, session);
        NotificationMessage message =
                new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, "joinPlayer");
        connections.broadcast(cmd.getAuthString(), message, cmd.gameID);

        GamesData games = new GamesData(gameService.listGames(cmd.getAuthString()));
        System.out.println("Retrieving game: " + cmd.gameID);
        GameData game = games.getGame(new GameID(cmd.gameID));
        System.out.println("got game: " + game.gameID());
        LoadGameMessage gMessage =
                new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game.game());
        connections.sendMessage(cmd.getAuthString(), gMessage);
    }
}
