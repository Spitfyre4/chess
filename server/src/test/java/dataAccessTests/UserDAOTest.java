package dataAccessTests;

import chess.ChessGame;
import dataAccess.*;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;

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
        UserData dbUser = userDatabase.getUser(user.username());
        assertEquals(user.username(), dbUser.username());
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

    }

    @Test
    public void failGetUser()  throws DataAccessException{

    }

    @Test
    public void testListUsers()  throws DataAccessException{

    }

    @Test
    public void failListUsers()  throws DataAccessException{

    }

    @Test
    public void testUserExists() throws DataAccessException{

    }

    @Test
    public void failUserExists() throws DataAccessException{

    }

    @Test
    public void testClear()  throws DataAccessException{

    }
}
