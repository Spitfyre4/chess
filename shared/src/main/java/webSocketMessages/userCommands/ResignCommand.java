package webSocketMessages.userCommands;

import model.GameID;

public class ResignCommand extends UserGameCommand{
    public final String username;
    public int gameID;
    public ResignCommand(String authToken, int gameID, String username) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.RESIGN;
        this.username = username;
    }
}
