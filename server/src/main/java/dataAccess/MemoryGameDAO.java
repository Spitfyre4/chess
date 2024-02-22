package dataAccess;

import model.AuthData;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{

    final private HashMap<String, GameData> games = new HashMap<>(); //can't use int because it's not an object
    //revisit this to see if you should change gameID to an Integer object or not

    @Override
    public GameData createGame() {
        return null;
    }

    @Override
    public GameData getGame(int gameID) {
        return null;
    }

    @Override
    public Collection<GameData> listGames() {
        return null;
    }

    @Override
    public void updateGame(int gameID) {

    }

    @Override
    public void clear() {
        games.clear();
    }
}
