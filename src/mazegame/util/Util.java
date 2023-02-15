package mazegame.util;

import mazegame.*;
import mazegame.cells.*;
import java.util.*;

public class Util {
    private Maze maze;

    public Util(Maze maze) {
        this.maze = maze;
    }

    public Maze getMaze(){
        return this.maze;
    }

    /**
     * Updates the status of a given wall of a given cell. This insures that the
     * status of the same wall on the neighbouring cell is updated at the same time.
     * 
     * @param cell      the cell to update
     * @param direction the direction of the wall to update
     * @param set       the type of wall to set
     */
    public void setWall(Cell cell, Direction direction, Wall set) {
        // TODO remplacer les 4 if par un switch svp
        int widthPos = cell.getX();
        int heightPos = cell.getY();
        if (cell.getWall(direction) != Wall.EDGE) {
            if (direction == Direction.NORTH) {
                cell.setWall(Direction.NORTH, set);
                this.maze.getCell(widthPos, heightPos - 1).setWall(Direction.SOUTH, set);
            }
            if (direction == Direction.WEST) {
                cell.setWall(Direction.WEST, set);
                this.maze.getCell(widthPos - 1, heightPos).setWall(Direction.EAST, set);
            }
            if (direction == Direction.SOUTH) {
                cell.setWall(Direction.SOUTH, set);
                this.maze.getCell(widthPos, heightPos + 1).setWall(Direction.NORTH, set);
            }
            if (direction == Direction.EAST) {
                cell.setWall(Direction.EAST, set);
                this.maze.getCell(widthPos + 1, heightPos).setWall(Direction.WEST, set);
            }
        }
    }

    /**
     * Sets every walls on the edge of the maze to match the according type.
     */
    public void initEdgeWalls(boolean buildSecretWall) {
        for (Cell[] cells : this.maze.getCells()) {
            for (Cell cell : cells) {
                int widthPos = cell.getX();
                int heightPos = cell.getY();
                if (heightPos == 0) {
                    cell.setWall(Direction.NORTH, Wall.EDGE);
                }
                if (widthPos == 0) {
                    cell.setWall(Direction.WEST, Wall.EDGE);
                }
                if (heightPos == this.maze.getHeight() - 1) {
                    cell.setWall(Direction.SOUTH, Wall.EDGE);
                }
                if (widthPos == this.maze.getWidth() - 1) {
                    cell.setWall(Direction.EAST, Wall.EDGE);
                }
            }
        }
        if(buildSecretWall){
            buildSecretWall();
        }
    }

    public void buildSecretWall(){
        // créé un edge brisable pour une condition de victoire secrète
        int width;
        int height;
        Direction direction;
        Random rand = new Random();
        boolean isHorizontal = rand.nextBoolean();
        if(isHorizontal){
            boolean isNorth = rand.nextBoolean();
            if (isNorth){
                direction = Direction.NORTH;
                height = 0;
                width = rand.nextInt(this.maze.getWidth() - 1);
            }
            else{
                direction = Direction.SOUTH;
                height = this.maze.getHeight()-1;
                width = rand.nextInt(this.maze.getWidth() - 1);
            }
        }
        else{
            boolean isWest = rand.nextBoolean();
            if(isWest){
                direction = Direction.WEST;
                height = rand.nextInt(this.maze.getHeight() - 1);
                width = 0;
            }
            else{
                direction = Direction.EAST;
                height = rand.nextInt(this.maze.getHeight() - 1);
                width = this.maze.getWidth()-1;
            }
        }

        this.maze.getCell(width, height).setWall(direction, Wall.SECRET_VICTORY);
        this.maze.setSecretWallLocation(this.maze.getCell(width, height));
    }

    public List<Cell> getNeighbours(Cell cell) {
        List<Cell> res = new ArrayList<>();
        int x = cell.getX();
        int y = cell.getY();

        if (cell.getWall(Direction.NORTH) == Wall.ABSENT) {
            res.add(this.maze.getCell(x, y - 1));
        }
        if (cell.getWall(Direction.SOUTH) == Wall.ABSENT) {
            res.add(this.maze.getCell(x, y + 1));
        }
        if (cell.getWall(Direction.EAST) == Wall.ABSENT) {
            res.add(this.maze.getCell(x + 1, y));
        }
        if (cell.getWall(Direction.WEST) == Wall.ABSENT) {
            res.add(this.maze.getCell(x - 1, y));
        }
        return res;
    }

    public void setUndiscovered() {
        for (int x = 0; x < this.maze.getWidth(); x++) {
            for (int y = 0; y < this.maze.getHeight(); y++) {
                this.maze.getCell(x, y).setDiscovered(false);
            }
        }
    }

    public boolean allCellsDiscovered(){
        for (int x = 0; x < this.maze.getWidth(); x++) {
            for (int y = 0; y < this.maze.getHeight(); y++) {
                if (!this.maze.getCell(x, y).isDiscovered()){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Sets every wall to match the Wall.ABSENT or Wall.PRESENT enum type (virtually deleting
     * or adding every wall).
     * This is necessary for the recursive division generation algorithm.
     */
    public void setMazeStatus(Wall status) {
        for (int i = 0; i < this.maze.getWidth() - 1; i++) {
            for (int j = 0; j < this.maze.getHeight() - 1; j++) {
                this.setWall(this.maze.getCell(i, j), Direction.EAST, status);
                this.setWall(this.maze.getCell(i, j), Direction.SOUTH, status);
            }
        }
        for (int i = 0; i < this.maze.getWidth() - 1; i++) {
            this.setWall(this.maze.getCell(i, this.maze.getHeight() - 1), Direction.EAST, status);
        }
        for (int j = 0; j < this.maze.getHeight() - 1; j++) {
            this.setWall(this.maze.getCell(this.maze.getWidth() - 1, j), Direction.SOUTH, status);
        }
    }

    /**
     * Returns the list of all unvisited cells neighbouring the given cell.
     * This is necessary for the first recursive generation algorithm.
     * 
     * @param cell, the cell to check
     * @return the list every neighbouring cell yet to be discovered
     */
    public List<Cell> getUnvisitedNeighbours(Cell cell) {
        List<Cell> res = new ArrayList<>();

        int x = cell.getX();
        int y = cell.getY();

        Cell leftCell = x - 1 < 0 ? null : this.maze.getCell(x - 1, y);
        Cell rightCell = x + 1 >= this.maze.getWidth() ? null : this.maze.getCell(x + 1, y);
        Cell topCell = y - 1 < 0 ? null : this.maze.getCell(x, y - 1);
        Cell bottomCell = y + 1 >= this.maze.getHeight() ? null : this.maze.getCell(x, y + 1);

        if (leftCell != null && !leftCell.isDiscovered()) {
            res.add(leftCell);
        }
        if (rightCell != null && !rightCell.isDiscovered()) {
            res.add(rightCell);
        }
        if (topCell != null && !topCell.isDiscovered()) {
            res.add(topCell);
        }
        if (bottomCell != null && !bottomCell.isDiscovered()) {
            res.add(bottomCell);
        }

        return res;
    }

    /**
     * Returns whether the cell has unvisited neighbours cells, i.e whether one of
     * them has been discovered.
     * 
     * @param cell, the cell to check
     * @return true if the cell has at least one unvisited cell neighbouring it,
     *         false otherwise
     */
    public boolean hasUnvisitedNeighbours(Cell cell) {
        return getUnvisitedNeighbours(cell).size() > 0;
    }
}
