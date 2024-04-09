package webSocketMessages.userCommands;

import model.GameID;

public class ResignCommand extends UserGameCommand{
    int gameID;
    public ResignCommand(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.RESIGN;
    }
}
