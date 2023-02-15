package mazegame.items;
import mazegame.entities.*;
import mazegame.data.*;

public class Shield extends Stuff { 

    protected float dodge; //dodge rate, e.g the chance to negate the damage from a hit
    
    /* Constructor with undefined owner */
    public Shield(String name, float dodge){
        super(name);
        this.dodge = dodge;
    }

    /* Constructor with defined owner */
    public Shield(String name, float dodge, Entity entity){
        super(name);
        this.dodge = dodge;
        this.entity = entity;
    }

    @Override
    public String toString() {
        return (this.name + Colors.colorize(" (shield " + this.dodge+ ")", Colors.WHITE));
    }

    /**
     * getter for the dodge
     * @return the dodge
     */
    public float getDodge(){
        return this.dodge;
    }

    /**
     * equipes this shield
     */
    public void equip(){
        this.entity.getStuff().set(2, this);
    }
}
