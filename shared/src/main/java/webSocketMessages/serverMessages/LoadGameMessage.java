package webSocketMessages.serverMessages;

import chess.ChessGame;

public class LoadGameMessage extends ServerMessage{
    public final ChessGame game;
    public final Boolean resign;
    public LoadGameMessage(ServerMessageType type, ChessGame game, Boolean resign) {
        super(type);
        this.game = game;
        this.resign = resign;
    }
}
