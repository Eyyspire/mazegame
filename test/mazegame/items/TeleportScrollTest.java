package mazegame.items;

import org.junit.*;
import static org.junit.Assert.*;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.HashMap;

import mazegame.entities.*;
import mazegame.items.*;
import mazegame.items.UsableItem;
import mazegame.*;

public class TeleportScrollTest {
    
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
    public void TeleportScrollTeleportsToARandomSquareInTheMaze(){
        player.use(tpscroll);
        assertTrue(player.getCell().getX() < game.getMaze().getWidth());
        assertTrue(player.getCell().getY() < game.getMaze().getHeight());
    }

    @Test
    public void TeleportScrollTeleportsToADifferentSquareFromTheCurrentOne(){
        int X, Y;
        X = player.getCell().getX();
        Y = player.getCell().getY();
        player.use(tpscroll);
        assertFalse(player.getCell().getX() == X && player.getCell().getY() == Y);
    }

    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(UsableItemTest.class);
    }
}