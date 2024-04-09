package webSocketMessages.userCommands;

import model.GameID;

public class JoinObserverCommand extends UserGameCommand{
    public final String username;
    public int gameID;
    public JoinObserverCommand(String authToken, int gameID, String username) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.JOIN_OBSERVER;
        this.username = username;
    }
}
