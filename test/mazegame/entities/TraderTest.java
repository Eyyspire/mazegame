package mazegame.entities;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import mazegame.*;
import mazegame.cells.*;
import mazegame.entities.*;
import mazegame.items.*;
import mazegame.data.TraderPrices;

public class TraderTest {

    private Game game;
    private Player player;
    private Trader trader;
    private IronSword sword;

    @Before
    public void initialize(){
        game = new Game();
        sword = new IronSword();
        player = new Player("Bob", 10, 5, game);
        trader = new Trader("Bill", 5, 5, game);
    }

    @Test
    public void BackpackIsEmptyAtCreation() {
        assertTrue(trader.getItems().isEmpty());
    }

    @Test
    public void TraderWillNotSellItemTheyDoNotOwn() {
        assertFalse(trader.sell(sword, 1));
    }

    @Test
    public void TraderWillNotSellItemIfTheyDoNotHaveEnoughOfIt() {
        trader.addItem(sword, 1);
        assertFalse(trader.sell(sword, 2));
    }

    @Test
    public void TraderWillNotSellItemIfPlayerDoesNotHaveEnoughMoney() {
        player.addItem(new Gold(), 10);
        trader.addItem(sword, 1);
       
        Gold gold = new Gold();
        int oldFortune = 0;
        for(Item item : player.getItems().keySet()) {
            if(item.equals(gold)) {
                oldFortune += player.getItems().get(item);
            }
        }

        int price = 0;
        for (TraderPrices tp : TraderPrices.values()) {
            if(sword.equals(tp.getItem())) {
                price = tp.getPrice();
            }
        }

        assertTrue(oldFortune < price);

        //attempting transaction
        player.buy(sword, 1, trader);

        int newFortune = 0;
        for(Item item : player.getItems().keySet()) {
            if(item.equals(gold)) {
                newFortune += player.getItems().get(item);
            }
        }
        
        //gold in backpack unchanged
        assertSame(oldFortune, newFortune);

        //still no sword
        assertFalse(player.getItems().containsKey(sword));
    }

    @Test
    public void TraderBackpackIsUpdatedWhenTransactionIsSuccessful() {
        trader.addItem(new Gold(), 1);
        trader.addItem(sword, 1);
   
        player.addItem(new Gold(), 20);

        assertTrue(trader.getItems().get(new Gold()) == 1);
        assertTrue(trader.getItems().containsKey(sword));

        player.buy(sword, 1, trader);

        assertTrue(trader.getItems().get(new Gold() ) == 16);
        assertFalse(trader.getItems().containsKey(sword));
    }


    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(TraderTest.class);
    }
}