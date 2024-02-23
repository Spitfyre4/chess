package service;


import dataAccess.*;
import model.AuthData;
import model.UserData;

public class UserService {

    private final UserDAO UserDatabase;
    private final AuthDAO AuthDatabase;

    public UserService(UserDAO UserDatabase, AuthDAO AuthDatabase){
        this.UserDatabase = UserDatabase;
        this.AuthDatabase = AuthDatabase;
    }
    public AuthData register(UserData user) {
        user = UserDatabase.createUser(user);
        return AuthDatabase.createAuth(user.username());
    }
//    public AuthData login(UserData user) {}
//    public void logout(UserData user) {}
}
