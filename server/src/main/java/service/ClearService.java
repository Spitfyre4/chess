package service;

import dataAccess.*;

public class ClearService {

    public final UserDAO userDatabase;
    public final AuthDAO authDatabase;
    public final GameDAO gameDatabase;


    public ClearService(UserDAO userDatabase, AuthDAO authDatabase, GameDAO gameDatabase){
        this.userDatabase = userDatabase;
        this.authDatabase = authDatabase;
        this.gameDatabase = gameDatabase;

    }

    public void clear() throws DataAccessException {
        authDatabase.clear();
        gameDatabase.clear();
        userDatabase.clear();
    }

    public boolean isEmpty() throws DataAccessException {
        boolean output = false;
        if (this.authDatabase.listAuths().isEmpty() && this.gameDatabase.listGames().isEmpty() && this.userDatabase.listUsers().isEmpty()){
            output = true;
        }

        return output;
    }
}
