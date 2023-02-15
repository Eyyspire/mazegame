package mazegame.items;
import mazegame.entities.*;
import mazegame.data.*;

public class Wood extends Materials {

    /* Constructor with undefined owner */
    public Wood() {
        super(Colors.colorize("wood", Colors.PURPLE));
    }

    /* Constructor with defined owner */
    public Wood(Entity entity) {
        super(Colors.colorize("wood", Colors.PURPLE), entity);
    }
    
    
}
