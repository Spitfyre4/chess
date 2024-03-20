package ui;

import server.ServerException;
import server.ServerFacade;

import java.util.Scanner;

public class GameplayClient {



    public final ServerFacade server;
    public final String url;
    public final int gameID;
    public final String playerColor;
    public boolean run;

    public GameplayClient(String url, int gameID){
        server = new ServerFacade(url);
        this.url = url;
        this.gameID = gameID;
        this.playerColor = null;
        this.run = true;
    }

    public GameplayClient(String url, int gameID, String playerColor){
        server = new ServerFacade(url);
        this.url = url;
        this.gameID = gameID;
        this.playerColor = playerColor;
        this.run = true;
    }

    public void run() throws ServerException {
        printBoard(this.gameID);

        Scanner scanner = new Scanner(System.in);
        while (run) {
            if(playerColor!= null) {
                this.help();
            }
            else{
                System.out.println("You Joined as an observer");
            }
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
                //future cases to be added
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
                    Put all future commands here
                    """);
    }

    public void printBoard(int gameID){
        
    }
}
