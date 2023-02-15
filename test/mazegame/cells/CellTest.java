package mazegame.cells;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;

import mazegame.cells.*;
import mazegame.items.*;

public class CellTest {
    private Cell cell1;

    @Before
    public void initialize(){
        cell1 = new Cell(0, 0);

    }

    @Test
    public void allWallsCreatedWhenCellCreated(){
        assertTrue(cell1.getWall(Direction.NORTH) == Wall.PRESENT);
        assertTrue(cell1.getWall(Direction.SOUTH) == Wall.PRESENT);
        assertTrue(cell1.getWall(Direction.EAST) == Wall.PRESENT);
        assertTrue(cell1.getWall(Direction.WEST) == Wall.PRESENT);
    }

    @Test
    public void cellEmptyAndNotDiscoveredWhenCreated(){
        
        assertTrue(cell1.getNPCs().isEmpty());
        assertFalse(cell1.isPlayerOnCell());
        assertTrue(cell1.getItems().isEmpty());
        assertFalse(cell1.isDiscovered());
    }

    @Test
    public void methodsGetWalAndSetWallWorkForAllDirections() {
        assertTrue(cell1.getWall(Direction.NORTH) == Wall.PRESENT);
        cell1.setWall(Direction.NORTH, Wall.ABSENT);
        assertTrue(cell1.getWall(Direction.NORTH) == Wall.ABSENT);
        assertTrue(cell1.getWall(Direction.SOUTH) == Wall.PRESENT);
        cell1.setWall(Direction.SOUTH, Wall.ABSENT);
        assertTrue(cell1.getWall(Direction.SOUTH) == Wall.ABSENT);
        assertTrue(cell1.getWall(Direction.EAST) == Wall.PRESENT);
        cell1.setWall(Direction.EAST, Wall.ABSENT);
        assertTrue(cell1.getWall(Direction.EAST) == Wall.ABSENT);
        assertTrue(cell1.getWall(Direction.WEST) == Wall.PRESENT);
        cell1.setWall(Direction.WEST, Wall.ABSENT);
        assertTrue(cell1.getWall(Direction.WEST) == Wall.ABSENT);
    }

    @Test
    public void methodSetDiscoveredWorksCorrectly() {
        assertFalse(cell1.isDiscovered());
        cell1.setDiscovered(true);
        assertTrue(cell1.isDiscovered());
    }

    @Test
    public void addItemAddTheCorrectItem(){
        assertTrue(!cell1.getItems().containsKey(new Iron()));
        cell1.addItem(new Iron(), 2);
        assertEquals((int) cell1.getItems().get(new Iron()), 2);
        cell1.addItem(new Iron(), 3);
        assertEquals((int) cell1.getItems().get(new Iron()), 5);
    }

    @Test
    public void removeItemremovesCorrectly(){
        cell1.addItem(new Wood(), 7);
        cell1.removeItem(new Wood(), 4);
        assertEquals((int) cell1.getItems().get(new Wood()), 2);
        cell1.addItem(new Wood(), 3);
        assertFalse(cell1.getItems().containsKey(new Iron()));
    }



    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(CellTest.class);
    }
}
