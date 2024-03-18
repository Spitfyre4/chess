package ui;


import model.UserData;
import server.ServerFacade;
import server.serverException;

public class PreLoginClient {

    public final ServerFacade server;

    public PreLoginClient(String url){
        server = new ServerFacade(url);
    }

    public Object register(UserData user) throws serverException {
        var path = "/user";
        return server.makeRequest("POST", path, user, UserData.class);
    }
}
