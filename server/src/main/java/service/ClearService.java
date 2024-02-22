package service;

import dataAccess.*;

public class ClearService {
    private final AuthDAO AuthDatabase;
    private final GameDAO GameDatabase;
    private final UserDAO UserDatabase;

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
}
