package mazegame.entities;

import mazegame.items.*;

import java.util.*;

import mazegame.*;
import mazegame.data.Colors;
import mazegame.data.TraderPrices;


public class Player extends Entity {

    /* Constructor with an empty backpack */
    public Player(String name, int healthPoints, int strength, Game game) {
        super(name, new HashMap<>(), healthPoints, strength, game);
        game.setPlayer(this);
    }

    /* Constructor with a given backpack */
    public Player(String name, HashMap<Item, Integer> backPack, int healthPoints, Game game) {
        super(name, backPack, healthPoints, 1, game);
        game.setPlayer(this);
    }

    /**
     * picks up an item from the ground and adds it to the backpack
     * @param item the item to pick up
     * @param quantity the quantity to pick up
     */
    public void pickUp(Item item, int quantity) {
        this.addItem(item, quantity); //adds to backpack
        if (item instanceof Key){
            HintScroll.HasKeyBeenFound = true;
        }
        this.getCell().removeItem(item, quantity); //removes from ground
    }

    /**
     * buys a set quantity of an item from a trader. The buying process will fail if
     * either :
     * - the player does not have enough gold
     * - the trader does not own desired amount of an item
     * - the trader has not set a price for the item
     * Otherwise, the process will end successfully and the trader and player's
     * backpacks will be updated accordingly.
     * 
     * @param item     the item to buy
     * @param quantity how much to buy
     * @param trader   trader to buy from
     */
    public void buy(Item item, int quantity, Trader trader) {
        int price = 0;

        for (TraderPrices tp : TraderPrices.values()) {
            if(item.equals(tp.getItem())) {
                price = tp.getPrice();
            }
        }

        int fortune = 0;
        for(Item i : this.backPack.keySet()){
            if(i.equals(new Gold())){
                fortune = this.backPack.get(i);
            }
        };

        if(fortune < price) {
            System.out.println(Colors.colorize("Not enough cash, stranger!", Colors.RED));
            System.out.println("For now you have " + String.valueOf(fortune) + Colors.colorize(" gold.", Colors.YELLOW));
            return;
        }
        boolean buy = trader.sell(item, quantity);
        if(buy) {
            this.removeItem(new Gold(), price);
            this.addItem(item, quantity);
            item.setEntity(this);
        }
        return;
    }

    /**
     * uses an item and removes one use from the total number of uses allowed
     * the effect of this action will change based on the item that is used
     * @param item the item to use
     */
    public void use(Item item) {
        ((UsableItem) item).use();
    }
}