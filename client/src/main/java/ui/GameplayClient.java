package ui;

import chess.*;
import exception.ServerException;
import model.AuthData;
import server.ServerFacade;
import websocket.WebSocketFacade;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class GameplayClient {
    public final ServerFacade server;
    public final String url;
    public final int gameID;
    public final String playerColor;
    public boolean run;
    public AuthData auth;

    public WebSocketFacade ws;

    //Observer joins
    public GameplayClient(String url, int gameID, AuthData auth) throws ServerException {
        server = new ServerFacade(url);
        this.url = url;
        this.gameID = gameID;
        this.playerColor = null;
        this.run = false;
        this.ws = new WebSocketFacade(url);
        this.auth = auth;
        this.ws.joinObserver(auth.authToken(), gameID);
    }

    //Player joins
    public GameplayClient(String url, int gameID, String playerColor, AuthData auth) throws ServerException {
        server = new ServerFacade(url);
        this.url = url;
        this.gameID = gameID;
        this.playerColor = playerColor;
        this.run = true;
        this.ws = new WebSocketFacade(url);
        this.auth = auth;
        this.ws.joinPlayer(auth.authToken(), gameID, playerColor);
    }

    public void run() throws ServerException {
        System.out.println();
        printWhiteBoard(this.gameID);
        printBlackBoard(this.gameID);
        boolean observer = false;

        Scanner scanner = new Scanner(System.in);
        System.out.println();
        if(playerColor!= null) {
            this.help();
        }
        else{
            System.out.println("You joined as an observer");
            this.ObserverHelp();
            observer = true;
        }

        while (run) {
            String line = scanner.nextLine();
            try {
                if(observer){
                    run = this.ObserveEval(line);
                }
                else{
                    run = this.eval(line);
                }
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
            System.out.println();
        }
        System.out.println();

    }

    public boolean eval(String input) {

        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            switch (cmd) {
                case "redraw" -> redraw();
                case "move" -> movePiece();
                case "highlight" -> highlight();
                case "resign" -> resign();
                case "leave" -> {
                    run = false;
                    this.ws.leave(auth.authToken(), gameID);
                }
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

    private void redraw() throws ServerException {
    }

    private void movePiece() throws ServerException {
        ChessPosition startPos = new ChessPosition(1,1);
        ChessPosition endPos = new ChessPosition(1,1);
        ChessMove move = new ChessMove(startPos, endPos, null);
        this.ws.makeMove(auth.authToken(), gameID, playerColor, move);
    }

    private void highlight() throws ServerException {
    }

    private void resign() throws ServerException {
        this.ws.resign(auth.authToken(), gameID);
    }

    public void help() throws ServerException {
        System.out.println("""
                    - redraw: Redraw the chess board
                    - move: Allows you to make a move
                    - highlight: Highlight all legal moves
                    - resign: Forfeit the game and the game
                    - leave: Leave game
                    - quit: Quit application
                    - help: Reprint commands
                    """);
    }

    public boolean ObserveEval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            switch (cmd) {
                case "leave" -> {
                    run = false;
                    this.ws.leave(auth.authToken(), gameID);
                }
                case "quit" -> {
                    run = false;
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                default -> ObserverHelp();
            }
            ;
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return run;
    }

    public void ObserverHelp() throws ServerException {
        System.out.println("""
                    - leave: Leave game
                    - quit: Quit application
                    - help: Reprint commands
                    """);
    }

    public void printWhiteBoard(int gameID){
        ChessBoard board = new ChessBoard();
        board.resetBoard(); //just to populate the board

        for (int i = 0; i <= 7; i++) {
            char letter = (char) ('A' + i);
            System.out.print(" \u2001\u2005\u200A  ");
            System.out.print(letter);
        }
        System.out.println();

        for(int i = 1; i<=8; i++){
            System.out.print(i + " ");
            for(int j = 1; j<=8; j++) {
                ChessPiece piece = board.getPiece(new ChessPosition(i, j));
                System.out.print("|");
                if (piece != null) {
                    switch (piece.getPieceType()) {
                        case PAWN -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_PAWN : BLACK_PAWN);
                        case ROOK -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_ROOK : BLACK_ROOK);
                        case KNIGHT -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_KNIGHT : BLACK_KNIGHT);
                        case BISHOP -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_BISHOP : BLACK_BISHOP);
                        case QUEEN -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_QUEEN : BLACK_QUEEN);
                        case KING -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_KING : BLACK_KING);
                    }
                }
                else{
                    System.out.print(" \u2001\u2005\u200A ");
                }
                System.out.print("|");
            }
            System.out.print(" " + i);
            System.out.println();
        }
        for (int i = 0; i <= 7; i++) {
            char letter = (char) ('A' + i);
            System.out.print(" \u2001\u2005\u200A  ");
            System.out.print(letter);
        }
        System.out.println();

        System.out.println();
    }

    public void printBlackBoard(int gameID){
        ChessBoard board = new ChessBoard();
        board.resetBoard(); //just to populate the board

        for (int i = 0; i <= 7; i++) {
            char letter = (char) ('H' - i);
            System.out.print(" \u2001\u2005\u200A  ");
            System.out.print(letter);
        }
        System.out.println();

        for(int i = 8; i>=1; i--){
            System.out.print(i + " ");
            for(int j = 8; j>=1; j--) {
                ChessPiece piece = board.getPiece(new ChessPosition(i, j));
                System.out.print("|");
                if (piece != null) {
                    switch (piece.getPieceType()) {
                        case PAWN -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_PAWN : BLACK_PAWN);
                        case ROOK -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_ROOK : BLACK_ROOK);
                        case KNIGHT -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_KNIGHT : BLACK_KNIGHT);
                        case BISHOP -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_BISHOP : BLACK_BISHOP);
                        case QUEEN -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_QUEEN : BLACK_QUEEN);
                        case KING -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_KING : BLACK_KING);
                    }
                }
                else{
                    System.out.print(" \u2001\u2005\u200A ");
                }
                System.out.print("|");
            }
            System.out.print(" " + i);
            System.out.println();
        }
        for (int i = 0; i <= 7; i++) {
            char letter = (char) ('H' - i);
            System.out.print(" \u2001\u2005\u200A  ");
            System.out.print(letter);
        }
        System.out.println();

        System.out.println();
    }
}
