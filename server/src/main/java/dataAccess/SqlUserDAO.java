package dataAccess;

import model.UserData;

import java.util.Collection;

public class SqlUserDAO implements UserDAO{
    @Override
    public UserData createUser(UserData user) throws DataAccessException {
        return null;
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return null;
    }

    @Override
    public Collection<UserData> listUsers() throws DataAccessException {
        return null;
    }

    @Override
    public boolean userExists(UserData user) throws DataAccessException {
        return false;
    }

    @Override
    public void clear() throws DataAccessException {

    }
}
