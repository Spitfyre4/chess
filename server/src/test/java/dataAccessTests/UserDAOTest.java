package dataAccessTests;

import chess.ChessGame;
import dataAccess.*;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    public SqlUserDAO userDatabase;

    @BeforeEach
    public void setup() throws DataAccessException {
        this.userDatabase = new SqlUserDAO();
        userDatabase.clear();
    }

    @Test
    public void testCreateUser()  throws DataAccessException{
        UserData user = new UserData("testName", "testPass", "test@gmail.com");

        userDatabase.createUser(user);

        assertEquals(user.username(), userDatabase.getUser(user.username()).username());
    }

    @Test
    public void failCreateUser()  throws DataAccessException{
        UserData user = new UserData(null, "testPass", "test@gmail.com");

        assertThrows(DataAccessException.class, () -> {
            userDatabase.createUser(user);
        });
    }

    @Test
    public void testGetUser()  throws DataAccessException{
        UserData user = new UserData("testName", "testPass", "test@gmail.com");

        userDatabase.createUser(user);
        UserData dbUser = userDatabase.getUser(user.username());

        assertEquals(user.username(), dbUser.username());
    }

    @Test
    public void failGetUser()  throws DataAccessException{
        UserData user = new UserData("testName", "testPass", "test@gmail.com");
        userDatabase.createUser(user);

        assertThrows(DataAccessException.class, () -> {
            userDatabase.getUser("fail");
        });
    }

    @Test
    public void testListUsers()  throws DataAccessException{
        UserData user = new UserData("testName", "testPass", "test@gmail.com");
        UserData user2 = new UserData("testName2", "testPass2", "test2@gmail.com");

        userDatabase.createUser(user);
        userDatabase.createUser(user2);

        ArrayList<UserData> userList = new ArrayList<>(userDatabase.listUsers());

        assertEquals(2, userList.size());
    }

    @Test
    public void failListUsers()  throws DataAccessException{
        UserData user = new UserData("testName", "testPass", "test@gmail.com");
        UserData user2 = new UserData("testName2", "testPass2", "test2@gmail.com");

        userDatabase.createUser(user);
        userDatabase.createUser(user2);

        ArrayList<UserData> userList = new ArrayList<>(userDatabase.listUsers());

        assertNotEquals(1, userList.size());
    }

    @Test
    public void testUserExists() throws DataAccessException{
        UserData user = new UserData("testName", "testPass", "test@gmail.com");

        userDatabase.createUser(user);

        assertTrue(userDatabase.userExists(user));
    }

    @Test
    public void failUserExists() throws DataAccessException{
        UserData user = new UserData("testName", "testPass", "test@gmail.com");

        assertFalse(userDatabase.userExists(user));
    }

    @Test
    public void testClear()  throws DataAccessException{
        userDatabase.clear();

        ArrayList<UserData> userList = new ArrayList<>(userDatabase.listUsers());

        assertEquals(0, userList.size());
    }
}
