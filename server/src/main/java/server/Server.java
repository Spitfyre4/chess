package server;

import dataAccess.*;
import service.ClearHandler;
import service.ClearService;
import service.UserHandler;
import service.UserService;
import spark.*;

public class Server {

    public static void main(String[] args) {
        new Server().run(8080);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        UserDAO userDatabase = new MemoryUserDAO();
        AuthDAO authDatabase = new MemoryAuthDAO();
        GameDAO gameDatabase = new MemoryGameDAO();
        ClearService myClearService = new ClearService(userDatabase, authDatabase, gameDatabase);
        UserService myUserService = new UserService(userDatabase, authDatabase);


        Spark.delete("/db", (req, res) ->
                (new ClearHandler(myClearService)).clear(req, res));

        Spark.post("/user", (req, res) ->
                (new UserHandler(myUserService)).register(req, res));


        // Register your endpoints and handle exceptions here.

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
