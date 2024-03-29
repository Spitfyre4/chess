import server.ServerException;
import ui.PreLoginClient;

public class Main {
    public static void main(String[] args) throws ServerException {
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }

        new PreLoginClient(serverUrl).run();
    }
}
