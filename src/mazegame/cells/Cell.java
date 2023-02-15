package mazegame.cells;

import mazegame.entities.*;
import mazegame.items.*;
import java.util.*;

public class Cell {
    protected HashMap<Direction, Wall> walls; //4 walls, either ABSENT, PRESENT, or EDGE
    protected boolean discovered; //a discovered cell reveals possible items on the floor or characters roaming the cell
    protected ArrayList<Entity> npc; //several NPCs can be on a cell at the same time
    protected boolean playerOnCell; //whether the player is currently on the cell
    protected HashMap<Item, Integer> items; //the items on the floor (nature of the items and their quantity)
    protected int x; //x position (width)
    protected int y; //y position (height)

    /**
     * builds a new Cell
     * @param x width position
     * @param y height position
     */
    public Cell (int x, int y) {
        this.x = x;
        this.y = y;
        walls = new HashMap<>();
        walls.put(Direction.NORTH, Wall.PRESENT);
        walls.put(Direction.EAST, Wall.PRESENT);
        walls.put(Direction.SOUTH, Wall.PRESENT);
        walls.put(Direction.WEST, Wall.PRESENT);
        discovered = false;
        playerOnCell = false;
        this.npc = new ArrayList<Entity>();
        this.items = new HashMap<>();
    }

    /**
     * returns the Cell's list of NPCs
     * @return the Cell's list of NPCs
     */
    public ArrayList<Entity> getNPCs() {
        return this.npc;
    }

    /**
     * adds a new NPC in the Cell's list of NPCs
     * @param npc the NPC to add
     */
    public void addNPC(Npc npc) {
        this.npc.add(npc);
    }

    /**
     * removes a NPC in the Cell's list of NPCs
     * @param npc the NPC to remove
     */
    public void removeNPC(Npc npc) {
        this.npc.remove(npc);
    }

    /**
     * returns the item on Cell
     * @return the item on Cell
     */
    public HashMap<Item, Integer> getItems() {
        return this.items;
    }

    /**
     * adds an item on Cell
     * @param item the item to add on Cell
     */
    public void addItem(Item item, int quantity) {
        for(Item i : this.items.keySet()) {
            if(i.equals(item)) {
                int oldQuantity = this.items.get(i);
                this.items.replace(i, oldQuantity + quantity);
                return;
            }
        }
        this.items.put(item, quantity);
    }

    /**
     * removes a defined quantity of an item currently on the floor
     * @param item the item to remove
     * @param quantity the quantity to remove
     */
    public void removeItem(Item item, int quantity) {
        for(Item i : this.items.keySet()) {
            if(i.equals(item)) {
                int oldQuantity = this.items.get(i);
                this.items.replace(i, oldQuantity - quantity);
                if(this.items.get(i) <= 0) {
                    this.items.remove(i);
                }
                return;
            }
        }
    }

    /**
     * sets the playerOnCell attribute to the given boolean
     * @param poc the given boolean
     */
    public void setPlayerOnCell(boolean poc) {
        this.playerOnCell = poc;
    }

    /**
     * returns whether the square has already been discovered or not
     * @return true if the cell has been discovered, false otherwise
     */
    public boolean isDiscovered () {
        return this.discovered;
    }

    /**
     * getter for one of the cell's walls
     * @param direction the direction of the wall to get
     * @return the wall matching the direction given
     */
    public Wall getWall(Direction direction) {
        return this.walls.get(direction);
    }

    /**
     * getter for x coordinate
     * @return the cell's x coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * getter for y coordinate
     * @return the cell's y coordinate
     */
    public int getY() {
        return this.y;
    }

    /**
     * getter for the boolean value checking if the player is currently on the cell
     * @return true if the player is currently on the cell, false otherwise
     */
    public boolean isPlayerOnCell() {
        return this.playerOnCell;
    }

    /**
     * setter for the discovered attribute
     * @param disc true if the cell is to be discovered, false to undiscover it
     */
    public void setDiscovered (boolean disc) {
        this.discovered = disc;
    }

    /**
     * setter for a wall of the cell
     * @param direction the direction of the wall to update
     * @param status the type of wall to set (cf Wall enum)
     */
    public void setWall(Direction direction, Wall status) {
        this.walls.put(direction, status);
    }

}
