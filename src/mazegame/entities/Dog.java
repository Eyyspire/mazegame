package mazegame.entities;

import mazegame.*;
import mazegame.items.WoodenSword;
import mazegame.data.*;
import java.util.*;

public class Dog extends Npc {

    private Scanner scan; 
    private int pat_counter; //how many times the dog was pet

    /**
     * Builds a dog.
     * 
     * @param game the game in which he is.
     */
    public Dog(Game game) {
        super("dog", 10000, 70, game);
        this.description = "a cool dog with a backpack.";
        this.pat_counter = 0;
        this.scan = new Scanner(System.in);
    }

    /**
     * interacts with the dog
     * the player can either :
     * - pet the dog : this has no effect, unless it is the 10th pet, in that case the dog will give the player a wooden sword
     * - starts a fight sequence
     * - leave
     */
    public void interact() {
        Boolean stop = false;

        while (!stop) {
            System.out.println(Colors.colorize("0 - pet it.\n1 - kill it.\n2 - leave.", Colors.WHITE));
            String action = scan.nextLine();

            switch (action) {
                case "0" :
                case "pet it":
                    System.out.println("You pet the dog.");
                    this.pat_counter += 1;

                    if (pat_counter == 10) {
                        System.out.println(Colors.colorize("You have pet the dog 10 times, it wants to bless you with a cool stick in his backpack.", Colors.BRIGHTYELLOW));
                        this.game.getPlayer().addItem(new WoodenSword(this.game.getPlayer()), 1);
                        System.out.println("The dog gave you a " + Colors.colorize("wooden sword.", Colors.PURPLE));
                    }
                    break;
                
                case "1":
                case "kill it":
                    System.out.println("You decided to kill the dog.");
                    System.out.println("The dog isn't happy and wants to fight.");
                    stop = new Fight(this.game, new ArrayList<Entity>(Arrays.asList(this.game.getPlayer())), new ArrayList<Entity>(Arrays.asList(this))).fight();
                    break;
                
                case "2":
                case "leave":
                    System.out.println("Bye bye doggo.");
                    stop = true;
                    break;
                
                default:
                    System.out.println(Colors.colorize("Action not found.", Colors.RED));
                    break;
    
            }
        }

    }
}
