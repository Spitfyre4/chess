package serviceTests;

import dataAccess.*;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;

public class ClearTest {

    public ClearService MyClearService;

    @BeforeEach
    public void setup(){
        UserDAO UserDatabase = new MemoryUserDAO();
        AuthDAO AuthDatabase = new MemoryAuthDAO();
        GameDAO GameDatabase = new MemoryGameDAO();
        ClearService MyClearService = new ClearService(UserDatabase, AuthDatabase, GameDatabase);
    }

    @Test
    public void testClear() throws DataAccessException {
        MyClearService.UserDatabase.createUser
                (new UserData("testName", "testPass", "testEmail"));
        MyClearService.UserDatabase.createUser
                (new UserData("testName2", "testPass2", "testEmail2"));
        //MyClearService.AuthDatabase issue atm is auth tokens get made in the other service
        this.MyClearService.clear();

    }
}
