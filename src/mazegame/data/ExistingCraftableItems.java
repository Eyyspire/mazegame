package mazegame.data;

import mazegame.items.*;

public enum ExistingCraftableItems {
    APPLEPIE(new ApplePie()), IRONARMOR(new IronArmor()), IRONSHIELD(new IronShield()), IRONSWORD(new IronSword()), PICKAXE(new Pickaxe()), WOODENARMOR(new WoodenArmor()), WOODENSHIELD(new WoodenShield()), WOODENSWORD(new WoodenSword());

    private Item item;

    private ExistingCraftableItems(Item item){
        this.item = item;
    }

    public Item getItem(){
        return item;
    }
}
