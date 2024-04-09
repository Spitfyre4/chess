package server.websocket;

import com.google.gson.Gson;
import exception.ServerException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import webSocketMessages.userCommands.*;

import java.io.IOException;

public class WebSocketHandler {
    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand cmd = new Gson().fromJson(message, UserGameCommand.class);
        switch (cmd.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer((JoinPlayerCommand)cmd);
            case JOIN_OBSERVER -> joinObserver((JoinObserverCommand)cmd);
            case MAKE_MOVE -> makeMove((MakeMoveCommand)cmd);
            case LEAVE -> leave((LeaveCommand)cmd);
            case RESIGN -> resign((ResignCommand)cmd);
        }
    }

    private void resign(ResignCommand cmd) throws IOException {
        connections.broadcast(cmd.getAuthString(), "resign", cmd.gameID);
    }

    private void leave(LeaveCommand cmd) throws IOException {
        connections.broadcast(cmd.getAuthString(), "leave", cmd.gameID);
    }

    private void makeMove(MakeMoveCommand cmd) throws IOException {
        connections.broadcast(cmd.getAuthString(), "makeMove", cmd.gameID);
    }


    private void joinObserver(JoinObserverCommand cmd) throws IOException {
        connections.broadcast(cmd.getAuthString(), "joinObs", cmd.gameID);
    }

    private void joinPlayer(JoinPlayerCommand cmd) throws IOException {
        connections.broadcast(cmd.getAuthString(), "joinPlayer", cmd.gameID);
    }
}
