package server.websocket;

import model.GameID;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    public void add(String username, String authToken, GameID gameID, Session session) {
        var connection = new Connection(username, authToken, gameID, session);
        connections.put(authToken, connection);
    }

    public void remove(String authToken) {
        connections.remove(authToken);
    }

    public void broadcast(String excludeAuth, String message, GameID gameID) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (!c.authToken.equals(excludeAuth)) {
                    if(c.gameID.equals(gameID)){
                        c.send(message);
                    }
                }
            } else {
                removeList.add(c);
            }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.authToken);
        }
    }
}