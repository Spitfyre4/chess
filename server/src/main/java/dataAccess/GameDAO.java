package dataAccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {

    public GameData createGame();

    public GameData getGame(int gameID);

    public Collection<GameData> listGames();

    public void updateGame(int gameID);
    public void clear();
}
