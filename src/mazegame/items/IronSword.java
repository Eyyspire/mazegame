package mazegame.items;
import mazegame.entities.*;
import mazegame.data.*;

import java.util.*;

public class IronSword extends Weapon {

    private static HashMap<Item, Integer> RECIPE = new HashMap<Item, Integer> (){{
        put(new Iron(), 3);
    }};

    /* Constructor with undefined owner */
    public IronSword() {
        super(Colors.colorize("iron sword", Colors.BRIGHTBLACK), 6);
    }

    /* Constructor with defined owner */
    public IronSword(Entity entity) {
        super(Colors.colorize("iron sword", Colors.BRIGHTBLACK), 6, entity);
    }

    /**
     * getter for the recipe
     * @return the recipe
     */
    public HashMap<Item, Integer> getRecipe(){
        return IronSword.RECIPE;
    }

}
