package mazegame.util;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

import mazegame.Maze;
import mazegame.util.*;
import mazegame.cells.*;

public class UtilTest {
    private Maze maze;
    private Util util;

    @Before
    public void initialize(){
        maze = new Maze(10, 10);
        util = maze.getUtil();
    }

    @Test 
    public void setMazeStatusAbsentDestroyAllTheWalls(){
        util.setMazeStatus(Wall.ABSENT);
        for (int i = 0; i < maze.getHeight() - 1; i++){
            for (int j = 0; j < maze.getWidth() - 1; j++){
            assertTrue(maze.getCell(i, j).getWall(Direction.EAST) == Wall.ABSENT);
            assertTrue(maze.getCell(i, j).getWall(Direction.SOUTH) == Wall.ABSENT);
            }
        }
    }

    @Test
    public void setWallSetWallsOnBothCells() {
        Cell cellToChange = maze.getCell(2, 2);
        Cell rightNeighbour = maze.getCell(3, 2);
        util.setWall(cellToChange, Direction.EAST, Wall.ABSENT);
        assertTrue(cellToChange.getWall(Direction.EAST) == Wall.ABSENT);
        assertTrue(rightNeighbour.getWall(Direction.WEST) == Wall.ABSENT);
    }

    @Test
    public void InitEdgeWallsWorksCorrectly() {
        util.initEdgeWalls(false);
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Cell cellToTest = maze.getCell(i, j);

                // the cell is on the west side of the maze
                if (i == 0) {
                    Wall cellWall = cellToTest.getWall(Direction.WEST);
                    assertEquals(cellWall, Wall.EDGE);
                }
                // the cell is on the east side of the maze
                else if(i == 9) {
                    Wall cellWall = cellToTest.getWall(Direction.EAST);
                    assertEquals(cellWall, Wall.EDGE); 
                }
                // the cell is on the north side of the maze
                if (j == 0) {
                    Wall cellWall = cellToTest.getWall(Direction.NORTH);
                    assertEquals(cellWall, Wall.EDGE);
                }
                // the cell is on the south side of the maze
                else if (j == 9) {
                    Wall cellWall = cellToTest.getWall(Direction.SOUTH);
                    assertEquals(cellWall, Wall.EDGE); 
                }

            }
        }
    }

    @Test
    public void getUnvisitedNeighboursWorksCorrectly() {
        Cell testCell = maze.getCell(3, 3);
        Cell topCell = maze.getCell(3, 2);
        Cell bottomCell = maze.getCell(3, 4);
        Cell westCell = maze.getCell(2, 3);
        Cell eastCell = maze.getCell(4, 3);

        eastCell.setDiscovered(true);
        List<Cell> unvisitedNeighbours = util.getUnvisitedNeighbours(testCell);

        // bottom, top and west cells are in unvisitedNeighbours 
        // east cells is not in unvisitedNeighbours
        assertTrue(unvisitedNeighbours.size() == 3);
        assertTrue(unvisitedNeighbours.contains(topCell));
        assertTrue(unvisitedNeighbours.contains(bottomCell));
        assertTrue(unvisitedNeighbours.contains(westCell));
        assertFalse(unvisitedNeighbours.contains(eastCell));
    }

    @Test
    public void secretVictoryWallIsSuccessfullyAddedAmongTheEdgeWalls(){
        util.initEdgeWalls(true);

        int countVictoryWall = 0;
        for(int i = 0; i < maze.getWidth(); i++){
            if(maze.getCell(i, 0).getWall(Direction.NORTH).equals(Wall.SECRET_VICTORY)){
                countVictoryWall++;
            }
            if(maze.getCell(i, maze.getHeight() - 1).getWall(Direction.SOUTH).equals(Wall.SECRET_VICTORY)){
                countVictoryWall++;
            }
        }
        for(int i = 0; i < maze.getHeight(); i++){
            if(maze.getCell(0, i).getWall(Direction.WEST).equals(Wall.SECRET_VICTORY)){
                countVictoryWall++;
            }
            if(maze.getCell(maze.getWidth() - 1, i).getWall(Direction.EAST).equals(Wall.SECRET_VICTORY)){
                countVictoryWall++;
            }
        }
        assertTrue(countVictoryWall == 1);
    }

    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(UtilTest.class);
    }
}
