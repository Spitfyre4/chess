package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.SqlAuthDAO;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AuthDAOTest {

    public SqlAuthDAO authDatabase;

    @BeforeEach
    public void setup() throws DataAccessException {
        this.authDatabase = new SqlAuthDAO();
        authDatabase.clear();
    }

    @Test
    public void testCreateAuth() throws DataAccessException{
        AuthData auth = authDatabase.createAuth("username1");

        String dbUsername = authDatabase.getAuth(auth.authToken()).username();

        assertEquals("username1", dbUsername);
    }

    @Test
    public void failCreateAuth() throws DataAccessException{
        assertThrows(DataAccessException.class, () -> {
            authDatabase.createAuth(null);
        });
    }

    @Test
    public void testGetAuth() throws DataAccessException{
        AuthData auth = authDatabase.createAuth("username1");

        AuthData dbAuth = authDatabase.getAuth(auth.authToken());

        assertEquals(auth, dbAuth);
    }

    @Test
    public void failGetAuth() throws DataAccessException{
        assertThrows(DataAccessException.class, () -> {
            authDatabase.getAuth("fake");
        });
    }

    @Test
    public void testDeleteAuth() throws DataAccessException{
        AuthData auth = authDatabase.createAuth("username1");

        authDatabase.deleteAuth(auth.authToken());

        assertEquals(0, authDatabase.listAuths().size());
    }

    @Test
    public void failDeleteAuth() throws DataAccessException{
        assertThrows(DataAccessException.class, () -> {
            authDatabase.deleteAuth("fake");
        });
    }

    @Test
    public void testListAuths() throws DataAccessException{
        authDatabase.createAuth("username1");
        authDatabase.createAuth("username2");


        assertEquals(2, authDatabase.listAuths().size());
    }

    @Test
    public void failListAuths() throws DataAccessException{
        authDatabase.createAuth("username1");
        authDatabase.createAuth("username2");


        assertNotEquals(1, authDatabase.listAuths().size());
    }

    @Test
    public void testClear() throws DataAccessException{
        authDatabase.clear();

        assertEquals(0, authDatabase.listAuths().size());
    }
}
