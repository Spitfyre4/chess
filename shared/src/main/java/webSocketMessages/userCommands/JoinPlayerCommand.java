package webSocketMessages.userCommands;

import model.GameID;

public class JoinPlayerCommand extends UserGameCommand{
    public final String username;
    public int gameID;
    public String playerColor;

    public JoinPlayerCommand(String authToken, int gameID, String playerColor, String username) {
        super(authToken);
        this.gameID = gameID;
        this.playerColor = playerColor;
        this.commandType = CommandType.JOIN_PLAYER;
        this.username = username;
    }
}
