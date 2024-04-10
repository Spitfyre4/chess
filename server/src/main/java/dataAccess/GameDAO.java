package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;

public interface GameDAO {

    public void createGame(GameData game) throws DataAccessException;

    public GameData getGame(int gameID) throws DataAccessException;

    public Collection<GameData> listGames() throws DataAccessException;

    public void joinGame(String username, String playerColor, int gameId) throws DataAccessException;
    public void clear() throws DataAccessException;

    public int getGameID() throws DataAccessException;

    public void updateGame(ChessGame newGame, int gameID) throws DataAccessException ;
}
