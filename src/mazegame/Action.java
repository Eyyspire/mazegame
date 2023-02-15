package mazegame;
import mazegame.cells.*;
import mazegame.entities.*;
import mazegame.items.*;
import mazegame.divers.*;
import mazegame.data.*;
import java.util.*;

public class Action {
    private Game game;

    public Action(Game game) {
        this.game = game;
    }

    /**
     * Lets the player choose one of the Npc  
    */
    public void npc_interaction(Scanner scan) {
        boolean interacting = true;

        while (interacting) {
            int nbNpc = 0;
            List<Entity> npcsOnCell = this.game.getPlayer().getCell().getNPCs();
            // If there is no Npc on the cell, leave the interaction with a message
            if (npcsOnCell.size() == 0) {
                System.out.println("There is no one here.");
                interacting = false;
            } else {
                System.out.println("Choose someone : ");
                // display all Npcs
                for (Entity npc : npcsOnCell) {
                    Npc copy = (Npc) npc;
                    System.out.println(Colors.colorize(nbNpc + " - " +copy.getDescription(), Colors.WHITE));
                    nbNpc += 1;
                }
                System.out.println(Colors.colorize(nbNpc + "- I don't want to interact anymore.", Colors.WHITE));
                int interaction_choice = 0;
                // We are trying to get the option, which has to be a number
                try {
                    interaction_choice = scan.nextInt();
                    scan.nextLine();
                } catch (InputMismatchException e) {
                    // if the input is not a number, we catch the error and tell the player to make a valid choice
                    System.out.println(Colors.colorize("Wrong input type, please choose an option (int)", Colors.RED));
                    // nextLine clean the standard input after catching the error
                    scan.nextLine();
                    continue;
                }
                // if the choice is an int but is not an available option
                if (interaction_choice < 0 || interaction_choice > nbNpc) {
                    System.out.println(Colors.colorize("Invalid choice.", Colors.RED));
                } 
                // if the player choose one of the Npc, we call the interaction method of the target Npc
                else if (interaction_choice < nbNpc) {
                    Npc npc_interaction = (Npc) npcsOnCell.get(interaction_choice);
                    System.out.println("You want to interact with " + npc_interaction.getDescription());
                    npc_interaction.interact();
                // if the player does not want to interact
                } else {
                    interacting = false;
                }
            }

        }
    }

    /**
     * Lets the player choose one of the available legal moves
     * @return true if the player move, false if the player wants to exit the game
    */
    public boolean direction_interaction(Scanner scan) {
        boolean interacting = true;
        boolean end = false;
        HashMap<String, Integer> options = new HashMap<>(); 
        options.put("z", 0);    
        options.put("q", 3); 
        options.put("s", 2); 
        options.put("d", 1);

        while (interacting) {
            // tell the player if any npc is on the cell and list them
            List<Entity> npcsOnCell = this.game.getPlayer().getCell().getNPCs();

            if (npcsOnCell.size() == 0) {
                System.out.println("No one here, what do you want to do ?");

            } else {
                System.out.println("We aren't alone here, what do you want to do ?");
                System.out.println(Colors.colorize("You can interact with :", Colors.BLUE));
                for (Entity npc : npcsOnCell) {
                    Npc copy = (Npc) npc;
                    System.out.println(Colors.colorize("- " + copy.getDescription(), Colors.BLUE));
                }
            }

            List<Direction> movment_possibilities = new ArrayList<>();
            int nb_movment = 0;
            // for each direction, we display it and add it to the movment_possibilities 
            for (Direction direction: Direction.values()){
                movment_possibilities.add(direction);
                System.out.println(Colors.colorize(nb_movment + " - "+ direction.toString(), Colors.WHITE));
                nb_movment += 1;
            }
            // other options 
            System.out.println(Colors.colorize(nb_movment + " - See backpack.", Colors.WHITE) );
            if(npcsOnCell.size() == 0){
                System.out.println(Colors.colorize(nb_movment + 1 + " - Interact with Entities.", Colors.WHITE) );
            } else {
                System.out.println(Colors.colorize(nb_movment + 1 + " - Interact with Entities.", Colors.BLUE) );
            }
            System.out.println(Colors.colorize(nb_movment + 2 + " - See map.", Colors.WHITE) );
            System.out.println(Colors.colorize(nb_movment + 3 + " - Exit game.", Colors.WHITE) );
            System.out.println(Colors.colorize(nb_movment + 4 + " - Help.", Colors.WHITE) );
            String choice_input = "";
            int movment_choice = -1;
            
            // We are trying to get the option, which has to be a number or a direction
            choice_input = scan.nextLine();

            try {
                movment_choice = Integer.parseInt(choice_input);
                
            } catch (Exception e) {
                // if the input is not a number, we catch the error and tell the player to make a valid choice
                if (!options.containsKey(choice_input)) {
                    System.out.println(Colors.colorize("Wrong input, please choose an option (int) or a direction (z q s d)", Colors.RED));
                    continue;
                }

                movment_choice = options.get(choice_input);

            }
            // if the choice is an int but is not an available option
            if (movment_choice < 0 || movment_choice > nb_movment + 4) {
                System.out.println(Colors.colorize("Invalid choice.", Colors.RED));
            } 
            // the player wants to move
            else if (movment_choice < nb_movment) {
                if (this.game.isMovementAllowed(this.game.getPlayer(), movment_possibilities.get(movment_choice))) {
                    this.game.moveTo(this.game.getPlayer(), movment_possibilities.get(movment_choice));
                    interacting = false;
                } else {
                    System.out.println(Colors.colorize("There is a wall blocking the way.", Colors.RED));
                }
            } 
            // "see backpack" option
            else if (movment_choice == nb_movment) {
                System.out.println("Backpack :");
                backpack_interaction(scan);
            }
            else if (movment_choice == nb_movment + 1) {
                this.npc_interaction(scan);
            }
            // "see map" option
            else if (movment_choice == nb_movment + 2) {
                System.out.println("Here is the map :");
                System.out.println(this.game.getMaze().toString());
            }   
            // "exit game" option         
            else if(movment_choice == nb_movment + 3) {
                interacting = false;
                end = true;
            }
            //help
            else{
                this.help();
            }
        }

        return end;
    }

    public void help(){
        String help = new String("Your quest will be here to find out how to get out of this maze.\n");
        help = help.concat(new String("You may move in the maze with Z/Q/S/D keys instead of 0/1/2/3.\n"));
        help = help.concat(new String("You will meet different characters during your journey. Each of them will be useful if you decide to talk with them. But you also can decide to fight them. Watch out, some of them are quite powerful. Once the fight has begun, you can't escape anymore...\n"));
        help = help.concat(new String("To become stronger, you may buy stuffs (armor, shield and weapon) to the trader or even craft some more powerful ones in your craftingTable. Then, to equip your stuff, go into your backpack, select your stuff item, and select \"equip\".\n"));
        help = help.concat(new String("In order to go in your crafting table, go in your backpack and select craftingTable, then, select the item you want to craft.\n"));
        help = help.concat(new String("In your backpack, you also can use your usable items, selecting this time \"use\" after selecting the item. You have different kinds of usable objects.\n"));
        help = help.concat(new String("You have healing items : apple (give you back some health points), applePie (restore you to full life). You also have scrolls : hintScrolls, that will help you in yout quest, and teleportScroll that will teleport you to a random cell of this maze... You also can find a pickaxe, thaht will allow you to break some walls.\n"));

        System.out.println(Colors.colorize(help, Colors.PURPLE));      
    }

    /**
     * la fonction n'est pas encore terminée  
    */
    public void backpack_interaction(Scanner scan) {
        boolean interacting = true;
        int nb_items;
        int choice;
        HashMap<Item, Integer> player_bag;
        Object keys[];

        while (interacting) {
            player_bag = this.game.getPlayer().getItems();
            keys = player_bag.keySet().toArray();
            nb_items = display_backpack(player_bag, keys);
            System.out.println(Colors.colorize((nb_items) + " - craft someting.", Colors.WHITE));
            System.out.println(Colors.colorize((nb_items + 1)+ " - leave backpack.", Colors.WHITE));
            try {
                choice = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(Colors.colorize("Invalid input", Colors.RED));
                scan.nextLine();
                continue;
            }

            if (choice < 0 || choice > (nb_items + 1)) {
                System.out.println(Colors.colorize("Invalid input", Colors.RED));
            } else if (choice == nb_items) {
                craftint_table_interaction(scan);
            } else if (choice == nb_items + 1) {
                interacting= false;
            } else {
                item_interaction(scan, (Item) keys[choice]);
            }

        }
    }

    public void craftint_table_interaction(Scanner scan) {
        CraftingTable table = this.game.getCraftingTable();
        HashMap<Item ,HashMap<Item, Integer>> recipes = table.getRecipes();
        Object craftable_items[] = recipes.keySet().toArray();
        int nb_items = craftable_items.length;


        boolean interacting = true;
        int choice = nb_items;

        while (interacting) {
            System.out.println("Here are the items you can craft.");
            display_craftable_items(craftable_items, recipes);
            System.out.println(Colors.colorize(nb_items + " - " + "leave crafting table.", Colors.WHITE));

            try {
                System.out.println("Choose an option :");
                choice = scan.nextInt();

            } catch (InputMismatchException e) {
                System.out.println(Colors.colorize("Wrong input.", Colors.RED));
                scan.nextLine();
                continue;
            }

            if (choice < 0 || choice > nb_items) {
                System.out.println(Colors.colorize("Invalid choice.", Colors.RED));
            } else if (choice == nb_items) {
                interacting = false;
            } else {
                if(table.canCraft((Item) craftable_items[choice])) {
                    table.updateItem((Item) craftable_items[choice]);
                    System.out.println(Colors.colorize("A new ", Colors.GREEN) + craftable_items[choice].toString() + Colors.colorize(" has been added in your inventory.", Colors.GREEN));
                    craftable_items = recipes.keySet().toArray();
                    nb_items = craftable_items.length;
                } else {
                    System.out.println(Colors.colorize("You can't craft this item yet.", Colors.RED));
                }
            }

        }

    }

    public void display_craftable_items(Object items[], HashMap<Item ,HashMap<Item, Integer>> recipes) {
        int nb_items = 0;
        for (Object item : items) {
            Item copy = (Item) item;
            System.out.println(nb_items + " - " + copy.toString() + " :");
            HashMap<Item, Integer> ingredients = copy.getRecipe();
            for (Map.Entry<Item, Integer> entry : ingredients.entrySet()) {
                Item ingre = entry.getKey();
                Integer amount = entry.getValue();
                System.out.println(" "+ingre.toString() + " : " + amount);
            }
            System.out.println(" ");
            nb_items += 1;
        }
    }

    public void item_interaction(Scanner scan, Item item) {
        System.out.println("What are you willing to do ?");
        System.out.println(Colors.colorize("0 - Use.", Colors.WHITE));
        System.out.println(Colors.colorize("1 - Equip.", Colors.WHITE));
        System.out.println(Colors.colorize("2 - Throw it.", Colors.WHITE));
        System.out.println(Colors.colorize("3 - Nothing.", Colors.WHITE));
        int max_choice = 3;
        int choice = 0;
        boolean valid_choice = false;
        while (!valid_choice) {
            try {
                choice = scan.nextInt();
                scan.nextLine();
                if (choice >= 0 && choice <= max_choice) {
                    valid_choice = true;
                } else {
                    System.out.println(Colors.colorize("Invalid input.", Colors.RED));
                }
            } catch (InputMismatchException e) {
                System.out.println(Colors.colorize("Invalid input.", Colors.RED));
                scan.nextLine();
            }
        }

        switch (choice) {
            case 0:
                if (!(item instanceof UsableItem)) {
                    System.out.println(Colors.colorize("You can't use this item.", Colors.RED));
                    break;
                }
                UsableItem usable_copy = (UsableItem) item;
                usable_copy.use();
                break;
            case 1:
                if (!(item instanceof Stuff)) {
                    System.out.println(Colors.colorize("You can't equip this item.", Colors.RED));
                    break;
                }
                Stuff stuff_copy = (Stuff) item;
                stuff_copy.equip();
                break;
            case 2:
                if(item instanceof Key){
                System.out.println(Colors.colorize("\nAre you sure you want to throw the key ?", Colors.RESET));
                System.out.println(Colors.colorize("After that, you may not be able to win the game anymore...", Colors.RESET));
                System.out.println(Colors.colorize("*except if you find the secret breakable edge wall*\n", Colors.RESET));
                HintScroll.HasSecretHintBeenFound = true;
                }
                else{
                System.out.println("Are you sure you want to throw this item ?");
                System.out.println("You have an unlimited magic backpack that can contain anything.");
                }
                System.out.println("yes/no :");
                String answer = scan.nextLine();
                if (answer.equals("yes") || answer.equals("y")) {
                    
                    System.out.println(Colors.colorize("What a waste..", Colors.RED));
                    if(item instanceof Key){
                        this.game.getPlayer().addItem(new Pickaxe(-1), 1);
                        System.out.println(Colors.colorize("Take this pickaxe with unlimited utilisations. You have now to find out the secret edge breakable wall and break it. You don't have others options anymore...", Colors.GREEN));
                        System.out.println(Colors.colorize("One Pickaxe with unlimited utilisations has been added to your backpack", Colors.GREEN));
                    }
                    this.game.getPlayer().removeItem(item, 1);
                } else {
                    System.out.println(Colors.colorize("Invalid input. Keep your items è-é.", Colors.RED));
                }
                break;
        }

    }

    /**
     * display all the player's items with their quantity 
     * @return the number of different items in the backpack
    */
    private int display_backpack(HashMap<Item, Integer> player_bag, Object keys[]) {
        int nb_items = 0;
        for(Object key : keys) {
            Item copy_key = (Item) key;
            Integer value = player_bag.get(key);
            System.out.println(nb_items + " - " + copy_key.toString() + " : " + value);
            nb_items += 1;
        }
        return nb_items;
    }

    /**
     * Pick all items on the current player's cell
    */
    public void pickItemsOnGround() {
        Player player = this.game.getPlayer();
        Cell playerCell = player.getCell();
        HashMap<Item, Integer> items = playerCell.getItems();
        // A separate array containing all items to not interfere with the hashmap during the loop
        Object keys[] = items.keySet().toArray();

        for(int i = 0; i < keys.length; i++) {
            Item item = (Item) keys[i];
            int amount = items.get(item);
            System.out.println(Colors.colorize("You found " + amount + " ", Colors.GREEN) + item.toString() + Colors.colorize(".", Colors.GREEN));

            player.pickUp(item, amount);
        }
    }
    
}
