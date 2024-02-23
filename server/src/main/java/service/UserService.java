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
        user = UserDatabase.createUser(user);
        return AuthDatabase.createAuth(user.username());
    }
    public AuthData login(UserData user) throws DataAccessException {
        UserDatabase.loginCheck(user);

        return AuthDatabase.createAuth(user.username());
    }

    public void logout(AuthData auth) throws DataAccessException{
        AuthDatabase.deleteAuth(auth.authToken());
    }
}
