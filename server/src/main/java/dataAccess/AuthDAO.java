package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.Collection;

public interface AuthDAO {

    public AuthData createAuth(String username) throws DataAccessException;

    public AuthData getAuth(String authToken) throws DataAccessException;

    public void deleteAuth(String authToken) throws DataAccessException;

    public Collection<AuthData> listAuths() throws DataAccessException;

    public void clear() throws DataAccessException;
}