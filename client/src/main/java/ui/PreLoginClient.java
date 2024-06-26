package ui;


import model.UserData;
import server.ServerFacade;
import exception.ServerException;

import java.util.Scanner;

public class PreLoginClient {

    public final ServerFacade server;
    public final String url;

    public PostLoginClient postLogin;

    public PreLoginClient(String url){
        server = new ServerFacade(url);
        this.url = url;
    }

    public void run() throws ServerException {
        System.out.println("\uD83D\uDC38 Welcome to Caleb's chess game");
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
            System.out.println();
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
                case "login" -> login();
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
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        UserData user = new UserData(username, password, email);
        var auth = server.register(user);

        this.postLogin = new PostLoginClient(this.url, auth);
        this.postLogin.run();
        this.help();
    }

    public void login() throws ServerException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        UserData user = new UserData(username, password, null);
        var auth = server.login(user);

        this.postLogin = new PostLoginClient(this.url, auth);
        this.postLogin.run();
        this.help();
    }
}
