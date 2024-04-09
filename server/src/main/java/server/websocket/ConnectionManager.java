package server.websocket;

import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    public void add(String authToken, int gameID, Session session) {
        var connection = new Connection(authToken, gameID, session);
        connections.put(authToken, connection);
    }

    public void remove(String authToken) {
        connections.remove(authToken);
    }

    public void broadcast(String excludeAuth, ServerMessage message, int gameID) throws IOException {
        System.out.println("in broadcast");
        var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            System.out.println("- In for loop");
            if (c.session.isOpen()) {
                System.out.println("- In first if loop(sSession is open)");
                if (!c.authToken.equals(excludeAuth)) {
                    System.out.println("- In second if loop(Excluded auth)");
                    if(c.gameID == gameID){
                        System.out.println("- In third if loop(GameId is equal)");
                        System.out.println("- In third if loop(GameId is equal)");
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