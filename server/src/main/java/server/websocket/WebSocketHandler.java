package server.websocket;

import com.google.gson.Gson;
import exception.ServerException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.userCommands.*;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {
    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        System.out.println("in handler");
        UserGameCommand cmd = new Gson().fromJson(message, UserGameCommand.class);
        switch (cmd.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(new Gson().fromJson(message, JoinPlayerCommand.class));
            case JOIN_OBSERVER -> joinObserver(new Gson().fromJson(message, JoinObserverCommand.class));
            case MAKE_MOVE -> makeMove(new Gson().fromJson(message, MakeMoveCommand.class));
            case LEAVE -> leave(new Gson().fromJson(message, LeaveCommand.class));
            case RESIGN -> resign(new Gson().fromJson(message, ResignCommand.class));
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
        System.out.println("in joinPlayer");
        connections.broadcast(cmd.getAuthString(), "joinPlayer", cmd.gameID);
    }
}
