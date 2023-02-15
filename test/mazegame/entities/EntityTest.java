package mazegame.entities;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Before.*;

import java.util.HashMap;

import mazegame.entities.*;
import mazegame.items.*;
import mazegame.Game;

public class EntityTest {
    private Game game;
    private Entity player;
    private HashMap<Item, Integer> playerBag;
    private Item gold;

    @Before
    public void initTestEntities() {
        game = new Game();
        player = new Player("LeMonsieurTest", 100, 10, game);
        playerBag = player.getItems();
        gold = new Gold();
    }

    @Test
    public void addItemPutItemWhenNotInBackPack() {
        assertFalse(playerBag.containsKey(gold));
        player.addItem(gold, 5);
        assertTrue(playerBag.containsKey(gold));
    }

    @Test
    public void addItemsUpdateQuantityWhenItemAlreadyPresent() {
        player.addItem(gold, 5);
        assertTrue(playerBag.get(gold) == 5);
        player.addItem(gold, 10);
        assertTrue(playerBag.get(gold) == 15);
    }

    @Test 
    public void removeItemUpdateQuantity() {
        player.addItem(gold, 20);
        assertTrue(playerBag.get(gold) == 20);
        player.removeItem(gold, 10);   
        assertTrue(playerBag.get(gold) == 10); 
        player.removeItem(gold, 20);
        assertFalse(playerBag.containsKey(gold));    
    }

    @Test 
    public void hitChangeTargetHealth () {
        Entity victim = new Villageois(game);
        int victimHealth = victim.getHealthPoints();
        player.hit(victim);
        assertTrue(victim.getHealthPoints() == victimHealth - player.getStrength());
    }


    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(EntityTest.class);
    }
}

