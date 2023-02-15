package mazegame.items;

import java.util.*;
import mazegame.entities.*;
import mazegame.data.*;

public class IronShield extends Shield {

    private static HashMap<Item, Integer> RECIPE = new HashMap<Item, Integer> (){{
        put(new Iron(), 4);
    }};

    /* Constructor with undefined owner */
    public IronShield() {
        super(Colors.colorize("iron shield", Colors.BRIGHTBLACK), 0.2f);
    }

    /* Constructor with defined owner */
    public IronShield(Entity entity) {
        super(Colors.colorize("iron shield", Colors.BRIGHTBLACK), 0.2f, entity);
    }

    /**
     * getter for the recipe
     * @return the recipe
     */
    public HashMap<Item, Integer> getRecipe(){
        return IronShield.RECIPE;
    }

}
