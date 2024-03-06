package dataAccess;

import com.google.gson.Gson;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SqlUserDAO implements UserDAO{

    public final DatabaseManager databaseManager = new DatabaseManager();
//    public SqlUserDAO() throws DataAccessException {
//        databaseManager.configureDatabase();
//    }
    @Override
    public UserData createUser(UserData user) throws DataAccessException {
        databaseManager.configureDatabase();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(user.password());

        if(userExists(user)){
            throw new DataAccessException("Error: already taken", 403);
        }

        user = new UserData(user.username(), hashedPassword, user.email());
        var statement = "INSERT INTO user (username, password, email, json) VALUES (?, ?, ?, ?)";
        var json = new Gson().toJson(user);
        databaseManager.executeUpdate(statement, user.username(), user.password(), user.email(), json);

        return user;
    }



    @Override
    public UserData getUser(String username) throws DataAccessException {
        databaseManager.configureDatabase();
        try (Connection conn = databaseManager.getConnection()){
            var statement = "SELECT json FROM user WHERE username=?";
            try(var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                var rs = ps.executeQuery();
                if (rs.next()) {
                    String userDataJson = rs.getString("json");
                    return new Gson().fromJson(userDataJson, UserData.class);
                } else {
                    throw new DataAccessException("Error: bad request", 400);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage(), 500);
        }
    }

    @Override
    public Collection<UserData> listUsers() throws DataAccessException {
        databaseManager.configureDatabase();
        Collection<UserData> users = new ArrayList<>();

        try (Connection conn = databaseManager.getConnection()){
            var statement = "SELECT json FROM user";
            try(var ps = conn.prepareStatement(statement)) {
                var rs = ps.executeQuery();
                while (rs.next()) {
                    String userDataJson = rs.getString("json");
                    UserData user = new Gson().fromJson(userDataJson, UserData.class);
                    users.add(user);
                }
                return users;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage(), 500);
        }
    }

    @Override
    public boolean userExists(UserData user) throws DataAccessException {
        databaseManager.configureDatabase();
        try (Connection conn = databaseManager.getConnection()){
             var statement = "SELECT json FROM user WHERE username=?";
             try(var ps = conn.prepareStatement(statement)) {
                 ps.setString(1, user.username());
                 var rs = ps.executeQuery();
                 if (rs.next()) {
                     String userDataJson = rs.getString("json");
                     UserData dbUser = new Gson().fromJson(userDataJson, UserData.class);
                     BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                     if(!encoder.matches(user.password(), dbUser.password())){
                         throw new DataAccessException("Error: unauthorized", 401);
                     }
                     return true;
                 } else {
                     return false;
                 }
             }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage(), 500);
        }
    }

    @Override
    public void clear() throws DataAccessException {
        databaseManager.configureDatabase();
        var statement = "TRUNCATE user";
        databaseManager.executeUpdate(statement);
    }
}
