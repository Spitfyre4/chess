package dataAccessTests;

import chess.ChessGame;
import dataAccess.*;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    public SqlUserDAO userDatabase;

    @BeforeEach
    public void setup() {
        this.userDatabase = new SqlUserDAO();
    }
}
