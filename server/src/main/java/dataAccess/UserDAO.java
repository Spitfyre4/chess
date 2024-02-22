package dataAccess;

import model.UserData;

public interface UserDAO {

    public UserData createUser(UserData user);

    void clear();
}
