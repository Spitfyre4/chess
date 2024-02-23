package serviceTests;

import dataAccess.AuthDAO;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import dataAccess.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import service.UserService;

public class UserTest {

    private UserService MyUserService;

    @BeforeEach
    public void setup(){
        UserDAO UserDatabase = new MemoryUserDAO();
        AuthDAO AuthDatabase = new MemoryAuthDAO();
        UserService MyUserService = new UserService(UserDatabase, AuthDatabase);
    }
}
