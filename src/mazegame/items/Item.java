package mazegame.items;

import java.util.*;

import mazegame.entities.*;

public abstract class Item {

    private static HashMap<Item, Integer> RECIPE = new HashMap<Item, Integer>();

    protected String name;
    protected Entity entity; //the owner of the item
    
    /* Constructor with undefined owner */
    public Item (String name){
        this.name = name;
        this.entity = null;
    }

    /* Constructor with defined owner */
    public Item (String name, Entity entity){
        this.name = name;
        this.entity = entity;
        entity.addItem(this, 1);
    }

    /**
     * setter for the entity
     * @param entity the entity set to be the new owner of the item
     */
    public void setEntity(Entity entity){
        this.entity = entity;
    }

    /**
     * getter for the entity
     * @return the entity
     */
    public Entity getEntity(){
        return this.entity;
    }

    /**
     * getter for the name
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * getter for the recipe
     * @return the recipe
     */
    public HashMap<Item, Integer> getRecipe(){
        return RECIPE;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Item) {
            Item copy = (Item) o;
            return (copy.getName().equals(this.name));
        }

        return false;
    }

    @Override
    public int hashCode() {
        return getName() != null ? getName().hashCode() : 0;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
