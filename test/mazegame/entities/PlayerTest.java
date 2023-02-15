package mazegame.entities;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;

import mazegame.Game;
import mazegame.cells.*;
import mazegame.items.*;
import mazegame.entities.*;

import mazegame.data.TraderPrices;

public class PlayerTest {

    private Player player;
    private Game game;
    private Cell cell;
    private Wood wood;
    private Trader trader;

    @Before
    public void initialize(){

        cell = new Cell(0, 0);
        game = new Game();
        player = new Player("billyboy", 100, 10, game);
        wood = new Wood();
        cell.addItem(wood, 1);
        player.setCell(cell);
        player.addItem(new Gold(), 1000);
        trader = new Trader("billyboy", 10, 1, game);

    }

    @Test
    public void pickUpRelocatesItemFromTheGroundToTheBackpack() {

        assertTrue(player.getCell().getItems().containsKey((wood)));
        assertFalse(player.getItems().containsKey((wood)));

        player.pickUp(wood, 1);

        assertFalse(player.getCell().getItems().containsKey((wood)));
        assertTrue(player.getItems().containsKey((wood)));

    }

    @Test
    public void buyAddsItemToTheBackpackAndRemovesGold() {

        trader.addItem(wood, 1);

        assertTrue(player.getItems().get((new Gold())) == 1000);
        assertFalse(player.getItems().containsKey((wood)));

        int woodprice = 0;

        for (TraderPrices tp : TraderPrices.values()) {
            if(wood.equals(tp.getItem())) {
                woodprice = tp.getPrice();
            }
        }

        player.buy(wood, 1, trader);

        assertTrue(player.getItems().get((new Gold())) == 1000-woodprice);
        assertTrue(player.getItems().containsKey((wood)));
    }


    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(PlayerTest.class);
    }
}
