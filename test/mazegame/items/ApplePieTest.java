package mazegame.items;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import mazegame.entities.*;
import mazegame.items.*;
import mazegame.items.UsableItem;
import mazegame.*;

public class ApplePieTest {
    
    private Player player;
    private Game game;
    private ApplePie applePie;


    @Before
    public void initialize(){
        game = new Game();
        player = new Player("merlin", 15, 5, game);
        applePie = new ApplePie();
        applePie.setEntity(player);
    }

    @Test 
    public void ApplePieHealsTheEntityToFullHealth(){
        player.setHealthPoints(5);
        player.use(applePie);
        assertTrue(player.getHealthPoints() == 15);
    }
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(ApplePieTest.class);
    }
}
