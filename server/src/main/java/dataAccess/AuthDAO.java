package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.Collection;

public interface AuthDAO {

    public AuthData createAuth(String username);

    public AuthData getAuth(String authToken);

    public void deleteAuth(String authToken);

    public Collection<AuthData> listAuths();

    public void clear();
}