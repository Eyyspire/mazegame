package mazegame.entities;

import mazegame.data.Colors;
import mazegame.data.VillageoisPhrases;
import mazegame.*;
import java.util.*;

public class Villageois extends Npc {

    private Scanner scan;
    private boolean hasSpoken;
    private String memory;

    public static int HINT_INDEX = 0;
    public static char HINT = 'A';
    
    /**
     * Builds a ~villageois~.
     * 
     * @param game the game in which the villageois is.
     */
    public Villageois(Game game){
        super("Villageois", 3, 1, game, 1);
        this.description = "A ~villageois~";
        this.scan = new Scanner(System.in);
        this.hasSpoken = false;
    }

    /**
     * interacts with the villager
     * the player can either :
     * - talk to the villager
     * - starts a fight sequence
     * - leave
     */
    public void interact() {
        Boolean stop = false;

        while (!stop) {
            System.out.println(Colors.colorize("0 - talk.\n1 - fight.\n2 - leave.", Colors.WHITE));
            String action = scan.next();

            switch (action) {
                case "0" :
                case "talk":
                if(!hasSpoken){
                    this.memory = VillageoisPhrases.valueOf("" + Villageois.HINT).getHint();
                    System.out.println(Colors.colorize("\"" + this.memory  + "\"", Colors.GREEN));
                    Villageois.HINT_INDEX = ((Villageois.HINT_INDEX +1) % VillageoisPhrases.values().length);
                    Villageois.HINT = (char) (Villageois.HINT_INDEX + 65);
                    this.hasSpoken = true;
                }
                else{
                    System.out.println(Colors.colorize("\"" + this.memory, Colors.GREEN) + " I don't know anything else : try to talk to other villagers\"");
                }
                    break;
                
                case "1":
                case "fight":
                    System.out.println("You decided to kill the ~villageois~.");
                    System.out.println("The ~villageois~ looks a bit questionned, they do not understand why you draw your weapon.");
                    stop = new Fight(this.game, new ArrayList<Entity>(Arrays.asList(this.game.getPlayer())), new ArrayList<Entity>(Arrays.asList(this))).fight();
                    break;
                
                case "2":
                case "leave":
                    System.out.println("See you later, Stranger !.");
                    stop = true;
                    break;
                
                default:
                    System.out.println(Colors.colorize("Action not found.", Colors.RED));
                    break;
    
            }
        }
    }
}
