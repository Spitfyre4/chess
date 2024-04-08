package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import exception.ServerException;
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

    public WebSocketFacade ws;

    public GameplayClient(String url, int gameID, WebSocketFacade ws){
        server = new ServerFacade(url);
        this.url = url;
        this.gameID = gameID;
        this.playerColor = null;
        this.run = false;
        this.ws = ws;
    }

    public GameplayClient(String url, int gameID, String playerColor, WebSocketFacade ws){
        server = new ServerFacade(url);
        this.url = url;
        this.gameID = gameID;
        this.playerColor = playerColor;
        this.run = true;
        this.ws = ws;
    }

    public void run() throws ServerException {
        System.out.println();
        printWhiteBoard(this.gameID);
        printBlackBoard(this.gameID);

        Scanner scanner = new Scanner(System.in);
        System.out.println();
        if(playerColor!= null) {
            this.help();
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
        else{
            System.out.println("You joined as an observer");
            while (run) {
                String line = scanner.nextLine();
                try {
                    run = this.ObserveEval(line);
                } catch (Throwable e) {
                    var msg = e.toString();
                    System.out.print(msg);
                }
                System.out.println();
            }
            System.out.println();
        }

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

    private void redraw() {
    }

    private void movePiece() {
    }

    private void highlight() {
    }

    private void resign() {
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
