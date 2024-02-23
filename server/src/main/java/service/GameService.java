package service;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;

public class GameService {

    public final UserDAO UserDatabase;
    public final AuthDAO AuthDatabase;
    public final GameDAO GameDatabase;

    public GameService(UserDAO UserDatabase, AuthDAO AuthDatabase, GameDAO GameDatabase){
        this.UserDatabase = UserDatabase;
        this.AuthDatabase = AuthDatabase;
        this.GameDatabase = GameDatabase;

    }
//    public Collection<GameData> listGames() {}
//    public GameData createGame(UserData user) {}
//    public void joinGame(UserData user) {}
}
