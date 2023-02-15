package mazegame.items;

import mazegame.entities.*;
import mazegame.data.*;

public abstract class UsableItem extends Item implements java.lang.Cloneable {

    protected int numberOfUses; //how many times the item can be used before it is destroyed

    /* Constructor with a set number of uses and undefined owner */
    public UsableItem(String name, int numberOfUses){
        super(name);
        this.numberOfUses = numberOfUses;
    }

    /* Constructor with infinite use and undefined owner */
    public UsableItem (String name){
        super(name);
        this.numberOfUses = -1;
    }

    /* Constructor with infinite use and defined owner */
    public UsableItem (String name, Entity entity){
        super(name, entity);
        this.numberOfUses = -1;
    }

    /* Constructor with a set number of uses and defined owner */
    public UsableItem (String name, int numberOfUses, Entity entity){
        super(name, entity);
        this.numberOfUses = numberOfUses;
    }

    /**
     * uses the item once and removes it from the backpack if no more uses available
     * the effect of the item will vary depending on the item
     */
    // public void use() {
    //     this.numberOfUses -= 1;
    //     if(this.numberOfUses == 0) {
    //         this.entity.removeItem(this, 1);
    //     }
    // }

    public void use() {
        this.entity.removeItem(this, 1);
        try {
            UsableItem item_clone = (UsableItem) this.clone();
            item_clone.setNumberOfUses(this.numberOfUses - 1);
            if(item_clone.getNumberOfUses() > 0) {
                this.entity.addItem(item_clone, 1);
            }
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    /**
     * getter for the number of uses
     * @return the number of uses
     */
    public int getNumberOfUses() {
        return this.numberOfUses;
    }

    /**
     * setter for the number of uses
     * @param nb the new number of uses
     */
    public void setNumberOfUses(int nb) {
        this.numberOfUses = nb;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof UsableItem) {
            UsableItem copy = (UsableItem) o;
            return (copy.getName().equals(this.name) && copy.getNumberOfUses() == this.numberOfUses);
        }

        return false;
    }

    @Override
    public String toString() {
        if (this.numberOfUses > 0){
            return (this.name + Colors.colorize(" (durability " + this.numberOfUses+ ")", Colors.WHITE));
        }
        else{
            return (this.name + Colors.colorize(" (infinite durability)", Colors.WHITE));
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
