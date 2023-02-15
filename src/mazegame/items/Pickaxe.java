package mazegame.items;
import mazegame.entities.*;
import mazegame.util.*;
import mazegame.cells.*;

import java.util.*;

public class Pickaxe extends UsableItem {

    private static HashMap<Item, Integer> RECIPE = new HashMap<Item, Integer> (){{
        put(new Iron(), 4);
        put(new Wood(), 1);
    }};

    /**
     * Builds a pickaxe.
     * 
     * @param numberOfUses the number of times that the pickaxe can be used.
     * @param entity       the owner of the pickaxe.
     */
    public Pickaxe(int numberOfUses, Entity entity){
        super("pickaxe", numberOfUses, entity);
    }

    /**
     * Builds a pickaxe
     * 
     * @param numberOfUses the number of times that the pickaxe can be used.
     */
    public Pickaxe(int numberOfUses){
        super("pickaxe", numberOfUses);
    }

    /**
     * Builds a pickaxe
     * 
     */
    public Pickaxe(){
        super("pickaxe", 3);
    }

    /* must give a direction (the wall to break) */
    public void use() {
        /* Ignoring the error */
        Scanner scan = new Scanner(System.in);
        boolean valid_input = false;
        Cell playerCell = this.entity.getCell();
        int input = -1;
        
        System.out.println("Here are the options:");
        List<Direction> directions = new ArrayList<>();
        int wallCounter = 0;
        for (Direction direction: Direction.values()){
            System.out.println(wallCounter + " - " + direction.toString());
            directions.add(direction);
            wallCounter += 1;
        }

        System.out.println(this.entity.getName() + ", choose an option.");
        do {
            try {
                input = scan.nextInt();
                scan.nextLine();
                valid_input = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please choose one of the options.");
                scan.nextLine();
            }

            if (input < 0 || input > wallCounter) {
                System.out.println("Invalid input, please choose one of the options.");
            }
        }
        while (!valid_input); 

        Wall choosenWall = playerCell.getWall(directions.get(input));
        destroyWall(playerCell, choosenWall, directions.get(input));
    }

    public void destroyWall(Cell targetCell, Wall wall, Direction direciton ) {
        Util util = this.entity.getGame().getMaze().getUtil();

        switch (wall) {
            case EDGE :
                System.out.println("Nice try, but you cannot escape this maze so easily.");
                break;
            case ABSENT :
                System.out.println("What are you trying to break ? There is no wall here.");
                break;
            case PRESENT :
                util.setWall(targetCell, direciton, Wall.ABSENT);
                super.use();
                System.out.println("Wow, a new path !");
                break;
            case SECRET_VICTORY :
                super.use();
                System.out.println("Congrats, you found the secret path !");
                System.exit(0);
            default : 
                System.out.println("Wait, what's that thing..? It doesn't look like a common wall.");
                System.out.println("I think we should not touch it for now.");
                break;
        }
    }


    /**
     * getter for the recipe
     * @return the recipe
     */
    public HashMap<Item, Integer> getRecipe(){
        return Pickaxe.RECIPE;
    }
}