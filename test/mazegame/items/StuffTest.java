package mazegame.items;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import mazegame.entities.*;
import mazegame.items.*;
import mazegame.items.UsableItem;
import mazegame.*;

public class StuffTest {
    
    private Stuff stuff;
    private Player player;
    private Game game;
    private Weapon ww;
    private Armor wa;
    private Shield ws;
    private Shield is;


    @Before
    public void initialize(){
        game = new Game();
        player = new Player("merlin", 5, 5, game);
        ww = new WoodenSword();
        wa = new WoodenArmor();
        ws = new WoodenShield();
        is = new IronShield();
        player.addItem(ww, 1);
        player.addItem(wa, 1);
        player.addItem(ws, 1);
        player.addItem(is, 1);
    }

    @Test 
    public void StuffIsInitializedWithNothing(){
        assertTrue(((Weapon) player.getStuff().get(0)).getStrength() == 0);
        assertTrue(((Armor) player.getStuff().get(1)).getProtection() == 0);
        assertTrue(((Shield) player.getStuff().get(2)).getDodge() == 0);
    }

    @Test
    public void StuffIsWellEquiped(){
        player.equip(ww);
        assertTrue(((Weapon)player.getStuff().get(0)).getStrength() == ww.getStrength());
        player.equip(wa);
        assertTrue(((Armor)player.getStuff().get(1)).getProtection() == wa.getProtection());
        player.equip(ws);
        assertTrue(((Shield)player.getStuff().get(2)).getDodge() == ws.getDodge());
        player.equip(is);
        assertTrue(((Shield)player.getStuff().get(2)).getDodge() == is.getDodge());
    }

    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(StuffTest.class);
    }
}
