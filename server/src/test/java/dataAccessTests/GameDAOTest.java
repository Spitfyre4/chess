package dataAccessTests;

import dataAccess.SqlGameDAO;
import org.junit.jupiter.api.BeforeEach;

public class GameDAOTest {

    public SqlGameDAO userDatabase;

    @BeforeEach
    public void setup() {
        this.userDatabase = new SqlGameDAO();
    }
}
