package server.websocket;

import model.GameID;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class Connection {
    public String username;
    public Session session;
    public String authToken;
    public GameID gameID;

    public Connection(String username, String authToken, GameID gameID, Session session) {
        this.username = username;
        this.session = session;
        this.authToken = authToken;
        this.gameID = gameID;
    }

    public void send(String msg) throws IOException {
        session.getRemote().sendString(msg);
    }
}