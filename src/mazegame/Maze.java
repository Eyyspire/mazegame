package mazegame;
import mazegame.cells.*;
import mazegame.data.Colors;
import mazegame.util.*;
import mazegame.generators.*;

public class Maze {
    private Generator algo;
    private Util util;
    private Cell[][] cells; //every cell of the maze
    private int width; //or x coordinate
    private int height; //or y coordinate
    private Cell secretWallLocation;


    /**
     * Initialises the maze.
     * First choosing the optimal generation algorithm based on the maze's size :
     * if the maze is below 100x100, it will use either recursive algorithms, if above, it will use a non-recursive algorithm.
     * (See documentation on said algorithms for further information.)
     * Then, will update every edge walls to match the according type.
     */

    public Maze(int width, int height) {
        this(width, height, new RecursiveDivision());
    }

    public Maze(int width, int height, Generator algo) {
        this.cells = new Cell[width][height];
        this.width = width;
        this.height = height;

        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                this.cells[x][y] = new Cell(x, y);
            }
        }

        this.util = new Util(this);
        this.algo = algo;
        this.algo.generate(this);

    }

    public Util getUtil() {
        return this.util;
    }

    public Generator getGenerator() {
        return this.algo;
    }

    /**
     * Getter for the width of the maze
     * @return the width of the maze
     */
    public int getWidth(){
        return this.width;
    }

    /**
     * Getter for the height of the maze
     * @return the height of the maze
     */
    public int getHeight(){
        return this.height;
    }
    
    /**
     * Getter for the cell of given coordinates
     * @return the cell of the given coordinates
     */
    public Cell getCell(int width, int height){
        return this.cells[width][height];
    }

    /**
     * Getter for every cells of the maze
     * @return all the cells of the maze
     */
    public Cell[][] getCells(){
        return this.cells;
    }
    

    private String firstLine(String[] s){
        String res = s[0];
    
        for (int index = 0; index < this.width-1; index++) {
            res += s[9] + s[9] + s[9] + s[1];
        }
        res += s[9] + s[9] + s[9] + s[2];
        return res + "\n";
    }
    
    private String verticalLine(String[] s, int y){
        String res = s[8];
        for (int x = 0; x < this.width; x++){
            Cell currentCell = this.cells[x][y];
    
            if (!currentCell.isDiscovered()) {
                res += Colors.colorize(" ? ", Colors.BRIGHTBLACK);
            } else if (currentCell.isPlayerOnCell()) {
                res += Colors.colorize(" * ", Colors.CYAN);
            } else if (!(currentCell.getNPCs().isEmpty())){
                res += " " + Colors.colorize(currentCell.getNPCs().get(0).getName().substring(0, 1).toUpperCase(), Colors.GREEN) + " ";
            } else {
                res += "   ";
            }
    
            res += currentCell.getWall(Direction.EAST) != Wall.ABSENT ? s[8] : " ";
        }
        return res + "\n";
    }
    
    private String horizontalLine(String[] s, int y, int i, int j){
        String res = s[7-j];
        for (int x = 0; x < this.width-1; x++) {
            Cell currentCell = this.cells[x][y];

            res += currentCell.getWall(Direction.SOUTH) != Wall.ABSENT ? s[9] + s[9] + s[9] + s[10-i] : "   " + s[10-i];
        }
        Cell currentCell = this.cells[this.width-1][y];

        res += (currentCell.getWall(Direction.SOUTH) != Wall.ABSENT) ? s[9] + s[9] + s[9] + s[3+j] : "   " + s[3-j];
        return res + "\n";
    }
    
    @Override
    public String toString(){
        String[] symbols = {"╔", "╦", "╗", "╣", "╝", "╩", "╚", "╠", "║", "═", "╬"};
        return this.toString(symbols);
    }
    
    public String toString(String[] symbols) {
    
        String res = this.firstLine(symbols);
      
        for (int y = 0 ; y < this.height-1; y++) {
            res += this.verticalLine(symbols, y);
            res += this.horizontalLine(symbols, y, 0, 0);
        }
        res += this.verticalLine(symbols, this.height - 1);
        res += this.horizontalLine(symbols, this.height - 1, 5, 1);
        return res;
    }

    public Cell getSecretWallLocation(){
        return this.secretWallLocation;
    }

    public void setSecretWallLocation(Cell cell){
        this.secretWallLocation = cell;
    }
}
