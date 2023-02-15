package mazegame.items;

import mazegame.entities.*;
import mazegame.data.*;

public class Gold extends Materials {

    /* Constructor with undefined owner */
    public Gold() {
        super(Colors.colorize("gold", Colors.YELLOW));
    }

    /* Constructor with defined owner */
    public Gold(Entity entity) {
        super(Colors.colorize("gold", Colors.YELLOW), entity);
    }
    
}
