package websocket;

import chess.ChessMove;
import com.google.gson.Gson;
import exception.*;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;


import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {

    Session session;

    public WebSocketFacade(String url) throws ServerException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);
            System.out.println("Websocket connected to server");

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    System.out.println("In Facade onMessage");
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    switch (serverMessage.getServerMessageType()) {
                        case NOTIFICATION -> System.out.println("Notification");
                        case ERROR -> System.out.println("Error");
                        case LOAD_GAME -> System.out.println("Load game");
                    }
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ServerException(ex.getMessage(), 500);
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }


    public void resign(String authString, int gameID) throws ServerException {
        try {
            var req = new ResignCommand(authString, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(req));
        } catch (IOException ex) {
            throw new ServerException(ex.getMessage(), 500);
        }
    }

    public void leave(String authString, int gameID) throws ServerException {
        try {
            var req = new LeaveCommand(authString, gameID);
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

    public void joinObserver(String authString, int gameID) throws ServerException {
        try {
            var req = new JoinObserverCommand(authString, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(req));
        } catch (IOException ex) {
            throw new ServerException(ex.getMessage(), 500);
        }
    }

    public void joinPlayer(String authString, int gameID, String playerColor) throws ServerException {
        try {
            var req = new JoinPlayerCommand(authString, gameID, playerColor);
            System.out.println("Sending joinPlayer Websocket from client to server");
            this.session.getBasicRemote().sendText(new Gson().toJson(req));
        } catch (IOException ex) {
            throw new ServerException(ex.getMessage(), 500);
        }
    }


}