package mazegame.data;

import mazegame.items.*;

public enum TraderPrices{

    //quantity is the amount of items the trader's backpack will be filled with
    //set quantity to 0 if you do not want the trader to sell an item
    APPLE(new Apple(), 10, 1),
    APPLEPIE(new ApplePie(), 50, 0),
    ARIANETHREAD(new ArianeThread(), 125, 0),
    HINTSCROLL(new HintScroll(), 10, 1),
    IRON(new Iron(), 10, 5),
    IRONARMOR(new IronArmor(), 60, 0),
    IRONSHIELD(new IronShield(), 50, 0),
    IRONSWORD(new IronSword(), 40, 0),
    PICKAXE(new Pickaxe(3), 50, 1),
    TELEPORTSCROLL(new TeleportScroll(), 10, 1),
    WOOD(new Wood(), 5, 10),
    WOODENARMOR(new WoodenArmor(), 40, 1),
    WOODENSHIELD(new WoodenShield(), 30, 1),
    WOODENSWORD(new WoodenSword(), 20, 1);


    private Item item;
    private int price;
    private int quantity;

    private TraderPrices(Item item, int price, int quantity){
        this.item = item;
        this.price = price;
        this.quantity = quantity;
    }

    public Item getItem() {
        return this.item;
    }
 
    public int getPrice() {
        return this.price;
    }

    public int getQuantity() {
        return this.quantity;
    }

}
