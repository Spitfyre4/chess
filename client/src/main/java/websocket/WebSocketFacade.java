package websocket;

import chess.ChessMove;
import com.google.gson.Gson;
import exception.*;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;


import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {

    Session session;

    private GameplayHandler gameplay;
    String playerColor = null;

    public WebSocketFacade(String url, GameplayHandler gameplay) throws ServerException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);
            this.gameplay = gameplay;

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    switch (serverMessage.getServerMessageType()) {
                        case NOTIFICATION -> gameplay.send(new Gson().fromJson(message, Notification.class));
                        case ERROR -> gameplay.error(new Gson().fromJson(message, Error.class));
                        case LOAD_GAME -> loadGame(new Gson().fromJson(message, LoadGame.class));
                    }
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ServerException(ex.getMessage(), 500);
        }
    }

    private void loadGame(LoadGame loadGameMessage) {
        if (loadGameMessage.resign){
            gameplay.endGame();
            System.out.println("Press anything to continue");
            return;
        }
        gameplay.updateGame(loadGameMessage.game);
        gameplay.printBoard(playerColor);
        gameplay.checkWin();
    }

    public void reprintBoard(){
        String color = playerColor;
        if (playerColor == null){
            color = "WHITE";
        }
        gameplay.printBoard(color);
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }


    public void resign(String authString, int gameID, String username, String playerColor) throws ServerException {
        try {
            var req = new ResignCommand(authString, gameID, username, playerColor);
            this.session.getBasicRemote().sendText(new Gson().toJson(req));
        } catch (IOException ex) {
            throw new ServerException(ex.getMessage(), 500);
        }
    }

    public void leave(String authString, int gameID, String username) throws ServerException {
        try {
            var req = new LeaveCommand(authString, gameID, username);
            this.session.getBasicRemote().sendText(new Gson().toJson(req));
            System.out.print("");
        } catch (IOException ex) {
            throw new ServerException(ex.getMessage(), 500);
        }
    }

    public void makeMove(String authString, int gameID, String playerColor, ChessMove move) throws ServerException {
        try {
            var req = new MakeMoveCommand(authString, gameID, playerColor, move);
            this.session.getBasicRemote().sendText(new Gson().toJson(req));
        } catch (IOException ex) {
            throw new ServerException(ex.getMessage(), 500);
        }
    }

    public void joinObserver(String authString, int gameID, String username) throws ServerException {
        try {
            var req = new JoinObserverCommand(authString, gameID, username);
            System.out.print("");
            this.session.getBasicRemote().sendText(new Gson().toJson(req));
        } catch (IOException ex) {
            throw new ServerException(ex.getMessage(), 500);
        }

    }

    public void joinPlayer(String authString, int gameID, String playerColor, String username) throws ServerException {
        this.playerColor = playerColor;
        try {
            var req = new JoinPlayerCommand(authString, gameID, playerColor, username);
            this.session.getBasicRemote().sendText(new Gson().toJson(req));
        } catch (IOException ex) {
            throw new ServerException(ex.getMessage(), 500);
        }
    }


}