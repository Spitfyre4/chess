package webSocketMessages.userCommands;

import model.GameID;

public class JoinObserverCommand extends UserGameCommand{
    public int gameID;
    public JoinObserverCommand(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.JOIN_OBSERVER;
    }
}
