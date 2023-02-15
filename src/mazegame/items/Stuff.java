package mazegame.items;
import mazegame.entities.*;

public abstract class Stuff extends Item { 

    public Stuff(String name){
        super(name);
    }

    public Stuff(String name, Entity entity){
        super(name);
        this.entity = entity;
    }

    /**
     * equips this piece of stuff
     */
    public abstract void equip();
} 