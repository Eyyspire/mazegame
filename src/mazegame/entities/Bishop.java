package mazegame.entities;

import mazegame.*;
import mazegame.items.*;
import mazegame.data.*;
import java.util.*;

public class Bishop extends Npc{

    private Scanner scan;

    /**
     * Builds a bishop.
     * 
     * @param healthPoints the number of life points he has.
     * @param strength     the number of attacking points he has.
     * @param game         the game in which he is.
     */
    public Bishop(int healthPoints, int strength, Game game) {
        super("bishop", healthPoints, strength, game, 0);
        this.description = "An elderly bishop, disturbingly looking at you...";
        this.scan = new Scanner(System.in);
    }

    /**
     * interacts with the bishop
     * the player can either :
     * - talk to the bishop
     * - ask the bishop for some heal, which they will do if the player helps them in return
     * - starts a fight sequence
     * - leave
     */
    public void interact() {
        Boolean stop = false;

        while (!stop) {
            System.out.println(Colors.colorize("0 - talk.\n1 - ask for some heal.\n2 - ask for some fight\n3 - leave.", Colors.WHITE));
            String action = scan.next();
            scan.nextLine();

            switch (action) {
                case "0" :
                case "talk":
                    System.out.println("Bishop : \"What a good time to be catholic ! Wait, there's no sun...\"");
                    break;
                
                case "1":
                case "ask for some heal":
                    System.out.println("Bishop : \"Here is a magic fountain, throw gold coins in it to get some heal!\"");
                    System.out.println("Enter the number of gold you want to throw in the magic foutain");
                    String heal = scan.next();
                    scan.nextLine();
                    this.heal(heal);
                    break;
                

                case "2":
                case "ask for some fight":
                    System.out.println("You decided to fight the bishop.");
                    System.out.println("The bishop stands like a rock, you can almost feel a presence around them...");
                    stop = new Fight(this.game, new ArrayList<Entity>(Arrays.asList(this.game.getPlayer())), new ArrayList<Entity>(Arrays.asList(this))).fight();
                    break;
                case "3":
                case "leave":
                    System.out.println("Bishop : \"Come to see me more often ! I am so alone...\"");
                    stop = true;
                    break;
                
                default:
                    System.out.println(Colors.colorize("Action not found.", Colors.RED));
                    break;
    
            }
        }

    }

    public void heal(String heal){
        int healthPoints = 0;
        try{
            healthPoints = Integer.parseInt(heal);
        }
        catch(InputMismatchException e){
            System.out.println(Colors.colorize("Enter an integer.", Colors.RED));
        }
        if(!this.game.getPlayer().getItems().containsKey(new Gold())) {
            System.out.println(Colors.colorize("You don't have any gold in your back", Colors.RED));
        }
        else if(this.game.getPlayer().getItems().get(new Gold()) < healthPoints) {
            System.out.println(Colors.colorize("You don't have enough gold...", Colors.RED));
        }
        else{
            this.game.getPlayer().removeItem(new Gold(), healthPoints);
            this.game.getPlayer().changeHealthPoints(healthPoints);
        }
    }

}
