package mazegame.entities;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Before.*;

import java.util.HashMap;

import mazegame.entities.*;
import mazegame.items.*;
import mazegame.Game;

public class BishopTest{
    private Game game;
    private Bishop bishop;
    private Player player;
    private Gold gold;

    @Before
    public void initalize(){
        game = new Game();
        bishop = new Bishop(10, 10, game);
        player = new Player("merlin", 15, 5, game);
        gold = new Gold();
        player.addItem(gold, 30);
        player.setHealthPoints(5);
    }

    @Test 
    public void healMethodHealsTheCorrectAmountOfHps(){
        bishop.heal("5");
        assertEquals(player.getHealthPoints(), 10);
        bishop.heal("10");
        assertEquals(player.getHealthPoints(), 15);
    }

    @Test 
    public void healMethodRemovesTheCorrectAmountOfGold(){
        bishop.heal("5");
        assertTrue(player.getItems().get(gold) == 25);
        bishop.heal("25");
        assertFalse(player.getItems().containsKey(new Gold()));
    }

    @Test 
    public void playerCannotHealIfHeDoesHaveAnyGold(){
        player.removeItem(gold, 30);
        bishop.heal("5");
        assertEquals(player.getHealthPoints(), 5);
    }

    @Test 
    public void playerCannotHealIfHeDoesNotHavecEnoughGold(){
        bishop.heal("35");
        assertTrue(player.getItems().get(gold) == 30);
        assertEquals(player.getHealthPoints(), 5);
    }

    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(BishopTest.class);
    }
}
