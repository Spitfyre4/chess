package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{

    final private HashMap<String, AuthData> authTokens = new HashMap<>();

    @Override
    public AuthData createAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        AuthData auth = new AuthData(authToken, username);
        authTokens.put(username, auth);

        return auth;
    }

    @Override
    public AuthData getAuth(String authToken) {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) {

    }
    @Override
    public void clear() {
        authTokens.clear();
    }
}
