package service;

import dataAccess.*;

public class ClearService {
    public final AuthDAO AuthDatabase;
    public final GameDAO GameDatabase;
    public final UserDAO UserDatabase;

    public ClearService(){
        this.AuthDatabase = new MemoryAuthDAO();
        this.GameDatabase = new MemoryGameDAO();
        this.UserDatabase = new MemoryUserDAO();
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
