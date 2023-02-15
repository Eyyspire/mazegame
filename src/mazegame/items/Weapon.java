package mazegame.items;
import mazegame.entities.*;
import mazegame.data.*;

public class Weapon extends Stuff { 

    protected int strength; //the strength will up the owner's damage
    
    /* Constructor with undefined owner */
    public Weapon(String name, int strength){
        super(name);
        this.strength = strength;
    }

    /* Constructor with defined owner */
    public Weapon(String name, int strength, Entity entity){
        super(name);
        this.strength = strength;
        this.entity = entity;
    }

    @Override
    public String toString() {
        return (this.name + Colors.colorize(" (strength " + this.strength+ ")", Colors.WHITE));
    }

    /**
     * getter for the strength
     * @return the strength
     */
    public int getStrength(){
        return this.strength;
    }

    /**
     * equips this weapon
     */
    public void equip(){
        this.entity.getStuff().set(0, this);
    }
}
