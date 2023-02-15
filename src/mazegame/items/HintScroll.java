package mazegame.items;
import mazegame.entities.*;
import mazegame.data.*;
import mazegame.cells.*;
import java.util.*;

public class HintScroll extends UsableItem {

    public static boolean HasKeyBeenFound = false;
    public static boolean HasSecretHintBeenFound = false;

    private int hintType; // 0 -> direction .. 1 -> distance
    private int objectType; // 0 -> key .. 1 -> minotaur .. 2 -> couturier .. 3 -> wall
    
    /* Constructor with undefined owner */
    public HintScroll(){
        super("hint scroll", 1);
    }

    /* Constructor with defined owner */
    public HintScroll(Entity entity){
        super("hint scroll", 1);
        this.entity = entity;
    }

    public void setObjectType(){
        Random random = new Random();
        
        if (!HintScroll.HasKeyBeenFound){
            this.objectType = random.nextInt(3); // all except wall
        }
        else if (HintScroll.HasKeyBeenFound && !HintScroll.HasSecretHintBeenFound){
            this.objectType = random.nextInt(2) + 1; //minotaur or couturier
        }
        else if (HintScroll.HasSecretHintBeenFound){
            this.objectType = random.nextInt(3) + 1; //all except key
        }
    }

    /**
     * uses the hint scroll
     * this will give the player a hint as to what to do or where to go
     */
    public void use(){
        String hint = new String("hint");
        Random random = new Random();

        this.hintType = random.nextInt(2);
        this.setObjectType();

        switch(this.hintType){
            case 0:
                Direction direction;
                switch(this.objectType){
                    case 0:
                        direction = computeDirection(this.getEntity().getGame().getKeyLocation());
                        hint = new String("The key is in this direction : " + direction.toString());
                        break;
                    case 1:
                        direction = computeDirection(this.getEntity().getGame().getMinotaur().getCell());
                        hint = new String("The Minotaur is in this direction : " + direction.toString());
                        break;
                    case 2:
                        direction = computeDirection(this.getEntity().getGame().getCouturier().getCell());
                        hint = new String("The Couturier is in this direction : " + direction.toString());
                        break;
                    case 3:
                        direction = computeDirection(this.getEntity().getGame().getMaze().getSecretWallLocation());
                        hint = new String("The secret breakable edge wall is in this direction : " + direction.toString());
                        break;
                }
            break;
            case 1:
                int distance;
                switch(this.objectType){
                    case 0:
                        distance = computeDistance(this.getEntity().getGame().getKeyLocation());
                        switch(distance){
                            case 1:
                                hint = new String("The key is " + distance +" cell away from here");
                                break;
                            case 0:
                                hint = new String("The key is juste here !");
                                break;
                            default:
                                hint = new String("The key is " + distance +" cells away from here");
                                break;
                        }
                        break;
                    case 1:
                        distance = computeDistance(this.getEntity().getGame().getMinotaur().getCell());
                        switch(distance){
                            case 1:
                                hint = new String("The minotaur is " + distance +" cell away from here");
                                break;
                            case 0:
                                hint = new String("The minotaur is juste here !");
                                break;
                            default:
                                hint = new String("The minotaur is " + distance +" cells away from here");
                                break;
                        }
                        break;
                    case 2:
                        distance = computeDistance(this.getEntity().getGame().getCouturier().getCell());
                        switch(distance){
                            case 1:
                                hint = new String("The Couturier is " + distance +" cell away from here");
                                break;
                            case 0:
                                hint = new String("The Couturier is juste here !");
                                break;
                            default:
                                hint = new String("The Couturier is " + distance +" cells away from here");
                                break;
                        }
                        break;
                    case 3:
                        distance = computeDistance(this.getEntity().getGame().getMaze().getSecretWallLocation());
                        switch(distance){
                            case 1:
                                hint = new String("The secret breakable edge wall is " + distance +" cell away from here");
                                break;
                            case 0:
                                hint = new String("The secret breakable edge wall is around this cell...");
                                break;
                            default:
                                hint = new String("The secret breakable edge wall is " + distance +" cells away from here");
                                break;
                        }
                        break;
                }
            break;
        }

        String separator = "";
        for(int i = 0; i < hint.length(); i++){
            separator += "~";
        }

        System.out.println("Here is what you can read on this paper :");
        System.out.println(Colors.colorize(separator, Colors.PURPLE));
        System.out.println(hint);
        System.out.println(Colors.colorize(separator, Colors.PURPLE));

        super.use();
    }

    public Direction computeDirection(Cell cell){
        Cell cellEntity = this.getEntity().getCell();
        int distance;
        distance = cellEntity.getY() - cell.getY();
        Direction res = Direction.NORTH;
        if(cell.getY() - cellEntity.getY() > distance){
            distance = cell.getY() - cellEntity.getY();
            res = Direction.SOUTH;
        }
        if(cellEntity.getX() - cell.getX() > distance){
            distance = cellEntity.getX() - cell.getX();
            res = Direction.WEST;
        }
        if(cell.getX() - cellEntity.getX() > distance){
            distance = cell.getX() - cellEntity.getX();
            res = Direction.EAST;
        }
        return res;
    }

    public int computeDistance(Cell cell){
        return Math.abs(this.getEntity().getCell().getX() - cell.getX()) + Math.abs(this.getEntity().getCell().getY() - cell.getY());
    }


}
