package service;


import dataAccess.*;
import model.AuthData;
import model.UserData;

public class UserService {
    private final AuthDAO AuthDatabase;
    private final UserDAO UserDatabase;

    public UserService(){
        this.AuthDatabase = new MemoryAuthDAO();
        this.UserDatabase = new MemoryUserDAO();
    }
    public AuthData register(UserData user) {
        user = UserDatabase.createUser(user);
        return AuthDatabase.createAuth(user.username());
    }
//    public AuthData login(UserData user) {}
//    public void logout(UserData user) {}
}
