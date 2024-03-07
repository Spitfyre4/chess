package dataAccessTests;

import dataAccess.SqlAuthDAO;
import org.junit.jupiter.api.BeforeEach;

public class AuthDAOTest {

    public SqlAuthDAO userDatabase;

    @BeforeEach
    public void setup() {
        this.userDatabase = new SqlAuthDAO();
    }
}
