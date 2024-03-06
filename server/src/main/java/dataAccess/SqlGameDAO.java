package dataAccess;

import com.google.gson.Gson;
import model.GameData;
import model.UserData;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class SqlGameDAO implements GameDAO{

    public final DatabaseManager databaseManager = new DatabaseManager();
//    public SqlGameDAO() throws DataAccessException {
//        databaseManager.configureDatabase();
//    }
    @Override
    public void createGame(GameData game) throws DataAccessException {
        databaseManager.configureDatabase();
        game = new GameData
                (game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());
        var statement = "INSERT INTO game (gameID, whiteUsername, blackUsername, gameName, jsonChessGame, json) VALUES (?, ?, ?, ?, ?, ?)";
        var jsonChessGame = new Gson().toJson(game.game());
        var json = new Gson().toJson(game);
        databaseManager.executeUpdate(statement, game.gameID(), game.whiteUsername(),
                game.blackUsername(), game.gameName(), jsonChessGame, json);
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        databaseManager.configureDatabase();
        try (Connection conn = databaseManager.getConnection()){
            var statement = "SELECT json FROM game WHERE gameID=?";
            try(var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                var rs = ps.executeQuery();
                if (rs.next()) {
                    String userDataJson = rs.getString("json");
                    return new Gson().fromJson(userDataJson, GameData.class);
                } else {
                    throw new DataAccessException("Error: bad request", 400);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage(), 500);
        }
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        databaseManager.configureDatabase();
        Collection<GameData> games = new ArrayList<>();

        try (Connection conn = databaseManager.getConnection()){
            var statement = "SELECT json FROM game";
            try(var ps = conn.prepareStatement(statement)) {
                var rs = ps.executeQuery();
                while (rs.next()) {
                    String gameDataJson = rs.getString("json");
                    GameData game = new Gson().fromJson(gameDataJson, GameData.class);
                    games.add(game);
                }
                return games;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage(), 500);
        }
    }

    @Override
    public void joinGame(String username, String playerColor, int gameId) throws DataAccessException {
        databaseManager.configureDatabase();

        GameData updatedGame = null;
        GameData game = getGame(gameId);

        if(Objects.equals(playerColor, "WHITE") && game.whiteUsername() == null){
            updatedGame = new GameData(gameId, username, game.blackUsername(), game.gameName(), game.game());
        }
        else if (Objects.equals(playerColor, "BLACK") && game.blackUsername() == null) {
            updatedGame = new GameData(gameId, game.whiteUsername(), username, game.gameName(), game.game());
        }else {
            throw new DataAccessException("Error: already taken", 403);
        }

        var statement = "UPDATE game SET gameID=?, whiteUsername=?, blackUsername=?, gameName=?, jsonChessGame=?, json=? WHERE gameID=?";
        var jsonChessGame = new Gson().toJson(updatedGame.game());
        var json = new Gson().toJson(updatedGame);
        databaseManager.executeUpdate(statement,
                updatedGame.gameID(), updatedGame.whiteUsername(), updatedGame.blackUsername(),
                updatedGame.gameName(), jsonChessGame, json, updatedGame.gameID());
    }

    @Override
    public void clear() throws DataAccessException {
        databaseManager.configureDatabase();
        var statement = "TRUNCATE game";
        databaseManager.executeUpdate(statement);
    }
}
