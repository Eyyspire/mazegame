package mazegame.entities;

import java.util.*;

import mazegame.items.*;
import mazegame.*;
import mazegame.data.Colors;
import mazegame.data.TraderPrices;

public class Trader extends Npc {

    //price in gold per unit of item. Only items with a price can be sold.
    protected HashMap<String, Integer> prices;
    protected Scanner scan;

    /**
     * Builds a trader.
     * 
     * @param name         the name of the trader.
     * @param healthPoints the healthpoints of the trader.
     * @param strength     the strength of the trader.
     * @param game         the game in which the trader is.
     */
    public Trader(String name, int healthPoints, int strength, Game game) {
        super(name, healthPoints, strength, game, 0);
        this.description = "a peaceful trader, waiting for customers to visit him.";
        // this.prices = new HashMap<>();
        this.scan = new Scanner(System.in);
    }

    /**
     * The trader will sell a given quantity of an item they offer to the player. They can only sell an item they possess, and for which a price is set.
     * @param item the item to sell
     * @param quantity the quantity to sell
     */
    public boolean sell(Item item, int quantity) {
        if(!this.backPack.containsKey(item)) {
            System.out.println(Colors.colorize("I am not selling this at the moment.", Colors.RED));
            return false;
        }
        if(this.backPack.get(item) < quantity) {
            System.out.println(Colors.colorize("I don't have that much !", Colors.RED));
            return false;
        }
        // if(!this.prices.containsKey(item.toString())) {
        //     System.out.println("I did not set a price for this just yet.");
        //     return false;
        // }

        int price = 0;

        for (TraderPrices tp : TraderPrices.values()) {
            if(item.equals(tp.getItem())) {
                price = tp.getPrice();
            }
        }

        this.addItem(new Gold(), price*quantity);

        this.removeItem(item, quantity);

        System.out.println(Colors.colorize("Heh heh heh... Thank you!", Colors.GREEN));

        // // if the item is soldout, we erase it from this.prices
        // if(!this.backPack.containsKey(item)) {
        //     this.prices.remove(item.toString());
        // }
        return true;

    }


    /**
     * Display all the items in the shop with their price
     * @param prices hashmap containing all the items associated with their price
    */
    private ArrayList<Item> display_prices() {
        int nb_items = 0;
        ArrayList<Item> toSell = new ArrayList<>();

        for(Item item : this.getItems().keySet()) {
            int price = 0;

            if(!item.equals(new Gold())) {
            

                for (TraderPrices tp : TraderPrices.values()) {
                    if(item.equals(tp.getItem())) {
                        price = tp.getPrice();
                    }
                }

                toSell.add(item);
                System.out.println(nb_items + " - " + item.toString() + " : " + String.valueOf(price));
                nb_items += 1;
            }

            
        }

        return toSell;
    }

    /**
     * Getter for the trader's prices
     * @return HashMap containing every items in the shop with their price
    */
    public HashMap<String, Integer> getPrices() {
        return this.prices;
    }

    /** 
    * interacts with the trader
    * the player can either :
    * - buy something from the trader : this will give the player a catalogue of items to buy from
    * - start a fight sequence
    * - leave
    */
    public void interact() {
        System.out.println("Welcome ! Want to buy something ?");
        Boolean stop = false;

        while (!stop) {
            System.out.println(Colors.colorize("0 - buy something.\n1 - fight.\n2 - leave.", Colors.WHITE));
            String action = scan.nextLine();

            switch (action) {
                case "0" :
                case "buy something":
                    System.out.println("What're ya buyin?");
                    ArrayList<Item> toSell = this.display_prices();
                    int nb_items = toSell.size();
                    // after displaying the prices we allow the player to leave the shop with the option below
                    System.out.println(Colors.colorize(nb_items + " - leave shop.", Colors.WHITE));
                    action = scan.nextLine();
                    // while the player does not choose "leave shop"
                    while(!action.equals(Integer.toString(nb_items))){
                        // if the player gives an invalid input (see next comment), we catch the error without breaking the loop
                        // an error can be thrown if the player choose a wrong number or if we can't parse the input
                        try {
                            this.game.getPlayer().buy(toSell.get(Integer.parseInt(action)), 1, this);
                        } catch (Exception e) {
                            System.out.println("I don't have this item, here are the items I have:");
                        }
                        toSell = this.display_prices();
                        nb_items = toSell.size();
                        System.out.println(nb_items + " - leave shop.");
                        action = scan.nextLine();
                    }
                    System.out.println("Well, you can come back when you want !");
                    break;
                
                case "1":
                case "fight":
                    System.out.println("You decided to kill the trader.");
                    System.out.println("The trader looks scared as hell, you can read it in their eyes.");
                    // init a new fight between the Trader and the player
                    stop = new Fight(this.game, new ArrayList<Entity>(Arrays.asList(this.game.getPlayer())), new ArrayList<Entity>(Arrays.asList(this))).fight();
                    break;
                
                case "2":
                case "leave":
                    System.out.println("See you later, Traveler !.");
                    stop = true;
                    break;
                
                default:
                    System.out.println(Colors.colorize("Action not found.", Colors.RED));
                    break;
    
            }
        }
    }
}
