package mazegame.entities;

import mazegame.*;
import mazegame.data.Colors;
import mazegame.items.*;
import java.util.*;

public class Minotaur extends Npc{
    
    private Weapon choppy;
    private Scanner scan;
    
    public Minotaur(Game game){
        super("trapdoor", 150, 10, game, 0);
        this.description = "a strange trapdoor, with some dust on it.";
        this.choppy = new MinotaurChoppy(this);
        this.equip(this.choppy);
        this.addItem(this.choppy, 1);
        this.addItem(new ArianeThread(), 1);
        this.scan = new Scanner(System.in);
    }

    public void interact(){
        boolean invalid = true;
        String action;

        System.out.println("This trapdoor seems to be locked...");
        System.out.println("Do you want to unlock it ?");
        while(invalid){
            System.out.println(Colors.colorize("0. yes\n1. no", Colors.WHITE));
            action = this.scan.nextLine();
            switch(action){
                case "0":
                case "yes":
                    boolean found = false;
                    for(Item item : this.game.getPlayer().getItems().keySet()){
                        if(item.equals(new Key())){
                            System.out.println(Colors.colorize("Thanks to the key you collected before, it opened !", Colors.BRIGHTYELLOW));
                            HintScroll.HasSecretHintBeenFound = true;
                            this.game.getPlayer().removeItem(item, 1);
                            found = true;
                            break;
                        }
                    }
                    if(!found){
                        System.out.println("You can't open it, for now...");
                        invalid = false;
                        break;
                    }
                    System.out.println("Oh no ! That was the boss' trapdoor ! Prepare to fight this monster, stranger !");
                    this.name = "minotaur";
                    this.description = "the minotaur of the maze, always angry, we never knew why.";
                    invalid = !(new Fight(this.game, new ArrayList<Entity>(Arrays.asList(this.game.getPlayer())), new ArrayList<Entity>(Arrays.asList(this))).fight());
                    System.out.println(Colors.colorize("Wow, traveler ! I'm impressed !", Colors.GREEN));
                    System.out.println("Oh look ! " + Colors.colorize("a magical thread !", Colors.BRIGHTYELLOW) + " With this thing, you be able to exit this maze !");
                    System.out.println(Colors.colorize("And leave us alone in the dark...", Colors.BRIGHTBLACK));
                    System.out.println("Well played !");
                    break;
                case "1":
                case "no":
                    System.out.println("Well, you can come back later.");
                    invalid = false;
                    break;
                default:
                    System.out.println(Colors.colorize("You can't say 'MaYbE'...", Colors.RED));
                    break;
            }
        }
    }
}
