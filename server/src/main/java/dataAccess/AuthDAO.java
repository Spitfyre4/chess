package dataAccess;

import model.AuthData;
import model.UserData;

public interface AuthDAO {

    public AuthData createAuth(String username);

    public AuthData getAuth(String authToken);

    public void deleteAuth(String authToken);

    public void clear();
}