package service;


import dataAccess.*;
import model.AuthData;
import model.UserData;

public class UserService {

    public final UserDAO userDatabase;
    public final AuthDAO authDatabase;

    public UserService(UserDAO userDatabase, AuthDAO authDatabase){
        this.userDatabase = userDatabase;
        this.authDatabase = authDatabase;
    }
    public AuthData register(UserData user) throws DataAccessException {
        if(user.username() == null || user.password() == null || user.email() == null ||
                user.username().isEmpty() || user.password().isEmpty() || user.email().isEmpty()){
            throw new DataAccessException("Error: Bad request", 400);
        }
        user = userDatabase.createUser(user);
        return authDatabase.createAuth(user.username());
    }
    public AuthData login(UserData user) throws DataAccessException {
        if(!userDatabase.userExists(user)){
            throw new DataAccessException("Error: unauthorized", 401);
        }

        return authDatabase.createAuth(user.username());
    }

    public void logout(String authToken) throws DataAccessException{
        authDatabase.deleteAuth(authToken);
    }
}
