package dataAccess;

import model.AuthData;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{

    final private HashMap<Integer, GameData> games = new HashMap<>(); //can't use int because it's not an object
    //revisit this to see if you should change gameID to an Integer object or not

    @Override
    public GameData createGame(GameData game) {
        game = new GameData
                (game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());
        games.put(game.gameID(), game);
        return game;
    }

    @Override
    public GameData getGame(int gameID) {
        return games.get(gameID);
    }

    @Override
    public Collection<GameData> listGames() {
        return games.values();
    }

    @Override
    public void updateGame(int gameID) {

    }

    @Override
    public void clear() {
        games.clear();
    }
}
