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

    public final UserDAO UserDatabase;
    public final AuthDAO AuthDatabase;
    public final GameDAO GameDatabase;
    private int nextId = 1;

    public GameService(UserDAO UserDatabase, AuthDAO AuthDatabase, GameDAO GameDatabase){
        this.UserDatabase = UserDatabase;
        this.AuthDatabase = AuthDatabase;
        this.GameDatabase = GameDatabase;


    }
    public Collection<GameData> listGames() throws DataAccessException {
        return GameDatabase.listGames();
    }
    public int createGame(String name) throws DataAccessException{
        GameDatabase.createGame(new GameData(nextId, null, null, name, new ChessGame()));
        int output = nextId;
        nextId++;
        return output;
    }
    public void joinGame(UserData user, String playerColor, int gameID) throws DataAccessException{
        if(playerColor != null){
            GameDatabase.getGame(gameID); //checks if game exists
            joinGame(user, playerColor, gameID);
        }
        //add as observer if null
    }
}
