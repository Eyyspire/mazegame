package mazegame.items;

import java.util.*;
import mazegame.entities.*;
import mazegame.data.*;

public class WoodenSword extends Weapon {

    private static HashMap<Item, Integer> RECIPE = new HashMap<Item, Integer> (){{
        put(new Wood(), 3);
    }};

    /* Constructor with undefined owner */
    public WoodenSword() {
        super(Colors.colorize("wooden sword", Colors.PURPLE), 3);
    }

    /* Constructor with defined owner */
    public WoodenSword(Entity entity) {
        super(Colors.colorize("wooden sword", Colors.PURPLE), 3, entity);
    }

    /**
     * getter for the recipe
     * @return the recipe
     */
    public HashMap<Item, Integer> getRecipe(){
        return WoodenSword.RECIPE;
    }

}
