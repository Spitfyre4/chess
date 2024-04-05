package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;

public class WebSocketHandler {
    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        ServerMessage sMessage = new Gson().fromJson(message, ServerMessage.class);
        switch (sMessage.getServerMessageType()) {
            case LOAD_GAME -> loadGame();
            case ERROR -> error();
            case NOTIFICATION -> notifaction();
        }
    }
}
