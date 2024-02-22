package serviceTests;

import org.junit.jupiter.api.BeforeEach;
import service.GameService;

public class GameTest {

    @BeforeEach
    public void setup(){
        GameService MyGameService = new GameService();
    }
}
