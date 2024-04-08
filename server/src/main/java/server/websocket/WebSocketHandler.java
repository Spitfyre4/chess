package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;

public class WebSocketHandler {
    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand cmd = new Gson().fromJson(message, UserGameCommand.class);
        switch (cmd.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(cmd.getAuthString());
            case JOIN_OBSERVER -> joinObserver(cmd.getAuthString());
            case MAKE_MOVE -> makeMove(cmd.getAuthString());
            case LEAVE -> leave(cmd.getAuthString());
            case RESIGN -> resign(cmd.getAuthString());
        }
    }

    private void resign(String authString) {
    }

    private void leave(String authString) {
    }

    private void makeMove(String authString) {
    }

    private void joinObserver(String authString) {
    }

    private void joinPlayer(String authString) {
        connections.add();
    }
}
