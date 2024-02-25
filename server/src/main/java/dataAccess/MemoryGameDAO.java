package dataAccess;

import model.AuthData;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

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
        if (!games.containsKey(gameID)){
            throw new DataAccessException("Error: bad request", 400);
        }
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
    public void joinGame throws DataAccessException {
        GameData game = games.get(gameId);(String username, String playerColor, int gameId)
        if(Objects.equals(playerColor, "White") && game.whiteUsername() == null){
            GameData updatedGame = new GameData(gameId, username, game.blackUsername(), game.gameName(), game.game());
            games.put(gameId, updatedGame);
        }
        else if (Objects.equals(playerColor, "Black") && game.blackUsername() == null) {
            GameData updatedGame = new GameData(gameId, game.whiteUsername(), username, game.gameName(), game.game());
            games.put(gameId, updatedGame);
        }else {
            throw new DataAccessException("Error: already taken", 403);
        }

    }

    @Override
    public void clear() throws DataAccessException{
        games.clear();
    }
}
