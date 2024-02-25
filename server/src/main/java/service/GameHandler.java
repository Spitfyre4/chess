package service;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.GameData;
import model.GameID;
import model.UserData;
import spark.Request;
import spark.Response;

public class GameHandler {

    public final GameService myGameService;

    public GameHandler(GameService myGameService){
        this.myGameService = myGameService;
    }

    public Object listGames(Request req, Response res) throws DataAccessException {
        var authToken = req.headers("authorization");
        res.status(200);
        return new Gson().toJson(myGameService.listGames(authToken));
    }

    public Object createGame(Request req, Response res) throws DataAccessException {
        var authToken = req.headers("authorization");
        var game = new Gson().fromJson(req.body(), GameData.class);

        res.status(200);
        return new Gson().toJson(new GameID(myGameService.createGame(game.gameName(), authToken)));
    }

    public void joinGame(Request req, Response res) throws DataAccessException {
        var authToken = req.headers("authorization");
        var game = new Gson().fromJson(req.body(), GameData.class);

        res.status(200);
        myGameService.joinGame(authToken, game.gameID());

    }
}
