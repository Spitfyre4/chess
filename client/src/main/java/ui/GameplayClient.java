package ui;

import chess.*;
import exception.ServerException;
import model.AuthData;
import server.ServerFacade;
import websocket.GameplayHandler;
import websocket.WebSocketFacade;

import java.util.*;

public class GameplayClient {
    public final ServerFacade server;
    public final String url;
    public final int gameID;
    public final String playerColor;
    public final GameplayHandler gameplay;
    public AuthData auth;
    public WebSocketFacade ws;

    //Observer joins
    public GameplayClient(String url, int gameID, AuthData auth) throws ServerException {
        server = new ServerFacade(url);
        this.gameplay = new GameplayHandler();
        this.url = url;
        this.gameID = gameID;
        this.playerColor = null;
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
            this.observerHelp();
            observer = true;
        }

        while (gameplay.isGameRunning()) {

            String line = scanner.nextLine();
            try {
                if (observer) {
                    this.observeEval(line);
                } else {
                    this.eval(line);
                }
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
            System.out.println();
        }
        System.out.println();

    }

    public void eval(String input) {

        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            switch (cmd) {
                case "redraw" -> redraw();
                case "move" -> movePiece();
                case "highlight" -> highlight();
                case "resign" -> resign();
                case "leave" -> {
                    gameplay.endGame();
                    this.ws.leave(auth.authToken(), gameID, auth.username());
                }
                case "quit" -> {
                    gameplay.endGame();
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                default -> help();
            }

        } catch (ServerException | InvalidMoveException e) {
            System.out.println(e.getMessage());
        }
    }

    private void redraw() throws ServerException {
        if(!gameplay.isGameRunning()){
            gameplay.confirmLeave();
            return;
        }
        this.ws.reprintBoard();
    }

    private void movePiece() throws ServerException, InvalidMoveException {
        if(!gameplay.isGameRunning()){
            gameplay.confirmLeave();
            return;
        }
        Scanner scanner = new Scanner(System.in);
        boolean startPiece = false;
        boolean endPosBool = false;
        ChessPosition startPos = null;
        ChessMove moveFinal = null;


        ChessPiece piece = null;

        if(!Objects.equals(gameplay.game.getTeamTurn().toString(), playerColor)){
            System.out.println("Wait your turn");
            return;
        }
            System.out.println("What is the column of the piece you would like to move?");
            String startColLetter = scanner.nextLine();
            if (startColLetter.length() != 1 || !startColLetter.matches("[a-zA-Z]+")) {
                System.out.println("Enter a valid letter");
                return;
            }
            int startCol = startColLetter.charAt(0) - 'a' + 1;

            System.out.println("What is the row of the piece you would like to move?");
            int startRow = scanner.nextInt();
            if(startRow < 1 || startRow > 8){
                System.out.println("Please enter a number between 1-8");
                return;
            }

            startPos = new ChessPosition(startRow, startCol);

            piece = gameplay.game.getBoard().getPiece(startPos);

            if (piece != null && Objects.equals(piece.getTeamColor().toString(), playerColor)) {
                startPiece = true;
            } else {
                System.out.println("There is no " + playerColor + " piece there");
                return;
            }

            ArrayList<ChessMove> moves = (ArrayList<ChessMove>)piece.pieceMoves(gameplay.game.getBoard(), startPos);
            if (moves.isEmpty()){
                System.out.println("Your " + piece.getPieceType() + " has no possible moves");
                return;
            }
            int index = 1;
            System.out.println("Where would you like to move your " + piece.getPieceType() + "?");
            System.out.println("Your possible moves are:");
            Set<ChessMove> moveSet = new HashSet<>(moves);
            moves = new ArrayList<>(moveSet);
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
            if(moveIndex > moves.size() || moveIndex < 1) {
                System.out.println("Invalid input");
                return;
            }

            moveFinal = moves.get(moveIndex-1);
            endPosBool = true;


        this.ws.makeMove(this.auth.authToken(), this.gameID, this.playerColor, moveFinal);
    }


    private void highlight() throws ServerException {
        if(!gameplay.isGameRunning()){
            gameplay.confirmLeave();
            return;
        }
        Scanner scanner = new Scanner(System.in);
        ChessPosition startPos = null;
        System.out.println("What is the column of the piece's moves you would like to highlight?");
        String startColLetter = scanner.nextLine();
        if (startColLetter.length() != 1 || !startColLetter.matches("[a-zA-Z]+")) {
            System.out.println("Enter a valid letter");
            return;
        }
        int startCol = startColLetter.charAt(0) - 'a' + 1;

        System.out.println("And the row?");
        int startRow = scanner.nextInt();

        startPos = new ChessPosition(startRow, startCol);

        ChessPiece piece = gameplay.game.getBoard().getPiece(startPos);

        if (piece != null && Objects.equals(piece.getTeamColor(), gameplay.game.getTeamTurn())) {
            if(playerColor!= null && playerColor.equals("BLACK")) {
                gameplay.highlight(piece, startPos, true);
            }
            else{
                gameplay.highlight(piece,startPos, false);
            }
        } else {
            System.out.println("There is no " + playerColor + " piece there");
        }
    }

    private void resign() throws ServerException {
        if(!gameplay.isGameRunning()){
            gameplay.confirmLeave();
            return;
        }

        String op = "WHITE";
        if (playerColor.equals("WHITE")) {
            op = "BLACK";
        }

        System.out.println("You have resigned");
        this.ws.resign(auth.authToken(), gameID, auth.username(), playerColor);
        System.out.println(op + " has won!");

        gameplay.confirmLeave();
    }

    public void help() throws ServerException {
        if(!gameplay.isGameRunning()){
            gameplay.confirmLeave();
            return;
        }
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

    public void observeEval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            switch (cmd) {
                case "leave" -> {
                    gameplay.endGame();
                    this.ws.leave(auth.authToken(), gameID, auth.username());
                }
                case "quit" -> {
                    gameplay.endGame();
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                case "highlight" -> highlight();
                case "redraw" -> redraw();
                default -> observerHelp();
            }

        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void observerHelp() throws ServerException {
        if(!gameplay.isGameRunning()){
            gameplay.confirmLeave();
            return;
        }
        System.out.println("""
                - redraw: Redraw the chess board
                - highlight: Highlight all legal moves
                - leave: Leave game
                - quit: Quit application
                - help: Reprint commands
                """);
    }
}
