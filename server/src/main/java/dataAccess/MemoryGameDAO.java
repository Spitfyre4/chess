package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class MemoryGameDAO implements GameDAO{

    final private HashMap<Integer, GameData> games = new HashMap<>();

    @Override
    public void createGame(GameData game) throws DataAccessException{
        game = new GameData
                (game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());
        games.put(game.gameID(), game);
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
    public void joinGame(String username, String playerColor, int gameId) throws DataAccessException {
        GameData game = games.get(gameId);
        if(Objects.equals(playerColor, "WHITE") && game.whiteUsername() == null){
            GameData updatedGame = new GameData(gameId, username, game.blackUsername(), game.gameName(), game.game());
            games.put(gameId, updatedGame);
        }
        else if (Objects.equals(playerColor, "BLACK") && game.blackUsername() == null) {
            GameData updatedGame = new GameData(gameId, game.whiteUsername(), username, game.gameName(), game.game());
            games.put(gameId, updatedGame);
        }else {
            throw new DataAccessException("Error: already taken", 403);
        }

    }

    public int getGameID(){
        return 1;
    }

    @Override
    public void updateGame(ChessGame newGame, int gameID) throws DataAccessException {
        GameData gameData = games.get(gameID);
        games.replace(gameID, new GameData(gameID, gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), newGame));
    }

    @Override
    public void clear() throws DataAccessException{
        games.clear();
    }
}
