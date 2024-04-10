package websocket;

import chess.*;
import webSocketMessages.serverMessages.NotificationMessage;

import java.util.ArrayList;
import java.util.Objects;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.BLACK_KING;

public class GameplayHandler {

    public ChessGame game;

    public GameplayHandler(){
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
                        case PAWN -> System.out.print(piece.pieceColor == ChessGame.TeamColor.BLACK ? WHITE_PAWN : BLACK_PAWN);
                        case ROOK -> System.out.print(piece.pieceColor == ChessGame.TeamColor.BLACK ? WHITE_ROOK : BLACK_ROOK);
                        case KNIGHT -> System.out.print(piece.pieceColor == ChessGame.TeamColor.BLACK ? WHITE_KNIGHT : BLACK_KNIGHT);
                        case BISHOP -> System.out.print(piece.pieceColor == ChessGame.TeamColor.BLACK ? WHITE_BISHOP : BLACK_BISHOP);
                        case QUEEN -> System.out.print(piece.pieceColor == ChessGame.TeamColor.BLACK ? WHITE_QUEEN : BLACK_QUEEN);
                        case KING -> System.out.print(piece.pieceColor == ChessGame.TeamColor.BLACK ? WHITE_KING : BLACK_KING);
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
                                System.out.print(piece.pieceColor == ChessGame.TeamColor.BLACK ? WHITE_PAWN : BLACK_PAWN);
                        case ROOK ->
                                System.out.print(piece.pieceColor == ChessGame.TeamColor.BLACK ? WHITE_ROOK : BLACK_ROOK);
                        case KNIGHT ->
                                System.out.print(piece.pieceColor == ChessGame.TeamColor.BLACK ? WHITE_KNIGHT : BLACK_KNIGHT);
                        case BISHOP ->
                                System.out.print(piece.pieceColor == ChessGame.TeamColor.BLACK ? WHITE_BISHOP : BLACK_BISHOP);
                        case QUEEN ->
                                System.out.print(piece.pieceColor == ChessGame.TeamColor.BLACK ? WHITE_QUEEN : BLACK_QUEEN);
                        case KING ->
                                System.out.print(piece.pieceColor == ChessGame.TeamColor.BLACK ? WHITE_KING : BLACK_KING);
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

    public void highlightWhite(ChessPiece ogPiece, ChessPosition startPos){
        ChessBoard board = game.getBoard();
        ArrayList<ChessMove> moves = (ArrayList<ChessMove>)ogPiece.pieceMoves(board, startPos);
        ChessMove move;
        ChessMove promoMove;
        ChessPosition endPos;

        for (int i = 0; i <= 7; i++) {
            char letter = (char) ('A' + i);
            System.out.print(" \u2001\u2005\u200A  ");
            System.out.print(letter);
        }
        System.out.println();

        for (int i = 8; i >= 1; i--) {
            System.out.print(i + " ");
            for(int j = 1; j<=8; j++) {
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
                        case PAWN -> System.out.print(piece.pieceColor == ChessGame.TeamColor.BLACK ? WHITE_PAWN : BLACK_PAWN);
                        case ROOK -> System.out.print(piece.pieceColor == ChessGame.TeamColor.BLACK ? WHITE_ROOK : BLACK_ROOK);
                        case KNIGHT -> System.out.print(piece.pieceColor == ChessGame.TeamColor.BLACK ? WHITE_KNIGHT : BLACK_KNIGHT);
                        case BISHOP -> System.out.print(piece.pieceColor == ChessGame.TeamColor.BLACK ? WHITE_BISHOP : BLACK_BISHOP);
                        case QUEEN -> System.out.print(piece.pieceColor == ChessGame.TeamColor.BLACK ? WHITE_QUEEN : BLACK_QUEEN);
                        case KING -> System.out.print(piece.pieceColor == ChessGame.TeamColor.BLACK ? WHITE_KING : BLACK_KING);
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
            System.out.print(letter);
        }
        System.out.println();

        System.out.println();

        System.out.println(">>> " + game.getTeamTurn() + "'s turn");
    }

    public void send(NotificationMessage notificationMessage) {
        System.out.println(notificationMessage.getMessage());
    }
}
