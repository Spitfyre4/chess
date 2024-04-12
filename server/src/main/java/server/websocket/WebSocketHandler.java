package server.websocket;

import chess.ChessGame;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.AuthData;
import model.GameData;
import model.GameID;
import model.GamesData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.GameService;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import java.io.IOException;
import java.util.Objects;

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
        try {
            GamesData games = new GamesData(gameService.listGames(cmd.getAuthString()));
            GameData game = games.getGame(new GameID(cmd.gameID));

            AuthData auth = gameService.authDatabase.getAuth(cmd.getAuthString());
            System.out.print("");
            String username = auth.username();
            if (!Objects.equals(username, game.whiteUsername()) && !Objects.equals(username, game.blackUsername())) {
                Error message =
                        new Error(ServerMessage.ServerMessageType.ERROR, "Error: unauthorized");
                connections.sendMessage(cmd.getAuthString(), message);
                return;
            }

            if(game.game().gameOver()){
                Error message =
                        new Error(ServerMessage.ServerMessageType.ERROR, "Error: Game already over");
                connections.sendMessage(cmd.getAuthString(), message);
                return;
            }

            game.game().forceGameover();

            gameService.gameDatabase.updateGame(game.game(), game.gameID());

            String op;
            if(game.blackUsername().equals(auth.username())){
                op = "WHITE";
            }
            else {
                op = "BLACK";
            }

            String phrase = cmd.username + " has resigned\n" + op + " has won!";
            Notification message =
                    new Notification(ServerMessage.ServerMessageType.NOTIFICATION, phrase);
            connections.broadcast("everyone", message, cmd.gameID);
        }
        catch (Exception ex){
            Error message =
                    new Error(ServerMessage.ServerMessageType.ERROR,"Error: " + ex.getMessage());
            connections.sendMessage(cmd.getAuthString(), message);
        }

    }

    private void leave(LeaveCommand cmd) throws IOException {
        try {
            connections.remove(cmd.getAuthString());
            String phrase = cmd.username + " left the game";
            Notification message =
                    new Notification(ServerMessage.ServerMessageType.NOTIFICATION, phrase);
            connections.broadcast(cmd.getAuthString(), message, cmd.gameID);
        }
        catch (Exception ex){
            Error message =
                    new Error(ServerMessage.ServerMessageType.ERROR,"Error: " + ex.getMessage());
            connections.sendMessage(cmd.getAuthString(), message);
        }

    }

    private void makeMove(MakeMoveCommand cmd) throws IOException, DataAccessException, InvalidMoveException {
        try {
            GamesData games = new GamesData(gameService.listGames(cmd.getAuthString()));
            GameData game = games.getGame(new GameID(cmd.gameID));

            AuthData auth = gameService.authDatabase.getAuth(cmd.getAuthString());
            String username = auth.username();
            System.out.print("");
            if (!Objects.equals(username, game.whiteUsername()) && !Objects.equals(username, game.blackUsername())) {
                Error message =
                        new Error(ServerMessage.ServerMessageType.ERROR, "Error: unauthorized");
                connections.sendMessage(cmd.getAuthString(), message);
                return;
            }
            System.out.print("");

            if(game.game().gameOver()){
                Error message =
                        new Error(ServerMessage.ServerMessageType.ERROR, "Error: Game already over");
                connections.sendMessage(cmd.getAuthString(), message);
                return;
            }


            String usernameToCheck = game.whiteUsername();
            if(game.game().getTeamTurn() == ChessGame.TeamColor.BLACK){
                usernameToCheck = game.blackUsername();
            }
            if(!Objects.equals(auth.username(), usernameToCheck)){
                Error message =
                        new Error(ServerMessage.ServerMessageType.ERROR, "Error: unauthorized");
                connections.sendMessage(cmd.getAuthString(), message);
                return;
            }


            game.game().makeMove(cmd.move);
            gameService.makeMove(game.game(), cmd.gameID);

            String moveString = auth.username() + " moved " + cmd.move.letterMove();
            Notification message2 =
                    new Notification(ServerMessage.ServerMessageType.NOTIFICATION, moveString);
            connections.broadcast(cmd.getAuthString(), message2, cmd.gameID);

            LoadGame message =
                    new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, game.game(), false);
            connections.broadcast(cmd.getAuthString(), message, cmd.gameID);
            connections.sendMessage(cmd.getAuthString(), message);

            String winString = checkWin(game);
            if (winString != null) {
                Notification message3 =
                        new Notification(ServerMessage.ServerMessageType.NOTIFICATION, winString);
                connections.broadcast("everyone", message3, cmd.gameID);
            } else {
                checkCheck(game);
            }
        }
        catch (Exception ex){
            Error message =
                    new Error(ServerMessage.ServerMessageType.ERROR,"Error: " + ex.getMessage());
            connections.sendMessage(cmd.getAuthString(), message);
        }
    }

    public String checkWin(GameData gameData) {
        ChessGame.TeamColor blackUser = ChessGame.TeamColor.BLACK;
        ChessGame.TeamColor whiteUser = ChessGame.TeamColor.WHITE;
        ChessGame game = gameData.game();

        if(game.isInCheckmate(blackUser)){
           return gameData.blackUsername() + " is in checkmate\n" + gameData.whiteUsername() + " has won!\nPress anything to continue";
        }

        if(game.isInCheckmate(whiteUser)){
            return gameData.whiteUsername() + " is in checkmate\n" + gameData.blackUsername() + " has won!\nPress anything to continue";
        }

        if(game.isInStalemate(blackUser) || game.isInStalemate(whiteUser)){
            return "Game is in Stalemate, nobody wins\nPress anything to continue";
        }
        return null;
    }

    private void checkCheck(GameData gameData) throws IOException {
        ChessGame game = gameData.game();
        String phrase;
        if(game.isInCheck(ChessGame.TeamColor.WHITE)){
            phrase = gameData.whiteUsername() + " is in check!";
            Notification message =
                    new Notification(ServerMessage.ServerMessageType.NOTIFICATION, phrase);
            connections.broadcast("everyone", message, gameData.gameID());
        }
        else if(game.isInCheck(ChessGame.TeamColor.BLACK)){
            phrase = gameData.blackUsername() + " is in check!";
            Notification message =
                    new Notification(ServerMessage.ServerMessageType.NOTIFICATION, phrase);
            connections.broadcast("everyone", message, gameData.gameID());
        }

    }


    private void joinObserver(JoinObserverCommand cmd, Session session) throws IOException, DataAccessException {
        connections.add(cmd.getAuthString(), cmd.gameID, session);

        try {

            GamesData games = new GamesData(gameService.listGames(cmd.getAuthString()));
            GameData game = games.getGame(new GameID(cmd.gameID));

            String phrase = cmd.username + " joined as an observer";
            Notification message =
                    new Notification(ServerMessage.ServerMessageType.NOTIFICATION, phrase);
            connections.broadcast(cmd.getAuthString(), message, cmd.gameID);

            LoadGame gMessage =
                    new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, game.game(), false);
            connections.sendMessage(cmd.getAuthString(), gMessage);
        }
        catch (Exception ex){
            Error message =
                    new Error(ServerMessage.ServerMessageType.ERROR,"Error: " + ex.getMessage());
            connections.sendMessage(cmd.getAuthString(), message);
        }
    }

    private void joinPlayer(JoinPlayerCommand cmd, Session session) throws IOException, DataAccessException {
//        System.out.println("in joinPlayer");
        connections.add(cmd.getAuthString(), cmd.gameID, session);

        try {
            GamesData games = new GamesData(gameService.listGames(cmd.getAuthString()));
            GameData game = games.getGame(new GameID(cmd.gameID));
            AuthData auth = gameService.authDatabase.getAuth(cmd.getAuthString());


            String username = null;
            if (cmd.playerColor.equals("WHITE")) {
                username = game.whiteUsername();
            }
            if (cmd.playerColor.equals("BLACK")) {
                username = game.blackUsername();
            }

            if (username == null || !username.equals(auth.username())) {
                Error message =
                        new Error(ServerMessage.ServerMessageType.ERROR, "Error: unauthorized");
                connections.sendMessage(cmd.getAuthString(), message);
                return;
            }

            String phrase = cmd.username + " joined as " + cmd.playerColor + " player";
            Notification message =
                    new Notification(ServerMessage.ServerMessageType.NOTIFICATION, phrase);
            connections.broadcast(cmd.getAuthString(), message, cmd.gameID);

            LoadGame gMessage =
                    new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, game.game(), false);
            connections.sendMessage(cmd.getAuthString(), gMessage);
        }
        catch (Exception ex){
            Error message =
                    new Error(ServerMessage.ServerMessageType.ERROR,"Error: " + ex.getMessage());
            connections.sendMessage(cmd.getAuthString(), message);
        }
    }

}
