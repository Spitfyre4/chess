package ui;


import model.UserData;
import server.ServerFacade;
import server.ServerException;

import java.util.Scanner;

public class PreLoginClient {

    public final ServerFacade server;

    public PreLoginClient(String url){
        server = new ServerFacade(url);
    }

    public void run() throws ServerException {
        System.out.println("\uD83D\uDC36 Welcome to Caleb's chess game");
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
                case "register" -> register();
                case "quit" -> {
                    run = false;
                }
                default -> help();
            };
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return run;
    }

    public void help() throws ServerException {
        System.out.println("""
                    - register: Make an account
                    - login: Access an existing account
                    - quit: Quit application
                    - help: Reprint commands
                    """);
    }
    public void register() throws ServerException {
        var path = "/user";
        
        server.makeRequest("POST", path, user, UserData.class);
    }
}
