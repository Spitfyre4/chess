package service;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.*;
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
        return new Gson().toJson(new GamesData(myGameService.listGames(authToken)));
    }

    public Object createGame(Request req, Response res) throws DataAccessException {
        var authToken = req.headers("authorization");
        var game = new Gson().fromJson(req.body(), GameData.class);

        res.status(200);
        return new Gson().toJson(new GameID(myGameService.createGame(game.gameName(), authToken)));
    }

    public Object joinGame(Request req, Response res) throws DataAccessException {
        var authToken = req.headers("authorization");
        var gameReq = new Gson().fromJson(req.body(), JoinGameReq.class);

        myGameService.joinGame(authToken, gameReq.playerColor(), gameReq.gameID());
        res.status(200);
        return "{}";


    }
}
