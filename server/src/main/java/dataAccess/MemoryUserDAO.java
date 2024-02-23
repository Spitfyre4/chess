package dataAccess;

import model.UserData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryUserDAO implements UserDAO{

    final private HashMap<String, UserData> users = new HashMap<>();

    public UserData createUser(UserData user){
        user = new UserData(user.username(), user.password(), user.email());

        users.put(user.username(), user);
        return user;
    }

    @Override
    public UserData getUser(String username) {
        return users.get(username);
    }

    @Override
    public Collection<UserData> listUsers() {
        return users.values();
    }

    @Override
    public void clear() {
        users.clear();
    }
}
