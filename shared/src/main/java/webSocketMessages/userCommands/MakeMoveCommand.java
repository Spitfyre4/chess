package webSocketMessages.userCommands;

import chess.ChessMove;
import model.GameID;

public class MakeMoveCommand extends UserGameCommand{
    int gameID;
    ChessMove move;
    String playerColor;
    public MakeMoveCommand(String authToken, int gameID, String playerColor, ChessMove move) {
        super(authToken);
        this.gameID = gameID;
        this.move = move;
        this.playerColor = playerColor;
        this.commandType = CommandType.MAKE_MOVE;
    }
}