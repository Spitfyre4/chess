package service;

import dataAccess.*;

public class ClearService {

    public final UserDAO UserDatabase;
    public final AuthDAO AuthDatabase;
    public final GameDAO GameDatabase;


    public ClearService(UserDAO UserDatabase, AuthDAO AuthDatabase, GameDAO GameDatabase){
        this.UserDatabase = UserDatabase;
        this.AuthDatabase = AuthDatabase;
        this.GameDatabase = GameDatabase;

    }

    public void clear() throws DataAccessException {
        AuthDatabase.clear();
        GameDatabase.clear();
        UserDatabase.clear();
    }

//    public void isEmpty() throws DataAccessException {
//        Boolean output = true;
//        if (this.AuthDatabase.)
//    }
}
