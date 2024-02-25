package serviceTests;

import chess.ChessGame;
import dataAccess.*;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;

import static org.junit.jupiter.api.Assertions.*;

public class ClearTest {

    public ClearService MyClearService;

    @BeforeEach
    public void setup(){
        UserDAO userDatabase = new MemoryUserDAO();
        AuthDAO authDatabase = new MemoryAuthDAO();
        GameDAO gameDatabase = new MemoryGameDAO();
        this.MyClearService = new ClearService(userDatabase, authDatabase, gameDatabase);
    }

    @Test
    public void testIsEmpty() throws DataAccessException {
        assertTrue(this.MyClearService.isEmpty());
    }
    @Test
    public void testClear() throws DataAccessException {
        MyClearService.userDatabase.createUser
                (new UserData("testName", "testPass", "testEmail"));
        MyClearService.userDatabase.createUser
                (new UserData("testName2", "testPass2", "testEmail2"));

        MyClearService.authDatabase.createAuth("testName");
        MyClearService.authDatabase.createAuth("testName2");

        MyClearService.gameDatabase.createGame(new GameData
                (1, "testName", "testName2", "TestGame", new ChessGame()));

        this.MyClearService.clear();

        assertTrue(this.MyClearService.isEmpty());

    }
}
