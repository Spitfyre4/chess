package server.websocket;

import com.google.gson.Gson;
import model.GameID;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;

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

    public void send(ServerMessage msg) throws IOException {
        System.out.println("In Send");
        session.getRemote().sendString(new Gson().toJson(msg));
    }
}