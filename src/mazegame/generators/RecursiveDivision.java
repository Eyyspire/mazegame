package mazegame.generators;

import mazegame.*;
import mazegame.cells.*;
import mazegame.util.*;
import java.util.*;

public class RecursiveDivision implements Generator {

    public RecursiveDivision(){

    } // No need for any attribute.

    /**
     * Generates a pattern in a maze.
     * 
     * @param maze the maze.
     */
    public void generate(Maze maze){
        Util util = maze.getUtil();
        util.initEdgeWalls(true);
        util.setMazeStatus(Wall.ABSENT);
        this.recursiveDivision(0, 0, maze.getWidth(), maze.getHeight(), util, maze);
    }

    /**
     * A recursive generation algorithm.
     * This algorithm needs a fully opened maze to begin with, i.e every walls of every cells is set to ABSENT, which is insured beforehand by the setMazeEmpty method.
     * We divide vertically or horizontally the maze in two, based on whether it has bigger width or height. Diving the maze means putting a full set of PRESENT walls in between two equal parts of the maze.
     * We then open the wall at random on one of the cell, creating a door. We then run the methode recursively on both subdivisions of the maze, until the maze is fully generated.
     */
    private void recursiveDivision(int x0, int y0, int width, int height, Util util, Maze maze){
        if (width < 2 || height < 2){
            return;
        }
        else {
            Random rnd = new Random();
            if ((width < height) || (width == height && rnd.nextBoolean())){
                int y = y0 + 1 + rnd.nextInt(height - 1);
                int door = x0 + rnd.nextInt(width);
                for (int i = x0; i < x0+width; i++){
                    util.setWall(maze.getCell(i, y-1), Direction.SOUTH, Wall.PRESENT);
                    util.setWall(maze.getCell(i, y), Direction.NORTH, Wall.PRESENT);
                }
                util.setWall(maze.getCell(door, y-1), Direction.SOUTH, Wall.ABSENT);
                util.setWall(maze.getCell(door, y), Direction.NORTH, Wall.ABSENT);
                this.recursiveDivision(x0, y0, width, y-y0, util, maze);
                this.recursiveDivision(x0, y, width, height - (y-y0), util, maze);
            }
            else {
                int x = x0 + 1 + rnd.nextInt(width - 1);
                int door = y0 + rnd.nextInt(height);
                for (int i = y0; i < y0+height; i++){
                    util.setWall(maze.getCell(x-1, i), Direction.EAST, Wall.PRESENT);
                    util.setWall(maze.getCell(x, i), Direction.WEST, Wall.PRESENT);
                }
                util.setWall(maze.getCell(x-1, door), Direction.EAST, Wall.ABSENT);
                util.setWall(maze.getCell(x, door), Direction.WEST, Wall.ABSENT);
                this.recursiveDivision(x0, y0, x-x0, height, util, maze);
                this.recursiveDivision(x, y0, width - (x-x0), height, util, maze);
            }
        }
    }

}
