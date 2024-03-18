package server;

/**
 * Indicates there was an error connecting to the database
 */
public class serverException extends Exception{
    private final int statusCode;
    public serverException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    public int getStatusCode() {
        return statusCode;
    }
}