package serviceTests;

import dataAccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    private UserService MyUserService;

    @BeforeEach
    public void setup(){
        UserDAO UserDatabase = new MemoryUserDAO();
        AuthDAO AuthDatabase = new MemoryAuthDAO();
        this.MyUserService = new UserService(UserDatabase, AuthDatabase);
    }

    @Test
    public void testRegister() throws DataAccessException {
        AuthData auth = MyUserService.register(new UserData("test1", "testP1", "testEmail"));
        UserData newUser = MyUserService.UserDatabase.getUser(auth.username());

        assertEquals(new UserData("test1", "testP1", "testEmail"), newUser);


    }

    @Test
    public void testRegisterFails() throws DataAccessException {
        MyUserService.register(new UserData("test1", "testP1", "testEmail"));

        try {
            MyUserService.register(new UserData("test1", "testP2", "testEmail2"));
        } catch (DataAccessException e) {
            assertEquals("Username already exists", e.getMessage());
        }
    }

    @Test
    public void testLogin() throws DataAccessException {
        MyUserService.register(new UserData("test1", "testP1", "testEmail"));
        AuthData auth = MyUserService.login(new UserData("test1", "testP1", "testEmail"));

        String authToken = MyUserService.AuthDatabase.getAuth(auth.authToken()).authToken();

        assertEquals(auth.authToken(), authToken);
    }

    @Test
    public void testLoginFails() throws DataAccessException {
        try {
            MyUserService.login(new UserData("test1", "testP1", "testEmail1"));
        } catch (DataAccessException e) {
            assertEquals("Username doesn't exist", e.getMessage());
        }
    }

    @Test
    public void testLogout() throws DataAccessException {
        MyUserService.register(new UserData("test1", "testP1", "testEmail"));
        AuthData auth = MyUserService.login(new UserData("test1", "testP1", "testEmail"));

        MyUserService.logout(auth);

        try {
            MyUserService.AuthDatabase.getAuth(auth.authToken());
        } catch (DataAccessException e) {
            assertEquals("Auth Token doesn't exist", e.getMessage());
        }


    }

    @Test
    public void testLogoutFails() throws DataAccessException {
        try {
            MyUserService.logout(new AuthData("token", "username"));;
        } catch (DataAccessException e) {
            assertEquals("Auth Token doesn't exist", e.getMessage());
        }
    }
}
