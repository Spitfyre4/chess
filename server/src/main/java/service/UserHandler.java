package service;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import spark.Request;
import spark.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserHandler {

    public final UserService myUserService;

    public UserHandler(UserService myUserService){
        this.myUserService = myUserService;
    }

    public Object register(Request req, Response res) throws DataAccessException {
        var user = new Gson().fromJson(req.body(), UserData.class);

        AuthData auth = myUserService.register(user);
        res.status(200);
        return new Gson().toJson(auth);
    }

    public Object login(Request req, Response res) throws DataAccessException {
        var user = new Gson().fromJson(req.body(), UserData.class);

        AuthData auth = myUserService.login(user);
        res.status(200);
        return new Gson().toJson(auth);
    }

    public Object logout(Request req, Response res) throws DataAccessException {
        return "not ready yet";
    }


}
