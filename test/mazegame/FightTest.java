package mazegame;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;
import mazegame.*;
import mazegame.entities.*;

public class FightTest {

    private Fight fight;
    private Npc bad1;
    private Npc bad2;
    private Npc good1;
    private Npc good2;
    private Game game;
    private Player player;

    @Before
    public void initialize(){
        this.player = new Player("test", 10, 10, new Game());
        this.game = new Game(new Maze(20, 20), new ArrayList<Npc>(), this.player);
        this.bad1 = new Villageois(this.game);
        this.bad2 = new Villageois(this.game);
        this.bad2.setHealthPoints(0);
        this.good1 = new Villageois(this.game);
        this.good2 = new Villageois(this.game);
        this.good2.setHealthPoints(0);
        this.game.initNpc(this.bad1, 1, this.game.getNpcs());
        this.game.initNpc(this.bad2, 1, this.game.getNpcs());
        this.game.initNpc(this.good1, 1, this.game.getNpcs());
        this.game.initNpc(this.good2, 1, this.game.getNpcs());
        this.game.initEntities();
        this.fight = new Fight(this.game, new ArrayList<Entity>(Arrays.asList(this.good1, this.good2)), new ArrayList<Entity>(Arrays.asList(this.bad1, this.bad2)));
    }

    //method hasDied() works correctly

    @Test
    public void npcIsRemovedWhenHealthPointsBelowZeroOnly(){
        assertTrue(this.game.getNpcs().contains(this.good1));
        assertTrue(this.game.getNpcs().contains(this.good2));
        assertTrue(this.game.getNpcs().contains(this.bad1));
        assertTrue(this.game.getNpcs().contains(this.bad2));
        this.fight.hasDied(this.good1);
        this.fight.hasDied(this.good2);
        this.fight.hasDied(this.bad1);
        this.fight.hasDied(this.bad2);
        assertTrue(this.game.getNpcs().contains(this.good1));
        assertFalse(this.game.getNpcs().contains(this.good2));
        assertTrue(this.game.getNpcs().contains(this.bad1));
        assertFalse(this.game.getNpcs().contains(this.bad2));
    }

    //method turn() works correctly

    @Test
    public void defenderTakesDamagesWhenAttacked(){
        assertTrue(this.bad1.getHealthPoints() == 3);
        this.fight.turn(this.good1, this.bad1);
        assertTrue(this.bad1.getHealthPoints() < 3);
    }

    //method isEnding() works correctly

    @Test
    public void fightEndsWhenOneSideContainsNoMoreNpc(){
        assertFalse(this.fight.isEnding());
        this.fight.hasDied(this.bad2);
        this.fight.turn(good1, bad1);
        this.fight.turn(good1, bad1);
        this.fight.turn(good1, bad1);
        assertTrue(this.fight.isEnding());
    }
    
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(FightTest.class);
    }
}
