package service;


import dataAccess.*;
import model.AuthData;
import model.UserData;

public class UserService {

    public final UserDAO UserDatabase;
    public final AuthDAO AuthDatabase;

    public UserService(UserDAO UserDatabase, AuthDAO AuthDatabase){
        this.UserDatabase = UserDatabase;
        this.AuthDatabase = AuthDatabase;
    }
    public AuthData register(UserData user) throws DataAccessException {
        if(user.username() == null || user.password() == null || user.email() == null){
            throw new DataAccessException("Error: Bad request", 400);
        }
        user = UserDatabase.createUser(user);
        return AuthDatabase.createAuth(user.username());
    }
    public AuthData login(UserData user) throws DataAccessException {
        UserDatabase.userExists(user);

        return AuthDatabase.createAuth(user.username());
    }

    public void logout(String authToken) throws DataAccessException{
        AuthDatabase.deleteAuth(authToken);
    }
}
