package serviceTests;

import dataAccess.DataAccessException;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;

public class ClearTest {

    public ClearService MyClearService;

    @BeforeEach
    public void setup(){
        ClearService MyClearService = new ClearService();
    }

    @Test
    public void testClear() throws DataAccessException {
        MyClearService.UserDatabase.createUser
                (new UserData("testname", "testpass", "testemail"));
        MyClearService.UserDatabase.createUser
                (new UserData("testname2", "testpass2", "testemail2"));
        //MyClearService.AuthDatabase issue atm is authtokens get made in the other service
        this.MyClearService.clear();

    }
}
