package server.websocket;

import model.GameID;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class Connection {
    public Session session;
    public String authToken;
    public int gameID;

    public Connection(String authToken, int gameID, Session session) {
        this.session = session;
        this.authToken = authToken;
        this.gameID = gameID;
    }

    public void send(String msg) throws IOException {
        session.getRemote().sendString(msg);
    }
}