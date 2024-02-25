package serviceTests;

import chess.ChessGame;
import dataAccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;
import service.GameService;
import service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTest {

    private GameService myGameService;
    private UserService myUserService;
    @BeforeEach
    public void setup(){
        UserDAO userDatabase = new MemoryUserDAO();
        AuthDAO authDatabase = new MemoryAuthDAO();
        GameDAO gameDatabase = new MemoryGameDAO();
        this.myGameService = new GameService(userDatabase, authDatabase, gameDatabase);
        this.myUserService = new UserService(userDatabase, authDatabase);
    }
    @Test
    public void testListGames() throws DataAccessException {
        myUserService.register(new UserData("test1", "testP1", "testEmail"));
        AuthData auth = myUserService.login(new UserData("test1", "testP1", "testEmail"));

        int gameID1 = myGameService.createGame("gameTest1", auth.authToken());
        int gameID2 = myGameService.createGame("gameTest2", auth.authToken());

        ArrayList<GameData> gamesTest = new ArrayList<>();
        gamesTest.add(myGameService.gameDatabase.getGame(gameID1));
        gamesTest.add(myGameService.gameDatabase.getGame(gameID2));

        ArrayList<GameData> gamesList = new ArrayList<>(myGameService.listGames(auth.authToken()));

        assertEquals(gamesList, gamesTest);

    }

    @Test
    public void listGamesFails() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {
            myUserService.register(new UserData("test1", "testP1", "testEmail"));
            AuthData auth = myUserService.login(new UserData("test1", "testP1", "testEmail"));

            myGameService.createGame("gameTest1", auth.authToken());
            myGameService.createGame("gameTest2", auth.authToken());

            myGameService.listGames("FakeAuth");
        });
    }

    @Test
    public void testCreateGame() throws DataAccessException {
        myUserService.register(new UserData("test1", "testP1", "testEmail"));
        AuthData auth = myUserService.login(new UserData("test1", "testP1", "testEmail"));

        int gameID1 = myGameService.createGame("gameTest1", auth.authToken());

        GameData realGame = new GameData(gameID1, null, null, "gameTest1", myGameService.gameDatabase.getGame(gameID1).game());

        assertEquals(realGame, myGameService.gameDatabase.getGame(gameID1));


    }

    @Test
    public void createGameFails() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {
            myGameService.createGame("gameTest1", "fakeAuth");
        });
    }

    @Test
    public void testJoinGame() throws DataAccessException {
        myUserService.register(new UserData("test1", "testP1", "testEmail"));
        AuthData auth = myUserService.login(new UserData("test1", "testP1", "testEmail"));

        int gameID1 = myGameService.createGame("gameTest1", auth.authToken());
        myGameService.joinGame(auth.authToken(), "WHITE", gameID1);

        assertEquals(myGameService.gameDatabase.getGame(gameID1).whiteUsername(), "test1");
    }

    @Test
    public void joinGameFails() throws DataAccessException {
        myUserService.register(new UserData("test1", "testP1", "testEmail"));
        AuthData auth = myUserService.login(new UserData("test1", "testP1", "testEmail"));

        myGameService.createGame("gameTest1", auth.authToken());

        assertThrows(DataAccessException.class, () -> {
            myGameService.joinGame(auth.authToken(), "WHITE", 2);
        });

    }
}
