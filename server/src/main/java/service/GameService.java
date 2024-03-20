package service;

import chess.ChessGame;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import model.GameData;
import model.UserData;

import java.util.Collection;

public class GameService {

    public final UserDAO userDatabase;
    public final AuthDAO authDatabase;
    public final GameDAO gameDatabase;
    private int nextId = 1;

    public GameService(UserDAO userDatabase, AuthDAO authDatabase, GameDAO gameDatabase){
        this.userDatabase = userDatabase;
        this.authDatabase = authDatabase;
        this.gameDatabase = gameDatabase;
    }

    public Collection<GameData> listGames(String authToken) throws DataAccessException {
        authDatabase.getAuth(authToken);
        return gameDatabase.listGames();
    }
    public int createGame(String name, String authToken) throws DataAccessException{
        authDatabase.getAuth(authToken);
        nextId = gameDatabase.getGameID();
        nextId++;
        gameDatabase.createGame(new GameData(nextId, null, null, name, new ChessGame()));

        return nextId;
    }
    public void joinGame(String authToken, String playerColor, int gameID) throws DataAccessException{
        gameDatabase.getGame(gameID); //checks if game exists
        String username = authDatabase.getAuth(authToken).username();
        if(playerColor != null){
            gameDatabase.joinGame(username, playerColor, gameID);
        }
        //add as observer if null
    }



}
