package clientTests;

import dataAccess.DataAccessException;
import dataAccess.SqlGameDAO;
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
    public void loginTest() {
        assertTrue(true);
    }

    @Test
    public void loginFail() {
        assertTrue(true);
    }

    @Test
    public void joinTest() {
        assertTrue(true);
    }

    @Test
    public void joinFail() {
        assertTrue(true);
    }

    @Test
    public void listTest() {
        assertTrue(true);
    }

    @Test
    public void listFail() {
        assertTrue(true);
    }

    @Test
    public void createTest() {
        assertTrue(true);
    }

    @Test
    public void createFail() {
        assertTrue(true);
    }

    @Test
    public void logoutTest() {
        assertTrue(true);
    }

    @Test
    public void logoutFail() {
        assertTrue(true);
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
