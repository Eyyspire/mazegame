package mazegame.items;

import java.util.*;
import mazegame.entities.*;
import mazegame.data.*;

public class IronArmor extends Armor {

    private static HashMap<Item, Integer> RECIPE = new HashMap<Item, Integer> (){{
        put(new Iron(), 5);
    }};

    /* Constructor with undefined owner */
    public IronArmor() {
        super(Colors.colorize("iron armor", Colors.BRIGHTBLACK), 6);
    }

    /* Constructor with defined owner */
    public IronArmor(Entity entity) {
        super(Colors.colorize("iron armor", Colors.BRIGHTBLACK), 6, entity);
    }

    /**
     * getter for the recipe
     * @return the recipe
     */
    public HashMap<Item, Integer> getRecipe(){
        return IronArmor.RECIPE;
    }

}
