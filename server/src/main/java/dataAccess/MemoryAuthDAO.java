package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{

    final private HashMap<String, AuthData> authTokens = new HashMap<>();

    @Override
    public AuthData createAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        AuthData auth = new AuthData(authToken, username);
        authTokens.put(authToken, auth);

        return auth;
    }

    @Override
    public AuthData getAuth(String authToken) {
        return authTokens.get(authToken);
    }

    @Override
    public void deleteAuth(String authToken) {
        authTokens.remove(authToken);
    }

    @Override
    public Collection<AuthData> listAuths() {
        return authTokens.values();
    }

    @Override
    public void clear() {
        authTokens.clear();
    }
}
