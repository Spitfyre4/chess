package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.SqlAuthDAO;
import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthDAOTest {

    public SqlAuthDAO authDatabase;

    @BeforeEach
    public void setup() {
        this.authDatabase = new SqlAuthDAO();
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

    }

    @Test
    public void failGetAuth() throws DataAccessException{

    }

    @Test
    public void testDeleteAuth() throws DataAccessException{

    }

    @Test
    public void failDeleteAuth() throws DataAccessException{

    }

    @Test
    public void testListAuths() throws DataAccessException{

    }

    @Test
    public void failListAuths() throws DataAccessException{

    }

    @Test
    public void testClear() throws DataAccessException{

    }
}
