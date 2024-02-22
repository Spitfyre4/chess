package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{

    final private HashMap<String, AuthData> authTokens = new HashMap<>();

    @Override
    public AuthData createAuth(String username) {
        String authtoken = UUID.randomUUID().toString();
        AuthData auth = new AuthData(authtoken, username);
        authTokens.put(username, auth);

        return auth;
    }

    public void clear() {}
}
