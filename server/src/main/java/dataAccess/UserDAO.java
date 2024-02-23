package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.Collection;

public interface UserDAO {

    public UserData createUser(UserData user);

    public UserData getUser(String username);

    public Collection<UserData> listUsers();

    public void clear();
}
