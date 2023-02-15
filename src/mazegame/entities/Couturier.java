// paye beaucoup de gold pour obtenir le fil

package mazegame.entities;

/*import java.util.*;*/

import mazegame.items.*;
import mazegame.*;
import mazegame.data.Colors;

public class Couturier extends Trader {

    private boolean isLocked;

    public Couturier(String name, int healthPoints, int strength, Game game){
        super(name, healthPoints, strength, game);
        this.description = "a strange trapdoor, with some dust on it.";
        this.addItem(new ArianeThread(), 1);
        this.isLocked = true;
    }

    public void interact(){
        if(this.isLocked){
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
                        System.out.println("You see a trader, and decided to talk to him.");
                        this.name = "couturier";
                        this.description = "A trader, but he looks different from the others...";
                        this.isLocked = false;
                        super.interact();
                        invalid = false;
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
        } else {
            super.interact();
        }
    }

}