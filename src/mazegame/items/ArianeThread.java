package mazegame.items;

import mazegame.data.*;

public class ArianeThread extends UsableItem {

    public ArianeThread() {
        super(Colors.colorize("Ariane thread", Colors.BRIGHTYELLOW), 1);
    }

    public void use() {
        System.out.println("You used the magical " + Colors.colorize("thread", Colors.BRIGHTYELLOW) + " and teleported out of the maze. Congrats !");
        System.exit(0);
    }
    
}
