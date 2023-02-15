package mazegame.divers;

import java.util.*;
import mazegame.*;
import mazegame.data.ExistingCraftableItems;
import mazegame.items.*;

public class CraftingTable {

    //On a ajouté l'attribut recette aux items : est-ce nécessaire d'avoir un attribut recettes pour la table de craft ?

    private HashMap<Item ,HashMap<Item, Integer>> recipes;
    private Game game;
    private ArrayList<Item> craftableItems;

    public CraftingTable(Game game){
        this.game = game;
        this.recipes = new HashMap<Item ,HashMap<Item, Integer>>();
        for (ExistingCraftableItems craft:  ExistingCraftableItems.values()){
            Item item = craft.getItem();
            this.recipes.put(item, item.getRecipe());
        }  
        this.game.setCraftingTable(this);
        this.craftableItems = new ArrayList<>();
    }
    
    /**
     * getter for the recipes
     * @return the recipes
     */
    public HashMap<Item ,HashMap<Item, Integer>> getRecipes(){
        return this.recipes;
    }

    /**
     * getter for the game
     * @return the game
     */
    public Game getGame(){
        return this.game;
    }

    /**
     * whether an item can be craft with the current items in the player's backpack
     * e.g the player has the necessary ingredients in their bag
     * @param item the item we are looking to craft
     * @return true if the player can craft this item, false otherwise
     */
    public Boolean canCraft(Item item){
        HashMap<Item, Integer> recipe = item.getRecipe();
        Boolean res = true;
        for (Item item_b: recipe.keySet()){
            if (!this.game.getPlayer().getItems().containsKey(item_b)){
                res = false; 
                break;
            }
            if(this.game.getPlayer().getItems().get(item_b) < recipe.get(item_b)){
               res = false; 
               break;
            }
        }
        return res;
    }

    /**
     * updates the player's backpack by adding an item and removing its ingredients from their bag
     * @param item the item to add to the player's backpack
     */
    public void updateItem(Item item){
        HashMap<Item, Integer> recipe = item.getRecipe();
        this.game.getPlayer().addItem(item, 1);
         for (Item item_b: recipe.keySet()){
            this.game.getPlayer().removeItem(item_b, recipe.get(item_b));
        }
    }

    public void addCraftableItem(Item item){
        this.craftableItems.add(item);
    }

    public ArrayList<Item> getCraftableItem(){
        return this.craftableItems;
    }
}
