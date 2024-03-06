package dataAccess;

import com.google.gson.Gson;
import model.AuthData;
import model.UserData;

import java.sql.Connection;
import java.sql.SQLException;
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

        var statement = "INSERT INTO auth (authToken, username, json) VALUES (?, ?, ?)";
        var json = new Gson().toJson(auth);
        databaseManager.executeUpdate(statement, auth.authToken(), auth.username(), json);

        return auth;
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        databaseManager.configureDatabase();
        try (Connection conn = databaseManager.getConnection()){
            var statement = "SELECT json FROM auth WHERE authToken=?";
            try(var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                var rs = ps.executeQuery();
                if (rs.next()) {
                    String authDataJson = rs.getString("json");
                    return new Gson().fromJson(authDataJson, AuthData.class);
                } else {
                    throw new DataAccessException("Error: unauthorized", 401);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage(), 500);
        }
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        databaseManager.configureDatabase();
        try (Connection conn = databaseManager.getConnection()){
            var statement = "DELETE FROM auth WHERE authToken=?";
            try(var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    throw new DataAccessException("Error: unauthorized", 401);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage(), 500);
        }
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
