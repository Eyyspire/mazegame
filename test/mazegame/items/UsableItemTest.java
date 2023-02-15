package mazegame.items;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import mazegame.entities.*;
import mazegame.items.*;
import mazegame.items.UsableItem;
import mazegame.*;

public class UsableItemTest {
    
    private TeleportScroll tpscroll;
    private Player player;
    private Game game;

    @Before
    public void initialize(){
        game = new Game();
        player = new Player("merlin", 5, 5, game);
        tpscroll = new TeleportScroll(player, 2);
    }

    @Test
    public void UsableItemIsRemovedFromBackpackOnceFullyUsed() {
        assertTrue(player.getItems().containsKey(tpscroll));
        assertSame(tpscroll.getNumberOfUses(), 2);

        //direct use
        tpscroll.use();
        assertTrue(player.getItems().containsKey(tpscroll));
        assertSame(tpscroll.getNumberOfUses(), 1);

        //indirect use
        player.use(tpscroll);
        assertFalse(player.getItems().containsKey(tpscroll));
    }


    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(UsableItemTest.class);
    }
}
