package mazegame.items;
import mazegame.entities.*;
import mazegame.data.*;

public class Iron extends Materials {

    /* Constructor with undefined owner */
    public Iron() {
        super(Colors.colorize("iron", Colors.BRIGHTBLACK));
    }

    /* Constructor with defined owner */
    public Iron(Entity entity) {
        super(Colors.colorize("iron", Colors.BRIGHTBLACK), entity);
    }   
}
