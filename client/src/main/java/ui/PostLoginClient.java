package ui;


import model.*;
import server.ServerFacade;
import exception.ServerException;
import websocket.WebSocketFacade;

import java.util.Scanner;

public class PostLoginClient {

    public final ServerFacade server;
    public final String url;
    public final AuthData auth;
    public boolean run;
    public GameplayClient gameClient;

    public PostLoginClient(String url, AuthData auth) throws ServerException {
        server = new ServerFacade(url);
        this.url = url;
        this.auth = auth;
        this.run = true;
    }

    public void run() throws ServerException {
        System.out.println();
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
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                default -> help();
            }
            ;
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return run;
    }

    private void watch() throws ServerException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter game number: ");
        int gameID = Integer.parseInt(scanner.nextLine());

        this.gameClient = new GameplayClient(this.url, gameID, this.auth);
        this.gameClient.run();
        this.help();
    }

    private void joinGame() throws ServerException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter game number: ");
        int gameNum = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter requested player color(WHITE/BLACK): ");
        String playerColor = scanner.nextLine();

        GamesData games = server.listGames(auth.authToken());

        int gameID = games.getGame(gameNum-1).gameID();
        JoinGameReq req = new JoinGameReq(playerColor, gameID);
        server.joinGame(req, auth.authToken());

        this.gameClient = new GameplayClient(this.url, gameID, playerColor, this.auth);
        this.gameClient.run();
        this.help();
    }

    private void listGames() throws ServerException {

        GamesData games = server.listGames(this.auth.authToken());
        int index = 1;
        for(GameData game : games.games()) {
            String whiteUsername = "N/A";
            String blackUsername = "N/A";
            if(game.whiteUsername()!=null){
                whiteUsername = game.whiteUsername();
            }
            if(game.blackUsername()!=null){
                blackUsername = game.blackUsername();
            }
            System.out.print(index);
            System.out.println(") "+ game.gameName() +"->" + " White player: "+whiteUsername +", Black player: "+blackUsername);
            index++;
        }
        System.out.println();
        help();

    }

    private void createGame() throws ServerException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter game name: ");
        String name = scanner.nextLine();

        GameData game = new GameData(-1, null, null, name, null);
        GameID gameID = server.createGame(game, this.auth.authToken());

        System.out.println();
        System.out.println("Your GameID is: " + gameID.gameID());
        System.out.println("Type join to join game");
        System.out.println();
        help();
    }

    private void logout() throws ServerException {
        server.logout(this.auth.authToken());

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
