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

        if(user.username() == null || user.password() == null || user.email() == null){
            res.status(400);
            return "Error: bad request";
        }

        try {
            AuthData auth = myUserService.register(user);
            res.status(200);
            return new Gson().toJson(auth);
        } catch (DataAccessException e) {
            if(e.getMessage().equals("Error: already taken")){ //do errors in error class on server
                res.status(403);
                return "Error: already taken";
            }
        }

        res.status(500);
        return "Error: Unknown";
    }


}
