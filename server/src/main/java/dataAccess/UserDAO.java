package dataAccess;

import model.UserData;

public interface UserDAO {

    public UserData createUser(UserData user);

    public UserData getUser(String username);

    public void clear();
}
