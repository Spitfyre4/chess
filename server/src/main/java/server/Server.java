package server;

import com.google.gson.Gson;
import dataAccess.*;
import model.ErrorMessage;
import server.websocket.WebSocketHandler;
import service.*;
import spark.*;

public class Server {

    public static void main(String[] args) {
        new Server().run(8080);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        try {
            UserDAO userDatabase = new SqlUserDAO();
            AuthDAO authDatabase = new SqlAuthDAO();
            GameDAO gameDatabase = new SqlGameDAO();
            ClearService myClearService = new ClearService(userDatabase, authDatabase, gameDatabase);
            UserService myUserService = new UserService(userDatabase, authDatabase);
            GameService myGameService = new GameService(userDatabase, authDatabase, gameDatabase);
            WebSocketHandler webSocketHandler = new WebSocketHandler(myGameService);


            Spark.webSocket("/connect", webSocketHandler);


            Spark.delete("/db", (req, res) ->
                    (new ClearHandler(myClearService)).clear(req, res));

            Spark.post("/user", (req, res) ->
                    (new UserHandler(myUserService)).register(req, res));

            Spark.post("/session", (req, res) ->
                    (new UserHandler(myUserService)).login(req, res));

            Spark.delete("/session", (req, res) ->
                    (new UserHandler(myUserService)).logout(req, res));

            Spark.get("/game", (req, res) ->
                    (new GameHandler(myGameService)).listGames(req, res));

            Spark.post("/game", (req, res) ->
                    (new GameHandler(myGameService)).createGame(req, res));

            Spark.put("/game", (req, res) ->
                    (new GameHandler(myGameService)).joinGame(req, res));


            Spark.exception(DataAccessException.class, this::exceptionHandler);


            // Register your endpoints and handle exceptions here.

            Spark.awaitInitialization();

        }
        catch (DataAccessException ex){
            System.out.println("Error loading server");
        }
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
