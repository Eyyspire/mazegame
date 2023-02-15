package mazegame.items;

import org.junit.*;
import static org.junit.Assert.*;

import mazegame.cells.*;
import mazegame.entities.*;
import mazegame.items.UsableItem;
import mazegame.*;

public class PickaxeTest {
    
    private Player player;
    private Game game;
    private Maze maze;
    private Pickaxe pickaxe;


    @Before
    public void initialize(){
        game = new Game();
        maze = game.getMaze();
        player = new Player("merlin", 15, 5, game);
        pickaxe = new Pickaxe(5, player);
    }

    @Test
    public void pickaxeDontBreakEdgeWalls() {
        Cell testCell = maze.getCell(0, 0);
        Wall northWall = testCell.getWall(Direction.NORTH);

        player.setCell(testCell);
        assertTrue(northWall == Wall.EDGE);

        pickaxe.destroyWall(testCell, northWall, Direction.NORTH);
        northWall = testCell.getWall(Direction.NORTH);
        assertTrue(northWall == Wall.EDGE);
    }

    @Test
    public void pickaxeBreakUsualWalls() {
        Cell testCell = maze.getCell(0, 0);
        Wall southWall = testCell.getWall(Direction.SOUTH);
        Wall eastWall = testCell.getWall(Direction.EAST);

        player.setCell(testCell);

        pickaxe.destroyWall(testCell, southWall, Direction.SOUTH);
        pickaxe.destroyWall(testCell, eastWall, Direction.EAST);
        southWall = testCell.getWall(Direction.SOUTH);
        eastWall = testCell.getWall(Direction.EAST);

        assertTrue(southWall == Wall.ABSENT);
        assertTrue(eastWall == Wall.ABSENT);
    }

    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(PickaxeTest.class);
    }
}
