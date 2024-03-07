package dataAccessTests;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.SqlGameDAO;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        assertEquals(game.gameName(), gameDatabase.getGame(1).gameName());
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

    }

    @Test
    public void failGetGame() throws DataAccessException{

    }

    @Test
    public void testListGames() throws DataAccessException{

    }

    @Test
    public void failListGames() throws DataAccessException{

    }

    @Test
    public void testJoinGame() throws DataAccessException{

    }

    @Test
    public void failJoinGame() throws DataAccessException{

    }

    @Test
    public void testClear() throws DataAccessException{

    }
}
