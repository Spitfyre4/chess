package clientTests;

import dataAccess.DataAccessException;
import dataAccess.SqlGameDAO;
import model.GameData;
import model.JoinGameReq;
import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;
import server.ServerException;
import server.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade sFacade;

    @BeforeAll
    public static void init() throws ServerException {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        sFacade = new ServerFacade("http://localhost:" + port);
    }

    @BeforeEach
    public void setup() throws ServerException {
        sFacade.clear();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void registerTest() throws ServerException {
        UserData user = new UserData("username", "password", "email");
        var auth = sFacade.register(user);
        assertNotNull(auth.authToken());
    }

    @Test
    public void registerFail() throws ServerException {
        UserData user = new UserData("username", "password", "email");
        sFacade.register(user);
        assertThrows(ServerException.class, () -> {
            sFacade.register(user);
        });

    }

    @Test
    public void loginTest() throws ServerException {
        UserData user = new UserData("username", "password", "email");
        sFacade.register(user);
        var auth = sFacade.login(user);
        assertNotNull(auth.authToken());
    }

    @Test
    public void loginFail() {
        UserData user = new UserData("username", "password", "email");
        assertThrows(ServerException.class, () -> {
            sFacade.login(user);
        });

    }

    @Test
    public void joinTest() throws ServerException {
        UserData user = new UserData("username", "password", "email");
        var auth = sFacade.register(user);
        String authToken = auth.authToken();

        GameData game = new GameData(-1, null, null, "game", null);
        var gameID = sFacade.createGame(game, authToken);

        JoinGameReq req = new JoinGameReq("WHITE", gameID.gameID());


        assertDoesNotThrow(() -> sFacade.joinGame(req, authToken));

    }

    @Test
    public void joinFail() throws ServerException {
        UserData user = new UserData("username", "password", "email");
        var auth = sFacade.register(user);
        String authToken = auth.authToken();

        JoinGameReq req = new JoinGameReq("WHITE", 1);

        assertThrows(ServerException.class, () -> {
            sFacade.joinGame(req, authToken);
        });

    }

    @Test
    public void listTest() throws ServerException {
        UserData user = new UserData("username", "password", "email");
        var auth = sFacade.register(user);
        String authToken = auth.authToken();

        GameData game = new GameData(-1, null, null, "game", null);
        sFacade.createGame(game, authToken);

        assertNotNull(sFacade.listGames(authToken));
    }

    @Test
    public void listFail() throws ServerException {
        UserData user = new UserData("username", "password", "email");
        var auth = sFacade.register(user);
        String authToken = auth.authToken();

        GameData game = new GameData(-1, null, null, "game", null);
        sFacade.createGame(game, authToken);

        assertThrows(ServerException.class, () -> {
            sFacade.listGames("badAuth");
        });
    }

    @Test
    public void createTest() throws ServerException {
        UserData user = new UserData("username", "password", "email");
        var auth = sFacade.register(user);
        String authToken = auth.authToken();

        GameData game = new GameData(-1, null, null, "game", null);

        assertNotNull(sFacade.createGame(game, authToken));
    }

    @Test
    public void createFail() throws ServerException {
        UserData user = new UserData("username", "password", "email");
        var auth = sFacade.register(user);
        String authToken = auth.authToken();

        GameData game = new GameData(-1, null, null, "game", null);

        assertThrows(ServerException.class, () -> {
            sFacade.createGame(game, "bad auth");
        });
    }

    @Test
    public void logoutTest() throws ServerException {
        UserData user = new UserData("username", "password", "email");
        var auth = sFacade.register(user);
        String authToken = auth.authToken();

        GameData game = new GameData(-1, null, null, "game", null);

        sFacade.logout(authToken);

        assertThrows(ServerException.class, () -> {
            sFacade.createGame(game, authToken);
        });
    }

    @Test
    public void logoutFail() throws ServerException {
        UserData user = new UserData("username", "password", "email");
        var auth = sFacade.register(user);
        String authToken = auth.authToken();

        assertThrows(ServerException.class, () -> {
            sFacade.logout("bad auth");
        });
    }

    @Test
    public void clearTest() throws ServerException {
        UserData user = new UserData("username", "password", "email");
        sFacade.register(user);

        sFacade.clear();

        assertThrows(ServerException.class, () -> {
            sFacade.login(user);
        });

    }


}
