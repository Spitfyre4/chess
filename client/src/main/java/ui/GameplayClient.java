package ui;

import chess.*;
import exception.ServerException;
import model.AuthData;
import server.ServerFacade;
import webSocketMessages.serverMessages.LoadGameMessage;
import websocket.GameplayHandler;
import websocket.WebSocketFacade;

import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;

import static ui.EscapeSequences.*;

public class GameplayClient {
    public final ServerFacade server;
    public final String url;
    public final int gameID;
    public final String playerColor;
    public final GameplayHandler gameplay;
    public boolean run;
    public AuthData auth;
    public WebSocketFacade ws;

    //Observer joins
    public GameplayClient(String url, int gameID, AuthData auth) throws ServerException {
        server = new ServerFacade(url);
        this.gameplay = new GameplayHandler();
        this.url = url;
        this.gameID = gameID;
        this.playerColor = null;
        this.run = true;
        this.ws = new WebSocketFacade(url, this.gameplay);
        this.auth = auth;
        this.ws.joinObserver(auth.authToken(), gameID, auth.username());
    }

    //Player joins
    public GameplayClient(String url, int gameID, String playerColor, AuthData auth) throws ServerException {
        server = new ServerFacade(url);
        this.url = url;
        this.gameID = gameID;
        this.playerColor = playerColor;
        this.gameplay = new GameplayHandler();
        this.run = true;
        this.ws = new WebSocketFacade(url, this.gameplay);
        this.auth = auth;
        this.ws.joinPlayer(auth.authToken(), gameID, playerColor, auth.username());
    }

    public void run() throws ServerException {
        System.out.println();
//        printWhiteBoard(this.gameID);
//        printBlackBoard(this.gameID);
        boolean observer = false;

        Scanner scanner = new Scanner(System.in);
        System.out.println();
        if (playerColor != null) {
            this.help();
        } else {
            System.out.println("You joined as an observer");
            this.ObserverHelp();
            observer = true;
        }

        while (run) {
            String line = scanner.nextLine();
            try {
                if (observer) {
                    run = this.ObserveEval(line);
                } else {
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
                    this.ws.leave(auth.authToken(), gameID, auth.username());
                }
                case "quit" -> {
                    run = false;
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                default -> help();
            }

        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
        return run;
    }

    private void redraw() throws ServerException {
        this.ws.reprintBoard();
    }

    private void movePiece() throws ServerException {
        Scanner scanner = new Scanner(System.in);
        boolean startPiece = false;
        boolean endPosBool = false;
        ChessPosition startPos = null;
        ChessPosition endPos = null;
        ChessMove moveFinal = null;

        ChessPiece piece = null;

        while (!startPiece) {
            startPos = null;
            System.out.println("What is the column of the piece you would like to move?");
            String startColLetter = scanner.nextLine();
            if (startColLetter.length() != 1 && !startColLetter.matches("[a-zA-Z]+")) {
                System.out.println("Enter a valid letter");
                continue;
            }
            int startCol = startColLetter.charAt(0) - 'a' + 1;

            System.out.println("What is the row of the piece you would like to move?");
            int startRow = scanner.nextInt();

            startPos = new ChessPosition(startRow, startCol);

            piece = gameplay.game.getBoard().getPiece(startPos);

            if (piece != null && Objects.equals(piece.getTeamColor().toString(), playerColor)) {
                startPiece = true;
            } else {
                System.out.println("There is no " + playerColor + " piece there");
            }
        }

        while(!endPosBool){
            endPos = null;
            ArrayList<ChessMove> moves = (ArrayList<ChessMove>)piece.pieceMoves(gameplay.game.getBoard(), startPos);
            int index = 1;
            System.out.println("Where would you like to move your " + piece.getPieceType() + "?");
            System.out.println("Your possible moves are:");
            for (ChessMove move : moves){
                System.out.print(index + ") " + move.getEndPosition().toMove());
                if (move.getPromotionPiece() != null){
                    System.out.println(" promoting to a " + move.getPromotionPiece().toString());
                }
                else{
                    System.out.println();
                }
                index++;
            }

            int moveIndex = scanner.nextInt();
            if(moveIndex > moves.size() || moveIndex < 1){
                System.out.println("Invalid input");
                continue;
            }

            moveFinal = moves.get(moveIndex-1);
            endPosBool = true;
        }

        this.ws.makeMove(this.auth.authToken(), this.gameID, this.playerColor, moveFinal);
    }

    private void highlight() throws ServerException {
    }

    private void resign() throws ServerException {
        this.ws.resign(auth.authToken(), gameID, auth.username());
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
                    this.ws.leave(auth.authToken(), gameID, auth.username());
                }
                case "quit" -> {
                    run = false;
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                default -> ObserverHelp();
            }

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
}

//    public void printWhiteBoard(int gameID){
//        ChessBoard board = new ChessBoard();
//        board.resetBoard(); //just to populate the board
//
//        for (int i = 0; i <= 7; i++) {
//            char letter = (char) ('A' + i);
//            System.out.print(" \u2001\u2005\u200A  ");
//            System.out.print(letter);
//        }
//        System.out.println();
//
//        for(int i = 1; i<=8; i++){
//            System.out.print(i + " ");
//            for(int j = 1; j<=8; j++) {
//                ChessPiece piece = board.getPiece(new ChessPosition(i, j));
//                System.out.print("|");
//                if (piece != null) {
//                    switch (piece.getPieceType()) {
//                        case PAWN -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_PAWN : BLACK_PAWN);
//                        case ROOK -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_ROOK : BLACK_ROOK);
//                        case KNIGHT -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_KNIGHT : BLACK_KNIGHT);
//                        case BISHOP -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_BISHOP : BLACK_BISHOP);
//                        case QUEEN -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_QUEEN : BLACK_QUEEN);
//                        case KING -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_KING : BLACK_KING);
//                    }
//                }
//                else{
//                    System.out.print(" \u2001\u2005\u200A ");
//                }
//                System.out.print("|");
//            }
//            System.out.print(" " + i);
//            System.out.println();
//        }
//        for (int i = 0; i <= 7; i++) {
//            char letter = (char) ('A' + i);
//            System.out.print(" \u2001\u2005\u200A  ");
//            System.out.print(letter);
//        }
//        System.out.println();
//
//        System.out.println();
//    }

//    public void printBlackBoard(int gameID){
//        ChessBoard board = new ChessBoard();
//        board.resetBoard(); //just to populate the board
//
//        for (int i = 0; i <= 7; i++) {
//            char letter = (char) ('H' - i);
//            System.out.print(" \u2001\u2005\u200A  ");
//            System.out.print(letter);
//        }
//        System.out.println();
//
//        for(int i = 8; i>=1; i--){
//            System.out.print(i + " ");
//            for(int j = 8; j>=1; j--) {
//                ChessPiece piece = board.getPiece(new ChessPosition(i, j));
//                System.out.print("|");
//                if (piece != null) {
//                    switch (piece.getPieceType()) {
//                        case PAWN -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_PAWN : BLACK_PAWN);
//                        case ROOK -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_ROOK : BLACK_ROOK);
//                        case KNIGHT -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_KNIGHT : BLACK_KNIGHT);
//                        case BISHOP -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_BISHOP : BLACK_BISHOP);
//                        case QUEEN -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_QUEEN : BLACK_QUEEN);
//                        case KING -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_KING : BLACK_KING);
//                    }
//                }
//                else{
//                    System.out.print(" \u2001\u2005\u200A ");
//                }
//                System.out.print("|");
//            }
//            System.out.print(" " + i);
//            System.out.println();
//        }
//        for (int i = 0; i <= 7; i++) {
//            char letter = (char) ('H' - i);
//            System.out.print(" \u2001\u2005\u200A  ");
//            System.out.print(letter);
//        }
//        System.out.println();
//
//        System.out.println();
//    }
//}
