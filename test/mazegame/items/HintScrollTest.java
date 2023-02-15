package mazegame.items;

import org.junit.*;
import static org.junit.Assert.*;

import java.beans.Transient;
import java.util.*;
import mazegame.entities.*;
import mazegame.items.*;
import mazegame.*;
import mazegame.cells.*;

public class HintScrollTest {
    
    private HintScroll hintScroll;
    private Player player;
    private Game game;
    private Minotaur minotaur;
    private Couturier couturier;
    private Key key;


    @Before
    public void initialize(){
        game = new Game(new Maze(15, 15), new ArrayList<Npc>());
        player = new Player("merlin", 15, 15, game);
        hintScroll = new HintScroll(player);
        minotaur = new Minotaur(game);
        couturier = new Couturier("testCouturier", 10, 10, game);
    }

    @Test
    public void computeDirectionComputesTheGoodDirectionHint(){
        Direction direction;

        player.setCell(game.getMaze().getCell(5,5));

        minotaur.setCell(game.getMaze().getCell(0,5));
        direction = hintScroll.computeDirection(minotaur.getCell());
        assertEquals(direction, Direction.WEST);

        couturier.setCell(game.getMaze().getCell(10,4));
        direction = hintScroll.computeDirection(couturier.getCell());
        assertEquals(direction, Direction.EAST);

        minotaur.setCell(game.getMaze().getCell(5,8));
        direction = hintScroll.computeDirection(minotaur.getCell());
        assertEquals(direction, Direction.SOUTH);

        couturier.setCell(game.getMaze().getCell(4,2));
        direction = hintScroll.computeDirection(couturier.getCell());
        assertEquals(direction, Direction.NORTH);

        minotaur.setCell(game.getMaze().getCell(5,5));
        direction = hintScroll.computeDirection(minotaur.getCell());
        assertEquals(direction, Direction.NORTH);
    }

    @Test
    public void computeDistanceComputesTheGoodDistanceHint(){
        int distance;

        player.setCell(game.getMaze().getCell(5,5));

        minotaur.setCell(game.getMaze().getCell(0,6));
        distance = hintScroll.computeDistance(minotaur.getCell());
        assertEquals(distance, 6);

        couturier.setCell(game.getMaze().getCell(11,1));
        distance = hintScroll.computeDistance(couturier.getCell());
        assertEquals(distance, 10);

        minotaur.setCell(game.getMaze().getCell(5,5));
        distance = hintScroll.computeDistance(minotaur.getCell());
        assertEquals(distance, 0);

        couturier.setCell(game.getMaze().getCell(4,4));
        distance = hintScroll.computeDistance(couturier.getCell());
        assertEquals(distance, 2);
    }

    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(HintScrollTest.class);
    }
}