package mazegame.entities;

import java.util.HashMap;

import mazegame.*;
import mazegame.items.*;

public abstract class Npc extends Entity implements java.lang.Cloneable{

    protected String description;
    protected int movements; //how many steps the NPC will walk by itself each turn

    /* Constructor with empty backpack and movements defaulted to 1 */
    public Npc(String name, int healthPoints, int strength, Game game) {
        super(name, new HashMap<Item, Integer>(), healthPoints, strength, game);
        game.getNpcs().add(this);
        this.movements = 1;
    }

    /* Constructor with given backpack and movements defaulted to 1 */
    public Npc(String name, HashMap<Item, Integer> hashMap, int healthPoints, int strength, Game game) {
        super(name, hashMap, healthPoints, strength, game);
        game.getNpcs().add(this);
        this.movements = 1;
    }

    /* Constructor with empty backpack */
    public Npc(String name, int healthPoints, int strength, Game game, int movements) {
        super(name,  new HashMap<Item, Integer>(), healthPoints, strength, game);
        game.getNpcs().add(this);
        this.movements = movements;
    }

    /* Constructor with given backpack */
    public Npc(String name, HashMap<Item, Integer> hashMap, int healthPoints, int strength, Game game, int movements) {
        super(name, hashMap, healthPoints, strength, game);
        game.getNpcs().add(this);
        this.movements = movements;
    }

    /**
     * manages player's interaction with the NPC
     * will prompt a series of choices the player can pick from to interact with the NPC
     */
    public abstract void interact();

    /**
     * getter for the description
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * getter for the movement
     * @return the movement
     */
    public int getMovements(){
        return this.movements;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return (Npc) super.clone();
    }

}
