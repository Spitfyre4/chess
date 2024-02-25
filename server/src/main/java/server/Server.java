package server;

import com.google.gson.Gson;
import dataAccess.*;
import model.ErrorMessage;
import service.*;
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
        GameService myGameService = new GameService(userDatabase, authDatabase, gameDatabase);


        Spark.delete("/db", (req, res) ->
                (new ClearHandler(myClearService)).clear(req, res));

        Spark.post("/user", (req, res) ->
                (new UserHandler(myUserService)).register(req, res));

        Spark.post("/session", (req, res) ->
                (new UserHandler(myUserService)).login(req, res));

        Spark.post("/session", (req, res) ->
                (new UserHandler(myUserService)).logout(req, res));

        Spark.exception(DataAccessException.class, this::exceptionHandler);


        // Register your endpoints and handle exceptions here.

        Spark.awaitInitialization();
        return Spark.port();
    }

    private void exceptionHandler(DataAccessException e, Request req, Response res) {
        res.status(e.getStatusCode());
        res.body(new Gson().toJson(new ErrorMessage(e.getMessage())));
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
