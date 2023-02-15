package mazegame.items;

import java.util.*;
import mazegame.entities.*;
import mazegame.data.*;

public class WoodenShield extends Shield {

    private static HashMap<Item, Integer> RECIPE = new HashMap<Item, Integer> (){{
        put(new Wood(), 4);
    }};

    /* Constructor with undefined owner */
    public WoodenShield() {
        super(Colors.colorize("wooden shield", Colors.PURPLE), 0.1f);
    }

    /* Constructor with defined owner */
    public WoodenShield(Entity entity) {
        super(Colors.colorize("wooden shield", Colors.PURPLE), 0.1f, entity);
    }

    /**
     * getter for the recipe
     * @return the recipe
     */
    public HashMap<Item, Integer> getRecipe(){
        return WoodenShield.RECIPE;
    }

}
