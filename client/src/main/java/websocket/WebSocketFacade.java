package websocket;

import chess.ChessMove;
import com.google.gson.Gson;
import exception.*;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
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
                        case NOTIFICATION -> gameplay.send(new Gson().fromJson(message, NotificationMessage.class));
                        case ERROR -> System.out.println("Error");
                        case LOAD_GAME -> loadGame(new Gson().fromJson(message, LoadGameMessage.class));
                    }
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ServerException(ex.getMessage(), 500);
        }
    }

    private void loadGame(LoadGameMessage loadGameMessage) {
        gameplay.updateGame(loadGameMessage.game);
        gameplay.printBoard(playerColor);
    }

    public void reprintBoard(){
        gameplay.printBoard(playerColor);
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }


    public void resign(String authString, int gameID, String username) throws ServerException {
        try {
            var req = new ResignCommand(authString, gameID, username);
            this.session.getBasicRemote().sendText(new Gson().toJson(req));
        } catch (IOException ex) {
            throw new ServerException(ex.getMessage(), 500);
        }
    }

    public void leave(String authString, int gameID, String username) throws ServerException {
        try {
            var req = new LeaveCommand(authString, gameID, username);
            this.session.getBasicRemote().sendText(new Gson().toJson(req));
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