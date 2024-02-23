package dataAccess;

import model.AuthData;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{

    final private HashMap<Integer, GameData> games = new HashMap<>();

    @Override
    public GameData createGame(GameData game) throws DataAccessException{
        game = new GameData
                (game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());
        games.put(game.gameID(), game);
        return game;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException{
        return games.get(gameID);
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException{
        return games.values();
    }

    @Override
    public void updateGame(int gameID) throws DataAccessException{

    }

    @Override
    public void clear() throws DataAccessException{
        games.clear();
    }
}
