package mazegame.items;

import java.util.*;
import mazegame.entities.*;
import mazegame.data.*;

public class WoodenArmor extends Armor {

    private static HashMap<Item, Integer> RECIPE = new HashMap<Item, Integer> (){{
        put(new Wood(), 5);
    }};

    /* Constructor with undefined owner */
    public WoodenArmor() {
        super(Colors.colorize("wooden armor", Colors.PURPLE), 3);
    }

    /* Constructor with defined owner */
    public WoodenArmor(Entity entity) {
        super(Colors.colorize("wooden armor", Colors.PURPLE), 3, entity);
    }

    /**
     * getter for the recipe
     * @return the recipe
     */
    public HashMap<Item, Integer> getRecipe(){
        return WoodenArmor.RECIPE;
    }

}
