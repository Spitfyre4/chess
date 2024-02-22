package serviceTests;

import org.junit.jupiter.api.BeforeEach;
import service.UserService;

public class UserTest {

    private UserService MyUserService;

    @BeforeEach
    public void setup(){
        UserService MyUserService = new UserService();
    }
}
