package webSocketMessages.userCommands;

import model.GameID;

public class LeaveCommand extends UserGameCommand{
    public int gameID;
    public LeaveCommand(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.LEAVE;
    }
}
