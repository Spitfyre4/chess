package webSocketMessages.serverMessages;

public class Error extends ServerMessage {
    private final String errorMessage;

    public Error(ServerMessageType type, String message) {
        super(type);
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
