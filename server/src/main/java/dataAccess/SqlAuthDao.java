package dataAccess;

import model.AuthData;

import java.util.Collection;

public class SqlAuthDAO implements AuthDAO{

    public final DatabaseManager databaseManager = new DatabaseManager();
    public SqlAuthDAO() throws DataAccessException {
        databaseManager.configureDatabase();
    }
    @Override
    public AuthData createAuth(String username) throws DataAccessException {
        return null;
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }

    @Override
    public Collection<AuthData> listAuths() throws DataAccessException {
        return null;
    }

    @Override
    public void clear() throws DataAccessException {

    }
}
