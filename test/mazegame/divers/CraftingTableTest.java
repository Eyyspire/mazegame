package mazegame.divers;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;

import mazegame.*;
import mazegame.items.*;
import mazegame.entities.*;


public class CraftingTableTest {
    private CraftingTable ct;
    private Game game;
    private Player player;

    @Before
    public void initialize(){
        this.game = new Game();
        this.player = new Player("Bob", 10, 5, this.game);
        this.player.addItem(new Wood(), 5);
        this.player.addItem(new Iron(), 3);
        this.ct = new CraftingTable(game);
    }
    
    @Test
    public void playerCanCraftAnObjetOnlyIfHeHasRessourcesFor(){
        assertTrue(this.ct.canCraft(new IronSword()));
        assertTrue(this.ct.canCraft(new WoodenShield()));
        assertFalse(this.ct.canCraft(new Pickaxe()));
    }

    @Test
    public void CraftupdateItemsRemoveTheItemNeededForTheRecipe(){

        player.addItem(new Iron(), 2);

        int woodAmount = this.player.getItems().get(new Wood());
        int ironAmount = this.player.getItems().get(new Iron());

        this.ct.updateItem(new Pickaxe());

        int uptWoodAmount = this.player.getItems().get(new Wood());
        int uptIronAmount = this.player.getItems().get(new Iron());

        assertEquals(uptWoodAmount, woodAmount - new Pickaxe().getRecipe().get(new Wood()));
        assertEquals(uptIronAmount, ironAmount - new Pickaxe().getRecipe().get(new Iron()));
    }

    @Test
    public void CraftupdateItemsAddTheCraftedItemToThePlayerBackPack(){

        WoodenShield ws = new WoodenShield();
        
        this.ct.updateItem(ws);
        int uptWoodenShieldAmount = this.player.getItems().get(ws);
        assertEquals(1, uptWoodenShieldAmount);     
        System.out.println(this.player.getItems().get(new Wood()));
        
    }

    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(CraftingTableTest.class);
    }
}
