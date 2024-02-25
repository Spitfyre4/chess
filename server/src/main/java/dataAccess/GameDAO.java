package dataAccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {

    public GameData createGame(GameData game) throws DataAccessException;

    public GameData getGame(int gameID) throws DataAccessException;

    public Collection<GameData> listGames() throws DataAccessException;
    public void updateGame(int gameID) throws DataAccessException;
    public void joinGame(String username, String playerColor, int gameId) throws DataAccessException;
    public void clear() throws DataAccessException;
}
