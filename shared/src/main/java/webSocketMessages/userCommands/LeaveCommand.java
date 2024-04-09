package webSocketMessages.userCommands;

import model.GameID;

public class LeaveCommand extends UserGameCommand{
    private final String username;
    public int gameID;
    public LeaveCommand(String authToken, int gameID, String username) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.LEAVE;
        this.username = username;
    }
}
