package service;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import spark.Request;
import spark.Response;

public class ClearHandler {

    public final ClearService myClearService;

    public ClearHandler(ClearService myClearService){
        this.myClearService = myClearService;
    }

    public Object clear(Request req, Response res) throws DataAccessException {
        myClearService.clear();
        res.status(200);
        return "{}";
    }
}
