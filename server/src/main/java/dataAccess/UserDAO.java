package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.Collection;

public interface UserDAO {

    public UserData createUser(UserData user)  throws DataAccessException;

    public UserData getUser(String username)  throws DataAccessException;

    public Collection<UserData> listUsers()  throws DataAccessException;

    public boolean userExists(UserData user) throws DataAccessException;

    public void clear()  throws DataAccessException;
}
