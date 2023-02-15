package mazegame.items;
import mazegame.entities.*;

import java.util.Random;

public class TeleportScroll extends UsableItem {
    
    /* Constructor with undefined owner */
    public TeleportScroll(){
        super("teleport scroll", 1);
    }

    /* Constructor with defined owner */
    public TeleportScroll(Entity entity, int nbofuses){
        super("teleport scroll", nbofuses, entity);
    }

    /**
     * uses a teleport scroll
     * this will teleport the player to a random cell of the maze
     */
    public void use(){
        Random rand = new Random();
        int X, Y;
        do{
            X = rand.nextInt(this.entity.getGame().getMaze().getWidth());
            Y = rand.nextInt(this.entity.getGame().getMaze().getHeight()); 
        }while(this.entity.getCell().getX() == X && this.entity.getCell().getY() == Y);

        this.getEntity().getGame().moveToCell(this.entity, this.entity.getGame().getMaze().getCell(X,Y));
        super.use();
    }


}
