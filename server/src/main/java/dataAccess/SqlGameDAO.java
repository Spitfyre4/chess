package dataAccess;

import model.GameData;

import java.util.Collection;

public class SqlGameDAO implements GameDAO{
    @Override
    public void createGame(GameData game) throws DataAccessException {
        
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return null;
    }

    @Override
    public void joinGame(String username, String playerColor, int gameId) throws DataAccessException {

    }

    @Override
    public void clear() throws DataAccessException {

    }
}
