package ui;


import model.AuthData;
import model.UserData;
import server.ServerFacade;
import server.ServerException;

import java.util.Scanner;

public class PostLoginClient {

    public final ServerFacade server;
    public final String url;
    public final AuthData auth;

//    public final GameplayClient gameplay;

    public PostLoginClient(String url, AuthData auth) {
        server = new ServerFacade(url);
        this.url = url;
        this.auth = auth;
    }

    public void run() throws ServerException {
        this.help();

        Scanner scanner = new Scanner(System.in);
        var run = true;
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
        boolean run = true;
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            switch (cmd) {
//                case "register" -> register();
//                case "login" -> login();
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

    public void help() throws ServerException {
        System.out.println("""
                    - logout: Logout of account
                    - create game: Make a new chess game
                    - list games: List all chess games
                    - join game: Join an existing chess game
                    - watch: Watch an existing chess game
                    - quit: Quit application
                    - help: Reprint commands
                    """);
    }
}
