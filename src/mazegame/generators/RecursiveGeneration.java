package mazegame.generators;

import mazegame.*;
import mazegame.cells.*;
import mazegame.util.*;
import java.util.*;

public class RecursiveGeneration implements Generator{

    public RecursiveGeneration() {
    }// No need for any attribute.

    /**
     * Generates a pattern in a maze.
     * 
     * @param maze the maze.
     */
    public void generate(Maze maze){
        Util util = maze.getUtil();
        util.initEdgeWalls(true);
        util.setMazeStatus(Wall.PRESENT);
        this.recursiveGeneration(0, 0, util, maze);
    }

    /**
     * A recursive generation algorithm.
     * This algorithm needs a fully closed maze to begin with, i.e every walls of every cells is set to PRESENT (which is true by default).
     * We first settle in a corner of the maze, and choose at random a cell among the neighbouring undiscovered cells, and destroy the cell (setting the wall to ABSENT) leading to that cell,
     * we then go from that new cell and run the method recursively, until every cell has been discovered.
     * @param x
     * @param y
     */
    public void recursiveGeneration(int x, int y, Util util, Maze maze) {
        if (!maze.getCell(x, y).isDiscovered()) {

            maze.getCell(x, y).setDiscovered(true);
            Cell currentCell = maze.getCell(x, y);

            while (util.hasUnvisitedNeighbours(currentCell)) {
                List<Cell> cellsToVisit =  util.getUnvisitedNeighbours(currentCell);

                int choosenCellNb = (int) Math.round(Math.random() * (cellsToVisit.size() - 1));
                Cell choosenCell = cellsToVisit.get(choosenCellNb);

                // chosenCell is on the right
                if (choosenCell.getX() - x == 1) {
                    util.setWall(currentCell, Direction.EAST, Wall.ABSENT);
                }
                // choosenCell is on the left
                else if (choosenCell.getX() - x == -1) {
                    util.setWall(currentCell, Direction.WEST, Wall.ABSENT);
                }
                // choosenCell is at the bottom
                else if (choosenCell.getY() - y == 1) {
                    util.setWall(currentCell, Direction.SOUTH, Wall.ABSENT);
                }
                // choosenCell is on top
                else {
                    util.setWall(currentCell, Direction.NORTH, Wall.ABSENT);
                }

                recursiveGeneration(choosenCell.getX(), choosenCell.getY(), util, maze);
            }
 
        }
    }
}