package serviceTests;

import dataAccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private UserService myUserService;

    @BeforeEach
    public void setup(){
        UserDAO userDatabase = new MemoryUserDAO();
        AuthDAO authDatabase = new MemoryAuthDAO();
        this.myUserService = new UserService(userDatabase, authDatabase);
    }

    @Test
    public void testRegister() throws DataAccessException {
        AuthData auth = myUserService.register(new UserData("test1", "testP1", "testEmail"));
        UserData newUser = myUserService.userDatabase.getUser(auth.username());

        assertEquals(new UserData("test1", "testP1", "testEmail"), newUser);


    }

    @Test
    public void testRegisterFails() throws DataAccessException {
        myUserService.register(new UserData("test1", "testP1", "testEmail"));

        try {
            myUserService.register(new UserData("test1", "testP2", "testEmail2"));
        } catch (DataAccessException e) {
            assertEquals("Error: already taken", e.getMessage());
        }
    }

    @Test
    public void testLogin() throws DataAccessException {
        myUserService.register(new UserData("test1", "testP1", "testEmail"));
        AuthData auth = myUserService.login(new UserData("test1", "testP1", "testEmail"));

        String authToken = myUserService.authDatabase.getAuth(auth.authToken()).authToken();

        assertEquals(auth.authToken(), authToken);
    }

    @Test
    public void testLoginFails() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {
            myUserService.login(new UserData("test1", "testP1", "testEmail1"));
        });
    }

    @Test
    public void testLogout() throws DataAccessException {
        myUserService.register(new UserData("test1", "testP1", "testEmail"));
        AuthData auth = myUserService.login(new UserData("test1", "testP1", "testEmail"));

        myUserService.logout(auth.authToken());

        assertEquals(this.myUserService.authDatabase.listAuths().size(), 1); //1 because the register authToken is still there

    }

    @Test
    public void testLogoutFails() throws DataAccessException {
    assertThrows(DataAccessException.class, () -> {
        myUserService.logout("fakeAuth");
        });
    }
}
