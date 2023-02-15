package mazegame.items;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import mazegame.entities.*;
import mazegame.items.*;
import mazegame.items.UsableItem;
import mazegame.*;

public class AppleTest {
    
    private Player player;
    private Game game;
    private Apple apple;
    private Apple apple2;


    @Before
    public void initialize(){
        game = new Game();
        player = new Player("merlin", 55, 5, game);
        apple = new Apple();
        apple.setEntity(player);
        apple2 = new Apple();
        apple2.setEntity(player);   
    }

    @Test 
    public void AppleHealsTheEntity(){
        player.setHealthPoints(5);
        player.use(apple);
        assertTrue(player.getHealthPoints() == 30);
        player.use(apple2);
        assertTrue(player.getHealthPoints() == 55);
    }

    @Test 
    public void AppleDoesNotHealMoreThanMaxHealthPoints(){
        player.setHealthPoints(30);
        player.use(apple);
        assertTrue(player.getHealthPoints() == 55);
        player.use(apple2);
        assertTrue(player.getHealthPoints() == 55);
    }

    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(AppleTest.class);
    }
}
