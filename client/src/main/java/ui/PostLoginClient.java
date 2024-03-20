package ui;


import model.*;
import server.ServerFacade;
import server.ServerException;

import java.util.Scanner;

public class PostLoginClient {

    public final ServerFacade server;
    public final String url;
    public final AuthData auth;
    public boolean run;

//    public final GameplayClient gameplay;

    public PostLoginClient(String url, AuthData auth) {
        server = new ServerFacade(url);
        this.url = url;
        this.auth = auth;
        this.run = true;
    }

    public void run() throws ServerException {
        this.help();

        Scanner scanner = new Scanner(System.in);
        while (run) {
            String line = scanner.nextLine();

            try {
                run = this.eval(line);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    public boolean eval(String input) {

        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            switch (cmd) {
                case "logout" -> logout();
                case "create" -> createGame();
                case "list" -> listGames();
                case "join" -> joinGame();
                case "watch" -> watch();
                case "quit" -> {
                    run = false;
                }
                default -> help();
            }
            ;
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return run;
    }

    private void watch() {
    }

    private void joinGame() {
    }

    private void listGames() throws ServerException {
        var path = "/game";

        var games = server.makeRequest("GET", this.auth.authToken(), path, null, GamesData.class);
        System.out.println(games);
    }

    private void createGame() throws ServerException {
        var path = "/game";
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter game name: ");
        String name = scanner.nextLine();

        GameData game = new GameData(-1, null, null, name, null);
        var gameID = server.makeRequest("POST", this.auth.authToken(), path, game, GameID.class);
        System.out.println(gameID);
        System.out.println("Type join to join game");
        help();
    }

    private void logout() throws ServerException {
        var path = "/session";

        server.makeRequest("DELETE", this.auth.authToken(), path, null, Object.class);
        run = false;
    }

    public void help() throws ServerException {
        System.out.println("""
                    - logout: Logout of account
                    - create: Make a new chess game
                    - list: List all chess games
                    - join: Join an existing chess game
                    - watch: Watch an existing chess game
                    - quit: Quit application
                    - help: Reprint commands
                    """);
    }
}
