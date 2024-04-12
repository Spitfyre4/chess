package websocket;

import chess.*;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.Notification;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.BLACK_KING;

public class GameplayHandler {

    public ChessGame game;
    private boolean run;

    public GameplayHandler(){
        run = true;
    }

    public void updateGame(ChessGame game){
        this.game = game;
    }

    public void printBoard(String playerColor){
        if(Objects.equals(playerColor, "BLACK")){
            printBlack();
        }
        else{
            printWhite();
        }
    }

    public void printWhite(){
        ChessBoard board = game.getBoard();

        for (int i = 0; i <= 7; i++) {
            char letter = (char) ('A' + i);
            System.out.print(" \u2001\u2005\u200A  ");
            System.out.print(letter);
        }
        System.out.println();

        for (int i = 8; i >= 1; i--) {
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

        System.out.println(">>> " + game.getTeamTurn() + "'s turn");
    }

    public void printBlack(){
        ChessBoard board = game.getBoard();

        for (int i = 0; i <= 7; i++) {
            char letter = (char) ('H' - i);
            System.out.print(" \u2001\u2005\u200A  ");
            System.out.print(letter);
        }
        System.out.println();

        for (int i = 1; i <= 8; i++) {
            System.out.print(i + " ");
            for (int j = 8; j >= 1; j--) {
                ChessPiece piece = board.getPiece(new ChessPosition(i, j));
                System.out.print("|");
                if (piece != null) {
                    switch (piece.getPieceType()) {
                        case PAWN ->
                                System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_PAWN : BLACK_PAWN);
                        case ROOK ->
                                System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_ROOK : BLACK_ROOK);
                        case KNIGHT ->
                                System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_KNIGHT : BLACK_KNIGHT);
                        case BISHOP ->
                                System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_BISHOP : BLACK_BISHOP);
                        case QUEEN ->
                                System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_QUEEN : BLACK_QUEEN);
                        case KING ->
                                System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_KING : BLACK_KING);
                    }
                } else {
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

        System.out.println(">>> " + game.getTeamTurn() + "'s turn");
    }

    public void highlight(ChessPiece ogPiece, ChessPosition startPos, Boolean isBlack){
        ChessBoard board = game.getBoard();
        ArrayList<ChessMove> moves = (ArrayList<ChessMove>)ogPiece.pieceMoves(board, startPos);
        ChessMove move;
        ChessMove promoMove;
        ChessPosition endPos;

        for (int i = 0; i <= 7; i++) {
            char letter = (char) ('A' + i);
            System.out.print(" \u2001\u2005\u200A  ");
            System.out.print(isBlack ? (char) ('H' - i) : letter);
        }
        System.out.println();

        for (int i = isBlack ? 1 : 8; isBlack ? i <= 8 : i >= 1; i += isBlack ? 1 : -1) {
            System.out.print(i + " ");
            for(int j = isBlack ? 8 : 1; isBlack ? j >= 1 : j <= 8; j += isBlack ? -1 : 1) {
                endPos = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(endPos);
                move = new ChessMove(startPos, endPos, null);
                promoMove = new ChessMove(startPos, endPos, ChessPiece.PieceType.KING);
                System.out.print("|");
                if(moves.contains(move) || moves.contains(promoMove)){
                    System.out.print(SET_BG_COLOR_DARK_GREEN);
                }
                if (piece != null) {
                    switch (piece.getPieceType()) {
                        case PAWN -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_PAWN : BLACK_PAWN);
                        case ROOK -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_ROOK : BLACK_ROOK);
                        case KNIGHT -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_KNIGHT : BLACK_KNIGHT);
                        case BISHOP -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_BISHOP : BLACK_BISHOP);
                        case QUEEN -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_QUEEN : BLACK_QUEEN);
                        case KING -> System.out.print(piece.pieceColor == ChessGame.TeamColor.WHITE ? WHITE_KING : BLACK_KING);
                    }
                    System.out.print(RESET_BG_COLOR);
                }
                else{
                    System.out.print(" \u2001\u2005\u200A ");
                    System.out.print(RESET_BG_COLOR);
                }
                System.out.print("|");
            }
            System.out.print(" " + i);
            System.out.println();
        }
        for (int i = 0; i <= 7; i++) {
            char letter = (char) ('A' + i);
            System.out.print(" \u2001\u2005\u200A  ");
            System.out.print(isBlack ? (char) ('H' - i) : letter);
        }
        System.out.println();

        System.out.println();

        System.out.println(">>> " + game.getTeamTurn() + "'s turn");
    }

    public void send(Notification notificationMessage) {
        System.out.println(notificationMessage.getMessage());
    }

    public void error(Error errorMessage) {
        System.out.println(errorMessage.getErrorMessage());
    }


    public void checkWin() {
        ChessGame.TeamColor blackUser = ChessGame.TeamColor.BLACK;
        ChessGame.TeamColor whiteUser = ChessGame.TeamColor.WHITE;

        if(game.isInCheckmate(blackUser)){
            endGame();
            return;
        }

        if(game.isInCheckmate(whiteUser)){
            endGame();
            return;
        }

        if(game.isInStalemate(blackUser) || game.isInStalemate(whiteUser)){
            endGame();
        }
    }

    public boolean isGameRunning(){
        return run;
    }

    public void endGame() {
        run = false;
    }

    public void confirmLeave() {
        Boolean stay = true;
        Scanner scanner = new Scanner(System.in);
        endGame();

        while(stay){
            System.out.println("Would you like to leave the game?(Yes/No)");
            String input = scanner.nextLine();
            input = input.toLowerCase();
            if(input.equals("yes")){
                stay = false;
            }
        }

    }
}
