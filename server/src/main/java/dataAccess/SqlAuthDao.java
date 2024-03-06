package dataAccess;

import com.google.gson.Gson;
import model.AuthData;

import java.util.Collection;
import java.util.UUID;

public class SqlAuthDAO implements AuthDAO{

    public final DatabaseManager databaseManager = new DatabaseManager();
//    public SqlAuthDAO() throws DataAccessException {
//        databaseManager.configureDatabase();
//    }
    @Override
    public AuthData createAuth(String username) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        AuthData auth = new AuthData(authToken, username);

        var statement = "INSERT INTO auth (authTokens, username, json) VALUES (?, ?, ?)";
        var json = new Gson().toJson(auth);
        databaseManager.executeUpdate(statement, auth.authToken(), auth.username(), json);

        return auth;
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
        databaseManager.configureDatabase();
        var statement = "TRUNCATE auth";
        databaseManager.executeUpdate(statement);
    }
}
