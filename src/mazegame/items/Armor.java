package mazegame.items;
import mazegame.entities.*;
import mazegame.data.*;

public class Armor extends Stuff { 

    protected int protection; //the protection reduces the damage taken
    
    /* Constructor with undefined owner */
    public Armor(String name, int protection){
        super(name);
        this.protection = protection;
    }

    /* Constructor with defined owner */
    public Armor(String name, int protection, Entity entity){
        super(name);
        this.protection = protection;
        this.entity = entity;
    }

    @Override
    public String toString() {
        return (this.name + Colors.colorize(" (protection " + this.protection+ ")", Colors.WHITE));
    }

    /**
     * getter for the protection
     * @return the protection
     */
    public int getProtection(){
        return this.protection;
    }

    /**
     * equips this piece of armor
     */
    public void equip(){
        this.entity.getStuff().set(1, this);
    }
}
