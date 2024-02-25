package dataAccess;

import model.UserData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class MemoryUserDAO implements UserDAO{

    final private HashMap<String, UserData> users = new HashMap<>();

    public UserData createUser(UserData user) throws DataAccessException{
        if (users.containsKey(user.username())) {
            throw new DataAccessException("Error: already taken", 403);
        }

        user = new UserData(user.username(), user.password(), user.email());

        users.put(user.username(), user);
        return user;
    }

    @Override
    public UserData getUser(String username) throws DataAccessException{
        if (!users.containsKey(username)){
            throw new DataAccessException("Error: bad request", 400);
        }

        return users.get(username);
    }

    @Override
    public Collection<UserData> listUsers() throws DataAccessException{
        return users.values();
    }

    @Override
    public boolean userExists(UserData user) throws DataAccessException {
        if (!users.containsKey(user.username())){
            throw new DataAccessException("Error: unauthorized", 401);
        }

        UserData trueUser = getUser(user.username());

        if(!Objects.equals(user.password(), trueUser.password())){
            throw new DataAccessException("Error: unauthorized", 401);
        }

        return true;
    }

    @Override
    public void clear() throws DataAccessException{
        users.clear();
    }
}
