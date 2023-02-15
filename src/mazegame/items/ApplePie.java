package mazegame.items;

import java.util.*;

public class ApplePie extends HealingItem {

    private static HashMap<Item, Integer> RECIPE = new HashMap<Item, Integer> (){{
        put(new Apple(), 3);
    }};
    
    /* Constructor with undefined owner */
    public ApplePie(){
        super("applePie");
    }
    
    /* restore player's health */
    public void use(){
        this.getEntity().setHealthPoints(this.getEntity().getMaxHealthPoints());
        super.use();
    }

    /**
     * getter for the recipe
     * @return the recipe
     */
    public HashMap<Item, Integer> getRecipe(){
        return ApplePie.RECIPE;
    }
}
