package websocket;

import com.google.gson.Gson;
import exception.*;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;


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

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    switch (serverMessage.getServerMessageType()) {
                        case NOTIFICATION -> System.out.println("Notification");
                        case ERROR -> System.out.println("Notification");
                        case LOAD_GAME -> System.out.println("Notification");
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

    public void resign(String authString) throws ServerException {
        try {
            var req = new UserGameCommand(authString);
            req.setCommandType(UserGameCommand.CommandType.RESIGN);
            this.session.getBasicRemote().sendText(new Gson().toJson(req));
        } catch (IOException ex) {
            throw new ServerException(ex.getMessage(), 500);
        }
    }

    public void leave(String authString) throws ServerException {
        try {
            var req = new UserGameCommand(authString);
            req.setCommandType(UserGameCommand.CommandType.LEAVE);
            this.session.getBasicRemote().sendText(new Gson().toJson(req));
        } catch (IOException ex) {
            throw new ServerException(ex.getMessage(), 500);
        }
    }

    public void makeMove(String authString) throws ServerException {
        try {
            var req = new UserGameCommand(authString);
            req.setCommandType(UserGameCommand.CommandType.MAKE_MOVE);
            this.session.getBasicRemote().sendText(new Gson().toJson(req));
        } catch (IOException ex) {
            throw new ServerException(ex.getMessage(), 500);
        }
    }

    public void joinObserver(String authString) throws ServerException {
        try {
            var req = new UserGameCommand(authString);
            req.setCommandType(UserGameCommand.CommandType.JOIN_OBSERVER);
            this.session.getBasicRemote().sendText(new Gson().toJson(req));
        } catch (IOException ex) {
            throw new ServerException(ex.getMessage(), 500);
        }
    }

    public void joinPlayer(String authString) throws ServerException {
        try {
            var req = new UserGameCommand(authString);
            req.setCommandType(UserGameCommand.CommandType.JOIN_PLAYER);
            this.session.getBasicRemote().sendText(new Gson().toJson(req));
        } catch (IOException ex) {
            throw new ServerException(ex.getMessage(), 500);
        }
    }


}