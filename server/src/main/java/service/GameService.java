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

    public GameService(UserDAO UserDatabase, AuthDAO AuthDatabase, GameDAO GameDatabase){
        this.userDatabase = UserDatabase;
        this.authDatabase = AuthDatabase;
        this.gameDatabase = GameDatabase;


    }
    public Collection<GameData> listGames(String authToken) throws DataAccessException {
        authDatabase.getAuth(authToken);
        return gameDatabase.listGames();
    }
    public int createGame(String name, String authToken) throws DataAccessException{
        authDatabase.getAuth(authToken);
        gameDatabase.createGame(new GameData(nextId, null, null, name, new ChessGame()));
        int output = nextId;
        nextId++;
        return output;
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
