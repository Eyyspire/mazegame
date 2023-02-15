package mazegame.items;
import mazegame.entities.*;

public abstract class Materials extends Item {
    
    /* Constructor with undefined owner */
    public Materials(String name) {
        super(name);
    }

    /* Constructor with defined owner */
    public Materials(String name, Entity entity){
        super(name, entity);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Materials) {
            Materials copy = (Materials) o;
            return copy.name.equals(this.name);
        }

        return false;
    }
}
