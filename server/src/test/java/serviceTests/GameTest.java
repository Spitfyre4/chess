package serviceTests;

import dataAccess.*;
import org.junit.jupiter.api.BeforeEach;
import service.ClearService;
import service.GameService;

public class GameTest {

    @BeforeEach
    public void setup(){
        UserDAO UserDatabase = new MemoryUserDAO();
        AuthDAO AuthDatabase = new MemoryAuthDAO();
        GameDAO GameDatabase = new MemoryGameDAO();
        GameService MyGameService = new GameService(UserDatabase, AuthDatabase, GameDatabase);
    }
}
