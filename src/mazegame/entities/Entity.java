package mazegame.entities;

import mazegame.cells.*;
import mazegame.items.*;
import mazegame.*;

import java.util.*;

public abstract class Entity {
    
    protected Game game;
    protected String name;
    protected HashMap<Item, Integer> backPack; //each Item in the backpack has an associated quantity
    protected int healthPoints; //healthpoints can be gained and lost during a game, if down to zero, player dies (loses the game or respawn)
    protected Cell cell; //where the entity is currently standing
    protected int strength; //strength will influence how much damage this entity deals on hit
    protected ArrayList<Stuff> stuff; //the entity's stuff (armor, shield, weapon)
    protected int maxHealthPoints;

    /**
     * Builds an entity.
     * 
     * @param name         the name of the entity.
     * @param backPack     the items in the backpack of the entity.
     * @param healthPoints the healthpoints of the entity.
     * @param strength     the strength of the entity.
     * @param game         the game in which the entity is.
     */
    public Entity(String name, HashMap<Item, Integer> backPack, int healthPoints, int strength, Game game) {
        this.name = name;
        this.backPack = backPack;
        this.healthPoints = healthPoints;
        this.strength = strength;
        this.game = game;
        this.stuff = new ArrayList<Stuff>(Arrays.asList(new Weapon("hand", 0), new Armor("shirt", 0), new Shield("hand", 0)));
        this.maxHealthPoints = healthPoints;
    }

    /**
     * adds an item to the entity's backpack
     * @param item item to add
     * @param quantity quantity to add
     */
    public void addItem(Item item, int quantity){
        for(Item i : this.backPack.keySet()) {
            if(i.equals(item)) {
                int oldQuantity = this.backPack.get(i);
                this.backPack.replace(i, oldQuantity + quantity);
                return;
            }
        }
        item.setEntity(this);
        this.backPack.put(item, quantity);
    }

    /**
     * removes an item to the entity's backpack
     * @param item item to remove
     * @param quantity quantity to remove
     */
    public void removeItem(Item item, int quantity) {
        for(Item i : this.backPack.keySet()) {
            if(i.equals(item)) {
                int oldQuantity = this.backPack.get(i);
                this.backPack.replace(i, oldQuantity - quantity);
                if(this.backPack.get(i) <= 0) {
                    this.backPack.remove(i);
                }
                return;
            }
        }

    }

    /**
     * hits a targeted entity
     * @param damage the amount of healthpoints the target will lose (based on hitter's strength)
     */
    public void hit(Entity target){
        target.changeHealthPoints(-this.strength);
    }

    /**
     * getter for the backpack
     * @return the entity's backpack
     */
    public HashMap<Item, Integer> getItems() {
        return this.backPack;
    }

    /**
     * getter for the healthpoints
     * @return the entity's current healthpoints
     */
    public int getHealthPoints() {
        return this.healthPoints;
    }
    /**
     * getter for the max healthpoints
     * @return the entity's max healthpoints
     */
    public int getMaxHealthPoints() {
        return this.maxHealthPoints;
    }

    /**
     * setter for the healthpoints
     * 
     * @param points new number of healthpoints.
     */
    public void setHealthPoints(int points){
        this.healthPoints = points;
    }

    /**
     * updates the healthpoints
     * @param points the number to add (or substract if negative param) to the current healthpoints
     */
    public void changeHealthPoints(int points){
        this.healthPoints = Math.min(this.maxHealthPoints, this.healthPoints + points);
    }

    /**
     * getter for the cell
     * @return the entity's current cell
     */
    public Cell getCell(){
        return this.cell;
    }

    /**
     * setter for the cell
     * @param cell the new cell for the entity
     */
    public void setCell(Cell cell){
        this.cell = cell;
    }

    /**
     * getter for the game
     * @return the game
     */
    public Game getGame(){
        return this.game;
    }

    /**
     * getter for the name
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * equips a piece of stuff : the entity will now wield a weapon, or wear a piece of armor, etc.
     * @param stuff the piece of stuff to equip
     */
    public void equip(Stuff stuff){
        stuff.equip();
    }

    /**
     * getter for the stuff
     * @return the stuff
     */
    public ArrayList<Stuff> getStuff(){
        return this.stuff;
    }

    /**
     * getter for the strength
     * @return the strength
     */
    public int getStrength(){
        return this.strength;
    }

}
