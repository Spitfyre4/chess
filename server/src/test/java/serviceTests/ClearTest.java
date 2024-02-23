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
        UserDAO UserDatabase = new MemoryUserDAO();
        AuthDAO AuthDatabase = new MemoryAuthDAO();
        GameDAO GameDatabase = new MemoryGameDAO();
        this.MyClearService = new ClearService(UserDatabase, AuthDatabase, GameDatabase);
    }

    @Test
    public void testIsEmpty() throws DataAccessException {
        assertTrue(this.MyClearService.isEmpty());
    }
    @Test
    public void testClear() throws DataAccessException {
        MyClearService.UserDatabase.createUser
                (new UserData("testName", "testPass", "testEmail"));
        MyClearService.UserDatabase.createUser
                (new UserData("testName2", "testPass2", "testEmail2"));

        MyClearService.AuthDatabase.createAuth("testName");
        MyClearService.AuthDatabase.createAuth("testName2");

        MyClearService.GameDatabase.createGame(new GameData
                (1, "testName", "testName2", "TestGame", new ChessGame()));

        this.MyClearService.clear();

        assertTrue(this.MyClearService.isEmpty());

    }
}
