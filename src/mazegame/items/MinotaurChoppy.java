package mazegame.items;

import mazegame.data.Colors;
import mazegame.entities.*;

public class MinotaurChoppy extends Weapon {
    
    public MinotaurChoppy() {
        super(Colors.colorize("minotaur choppy", Colors.BRIGHTYELLOW), 7);
    }

    public MinotaurChoppy(Entity entity){
        super(Colors.colorize("minotaur choppy", Colors.BRIGHTYELLOW), 7, entity);
    }
}
