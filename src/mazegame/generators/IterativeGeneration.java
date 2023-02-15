package mazegame.generators;

import mazegame.*;
import mazegame.cells.*;
import mazegame.util.*;

public class IterativeGeneration implements Generator{

    public IterativeGeneration() {
    } // No need for any attribute

    /**
     * Generates a pattern in a maze.
     * 
     * @param maze the maze.
     */
    public void generate(Maze maze){
        Util util = maze.getUtil();
        util.initEdgeWalls(true);
        util.setMazeStatus(Wall.PRESENT);
        this.iterativeGeneration(util, maze);
    }

    /**
     * A non-recursive generation algorithm.
     * This algorithm needs a fully closed maze to begin with, i.e every walls of every cells is set to PRESENT (which is true by default).
     * We go through every cell of the maze, and pick at random a wall to destroy between the south and the east wall.
     * If on the eastern column, will necessarily destroy the south wall; if on the southern row will necessarily destroy the east wall.
     * This is the only implemented algorithm able to generate large mazes (e.g larger than 100x100), but the southern row and eastern column will necessarily be opened.
     */
    public void iterativeGeneration(Util util, Maze maze) {
        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {

                // for any cell of the maze expect the southern and eastern cells
                if (x != maze.getWidth() - 1 && y != maze.getHeight() -1) {
                    boolean destroyRightWall = Math.round(Math.random()) == 1; // coinflip to choose which wall to destroy
                    if (destroyRightWall) {
                        util.setWall(maze.getCell(x, y), Direction.EAST, Wall.ABSENT);
                    } else {
                        util.setWall(maze.getCell(x, y+1), Direction.NORTH, Wall.ABSENT);
                    }
                }
                // if on the eastern collumn, will necessarily destroy the south wall
                else if (x == maze.getWidth() - 1 && y != maze.getHeight() -1) {
                    util.setWall(maze.getCell(x, y), Direction.SOUTH, Wall.ABSENT);
                }
                // if on the southern rown will necessarily destroy the east wall
                else if (x != maze.getWidth() - 1 && y == maze.getHeight() -1) {
                    util.setWall(maze.getCell(x, y), Direction.EAST, Wall.ABSENT);
                }
            }
        }
    }
}