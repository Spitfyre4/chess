package dataAccessTests;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.SqlGameDAO;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class GameDAOTest {

    public SqlGameDAO gameDatabase;

    @BeforeEach
    public void setup() throws DataAccessException {
        this.gameDatabase = new SqlGameDAO();
        gameDatabase.clear();
    }

    @Test
    public void testCreateGame() throws DataAccessException{
        GameData game = new GameData(1, null, null, "game1", new ChessGame());
        gameDatabase.createGame(game);

        ArrayList<GameData> gameList = new ArrayList<>(gameDatabase.listGames());

        assertEquals(1, gameList.size());
    }

    @Test
    public void failCreateGame() throws DataAccessException{
        GameData game = new GameData(1, null, null, null, new ChessGame());

        assertThrows(DataAccessException.class, () -> {
            gameDatabase.createGame(game);
        });

    }

    @Test
    public void testGetGame() throws DataAccessException{
        GameData game = new GameData(1, null, null, "game1", new ChessGame());
        gameDatabase.createGame(game);

        assertEquals(game.gameName(), gameDatabase.getGame(1).gameName());
    }

    @Test
    public void failGetGame() throws DataAccessException{
        GameData game = new GameData(1, null, null, "game1", new ChessGame());

        assertThrows(DataAccessException.class, () -> {
            gameDatabase.getGame(game.gameID());
        });
    }

    @Test
    public void testListGames() throws DataAccessException{
        GameData game = new GameData(1, null, null, "game1", new ChessGame());
        GameData game2 = new GameData(2, null, null, "game2", new ChessGame());
        gameDatabase.createGame(game);
        gameDatabase.createGame(game2);

        ArrayList<GameData> gameList = new ArrayList<>(gameDatabase.listGames());

        assertEquals(2, gameList.size());
    }

    @Test
    public void failListGames() throws DataAccessException{
        GameData game = new GameData(1, null, null, "game1", new ChessGame());
        GameData game2 = new GameData(2, null, null, "game2", new ChessGame());
        gameDatabase.createGame(game);
        gameDatabase.createGame(game2);

        ArrayList<GameData> gameList = new ArrayList<>(gameDatabase.listGames());

        assertNotEquals(1, gameList.size());
    }

    @Test
    public void testJoinGame() throws DataAccessException{
        GameData game = new GameData(1, null, null, "game1", new ChessGame());
        gameDatabase.createGame(game);

        gameDatabase.joinGame("username1", "WHITE", 1);

        assertEquals("username1", gameDatabase.getGame(1).whiteUsername());

    }

    @Test
    public void failJoinGame() throws DataAccessException{
        GameData game = new GameData(1, null, null, "game1", new ChessGame());
        gameDatabase.createGame(game);

        assertThrows(DataAccessException.class, () -> {
            gameDatabase.joinGame("username1", "WHITE", 2);
        });
    }

    @Test
    public void testClear() throws DataAccessException{
        gameDatabase.clear();

        ArrayList<GameData> gameList = new ArrayList<>(gameDatabase.listGames());

        assertEquals(0, gameList.size());
    }
}
