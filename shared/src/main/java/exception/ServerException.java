package exception;

/**
 * Indicates there was an error connecting to the database
 */
public class ServerException extends Exception{
    private final int statusCode;
    public ServerException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    public int getStatusCode() {
        return statusCode;
    }
}