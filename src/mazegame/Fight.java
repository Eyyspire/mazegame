package mazegame;
import mazegame.entities.*;
import mazegame.items.*;
import mazegame.data.*;

import java.util.*;

public class Fight {

    private Game game;
    private ArrayList<Entity> goodBoys;
    private ArrayList<Entity> badBoys;
    int index;
    int choice;
    Scanner scan;

    public Fight(Game game, ArrayList<Entity> goodBoys,  ArrayList<Entity> badBoys){

        this.game = game;
        this.goodBoys = goodBoys;
        this.badBoys = badBoys;
        this.index = 0;
        this.choice = -1; //Choice of the NPC to attack.
        this.scan = new Scanner(System.in);

    }

    /**
     * Plays a turn where the attacker tries to deal damages to the defender.
     * @param attacker the attacker of one side
     * @param defender the defender of the other side
     */
    public void turn(Entity attacker, Entity defender){
        if(attacker != this.game.getPlayer()){
            if(this.goodBoys.contains(attacker)){
                System.out.println(Colors.colorize("Good ", Colors.BLUE) + attacker.getName() + " targets " + Colors.colorize("bad ", Colors.RED) + defender.getName());
            } else {
                System.out.println(Colors.colorize("Bad ", Colors.RED) + attacker.getName() + " targets " + Colors.colorize("good ", Colors.BLUE) + defender.getName());
            }
        }
        int strength = attacker.getStrength() + ((Weapon) attacker.getStuff().get(0)).getStrength();
        int damagesStrength = new Random().nextInt(Math.max((int) Math.floor(strength * 0.2), 1)) + strength - (int) Math.floor(strength * 0.2);
        int damages = damagesStrength - ((Armor) defender.getStuff().get(1)).getProtection();
        if (((Shield) defender.getStuff().get(2)).getDodge() < new Random().nextFloat()){ //Dogde didn't work.
            defender.setHealthPoints(defender.getHealthPoints() - damages);
            System.out.println("This dealt " + Colors.colorize(Integer.toString(damages) + " damages !", Colors.RED));
            this.hasDied(defender); //Checks if the fighter is dead or not.
        } else { //Dodge worked.
            System.out.println("Oh no ! The attack has been dodged !");
        }
    }

    /**
     * Checks if the fighter is dead or not. If so, he will be removed from everywhere.
     * @param fighter the fighter to check
     */
    public void hasDied(Entity fighter){
        if(fighter.getHealthPoints() <= 0){
            if(fighter == this.game.getPlayer()){ //If the fighter who is dead is the player, the game ends.
                System.out.println("YOU ARE DEAD *resident evil sound effect*");
                System.exit(0);
            }
            //Else we remove the dead NPC from the side, the cell, and the game.
            if(this.badBoys.contains(fighter)){
                System.out.println("Fatality ! " + Colors.colorize("Bad ", Colors.RED) + fighter.getName() + " just left the chat because of this attack !");
               
                for(Item item : fighter.getItems().keySet()) {
                    this.game.getPlayer().addItem(item, fighter.getItems().get(item));
                    System.out.println("You just looted " + String.valueOf(fighter.getItems().get(item)) + " " +  item.toString());
                }
                this.badBoys.remove(this.badBoys.indexOf(fighter));
            } else {
                System.out.println("Fatality ! " + Colors.colorize("Good ", Colors.BLUE) + fighter.getName() + " just left the chat because of this attack !");
                this.goodBoys.remove(this.goodBoys.indexOf(fighter));
            }
            this.game.getNpcs().remove(fighter);
            fighter.getCell().getNPCs().remove(fighter);
        }
    }
    

    /**
     * Plays an entire fight between two sides.
     * @return a boolean in order to tell that the fight is over
     */
    public boolean fight(){
        this.index = 0;
        while (!this.isEnding()){
            this.choice = -1;
            if(this.index % this.goodBoys.size() == this.goodBoys.indexOf(this.game.getPlayer())){//If the entity is the player, we begin an interaction.
                this.interaction();
                if(this.choice != -1){
                    this.turn(this.goodBoys.get(this.index % this.goodBoys.size()), this.badBoys.get(this.choice % this.badBoys.size()));
                }
            } else {
                System.out.println(Colors.colorize("Good ", Colors.BLUE) + this.goodBoys.get(this.index % this.goodBoys.size()).getName() + " decides to attack.");
                this.turn(this.goodBoys.get(this.index % this.goodBoys.size()), this.badBoys.get(new Random().nextInt() % this.badBoys.size()));
            }
            if(!this.isEnding()){
                System.out.println(Colors.colorize("Bad ", Colors.RED) + this.badBoys.get(this.index % this.badBoys.size()).getName() + " decides to attack.");
                this.turn(this.badBoys.get(this.index % this.badBoys.size()), this.goodBoys.get(new Random().nextInt() % this.goodBoys.size()));
            }
            this.index ++;
        }
        System.out.println(Colors.colorize("Well done, traveler ! You are victorious !", Colors.BRIGHTYELLOW)); //If the fights ends before the end of the game, that must say that the player has won the fight.
        return true;
    }

    /**
     * Gives the player the choice between several actions and sereval targets. Also gives informations about health points of each side.
     */
    public void interaction(){
        for(int i = 0; i < this.goodBoys.size(); i++){
            System.out.println(Colors.colorize("Good ", Colors.BLUE) + this.goodBoys.get(i).getName() + " has " + Colors.colorize(Integer.toString(this.goodBoys.get(i).getHealthPoints()) + " HPs", Colors.RED) + " left.");
        }
        for(int i = 0; i < this.badBoys.size(); i++){
            System.out.println(Colors.colorize("Bad ", Colors.RED) + this.badBoys.get(i).getName() + " has " + Colors.colorize(Integer.toString(this.badBoys.get(i).getHealthPoints()) + " HPs", Colors.RED) + " left.");
        }
        System.out.println(Colors.colorize("====================================", Colors.PURPLE));
        boolean invalid = true;
        int action = 1;
        while(invalid){
            System.out.println("What do you want to do ?");
            System.out.println(Colors.colorize("1. Heal myself.\n2. ATTACK !", Colors.WHITE));
            try {
                action = this.scan.nextInt();
                scan.nextLine();
                invalid = false;
            } catch (InputMismatchException e) {
                System.out.println(Colors.colorize("Invalid option. You will attack your opponent.", Colors.RED));
                scan.nextLine();
                invalid = true;
            }
        }
        System.out.println(Colors.colorize("====================================", Colors.PURPLE));
        switch(action){
            case 1:
                if(this.game.getPlayer().getHealthPoints() == this.game.getPlayer().getMaxHealthPoints()){
                    System.out.println("You're already full HP ! Too late, your turn ends.");
                    break;
                }
                System.out.println("You decided to recover some HPs.");
                invalid = true;
                while(invalid){
                    System.out.println("Have you got something ?");
                    ArrayList<Item> healingItems = this.listOfHealingItems();
                    if(healingItems.isEmpty()){
                        System.out.println("Unfortunately you've got nothing ! Too late, your turn ends.");
                        break;
                    }
                    try {
                        this.choice = this.scan.nextInt();
                        ((HealingItem) healingItems.get(this.choice)).use();
                        System.out.println("You're healing, and you're now up to " + Colors.colorize(String.valueOf(this.game.getPlayer().getHealthPoints()) + " HPs.", Colors.RED));
                        invalid = false;
                    } catch(Exception e){
                        System.out.println(Colors.colorize("You can't cancel the recovery.", Colors.RED));
                        invalid = true;
                    }
                    this.choice = -1;
                }
                break;
            case 2:
            default:
                System.out.println("You decided to attack.");
                if(this.badBoys.size() == 1){
                    this.choice = 0;
                    invalid = false;
                } else {
                    invalid = true;
                }
                while(invalid){
                    System.out.println("Who do you want to attack ?");
                    this.listOfBadBoys();
                    try {
                        this.choice = this.scan.nextInt();
                        this.badBoys.get(this.choice).getName();
                        scan.nextLine();
                        invalid = false;
                    } catch(Exception e){
                        System.out.println(Colors.colorize("You can't cancel the attack.", Colors.RED));
                        invalid = true;
                    }
                }
                System.out.println("You target " + Colors.colorize("bad ", Colors.RED) + this.badBoys.get(this.choice).getName());

        }
    }

    /**
     * Prints a list of ennemies.
     */
    public void listOfBadBoys(){
        for(int i = 0; i < this.badBoys.size(); i++){
            System.out.println(i + ". " + Colors.colorize("Bad ", Colors.RED) + this.badBoys.get(i).getName());
        }
    }

    /**
     * Prints a list of your healing items.
     */
    public ArrayList<Item> listOfHealingItems(){
        ArrayList<Item> healingItems = new ArrayList<>();
        int i = 0;
        for(Item item : this.game.getPlayer().getItems().keySet()){
            if(item instanceof HealingItem){
                System.out.println(String.valueOf(i) + " - " + item.getName() + " (you have " + String.valueOf(this.game.getPlayer().getItems().get(item)) + ")");
                i++;
                healingItems.add(item);
            }
        }
        return healingItems;
    }

    /**
     * Checks if one of the sides is defeated.
     * @return true if there is no one left in any side
     */
    public boolean isEnding(){
        return (this.goodBoys.size() == 0) || (this.badBoys.size() == 0);
    }
    /**
     * Getter for the game in which takes place the fight.
     * @return the game
     */
    public Game getGame(){
        return this.game;
    }

    /**
     * Getter for the good side of the fight (generally the side of the player).
     * @return the good side
     */
    public ArrayList<Entity> getGoodBoys(){
        return this.goodBoys;
    }

    /**
     * Getter for the bad side of the fight (generally the side of the ennemies).
     * @return
     */
    public ArrayList<Entity> getBadBoys(){
        return this.badBoys;
    }
    
}
