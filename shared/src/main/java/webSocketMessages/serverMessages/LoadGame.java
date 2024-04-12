package webSocketMessages.serverMessages;

import chess.ChessGame;

public class LoadGame extends ServerMessage{
    public final ChessGame game;
    public final Boolean resign;
    public LoadGame(ServerMessageType type, ChessGame game, Boolean resign) {
        super(type);
        this.game = game;
        this.resign = resign;
    }
}
