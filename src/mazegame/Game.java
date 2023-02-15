package mazegame;
import mazegame.cells.*;
import mazegame.entities.*;
import mazegame.generators.Generator;
import mazegame.generators.IterativeGeneration;
import mazegame.generators.RecursiveDivision;
import mazegame.generators.RecursiveGeneration;
import mazegame.items.*;
import mazegame.divers.*;
import mazegame.data.*;
import java.util.*;


public class Game {

    private Maze maze;
    private ArrayList<Npc> NPCs;
    private Player player;
    private Minotaur minotaur;
    private Couturier couturier;
    private HashMap<Item, Integer> items;
    private Action action;
    private CraftingTable ct;
    private Cell keyLocation;
    
    public Game(Maze maze, ArrayList<Npc> NPCs, Player player){
        this.maze = maze;
        this.NPCs = NPCs;
        this.player = player;
        this.action = new Action(this);
        this.items = new HashMap<>();
        this.minotaur = new Minotaur(this);
        this.couturier = new Couturier("trapdoor", 300, 10, this);
        this.ct = new CraftingTable(this);
    }

    public Game(){
        this(new Maze(10, 10), new ArrayList<Npc>());
    }

    public Game(Maze maze, ArrayList<Npc> NPCs){
        this.maze = maze;
        this.NPCs = NPCs;
        this.action = new Action(this);
        this.items = new HashMap<>();
        this.minotaur = new Minotaur(this);
        this.couturier = new Couturier("trapdoor", 300, 10, this);
        this.ct = new CraftingTable(this);
    }

    /**
     * Introdution to the game and initialisation of the basic mobs and items 
    */
    public boolean intro(Scanner scan, String choice){
        Maze maze = new Maze(2,2);
        Player player;
        String name;
        boolean end = false;

        System.out.println(Colors.colorize("Welcome to \"A-maze-in'\".", Colors.GREEN));

        
        switch (choice) {
            case "1":
                System.out.println("You choose the iterative maze.");
                maze = new Maze(10, 10, new IterativeGeneration());
                break;

            case "2":
                System.out.println("You choose the recursive generation maze.");
                maze = new Maze(10, 10, new RecursiveGeneration());
                maze.getUtil().setUndiscovered();
                break;

            case "3":
                System.out.println("You choose the recursive division maze.");
                maze = new Maze(10, 10, new RecursiveDivision());
                break;

            default:
                List<Generator> generators = new ArrayList<>(Arrays.asList(new IterativeGeneration(), new RecursiveGeneration(), new RecursiveDivision()));
                List<String> strings = new ArrayList<>(Arrays.asList("iterative", "recursive generation", "recursive division"));
                int index = new Random().nextInt(3);
                System.out.println(Colors.colorize("I don't understand your choice. That will be the " + strings.get(index) + " maze for you.", Colors.RED));
                maze = new Maze(10, 10, generators.get(index));
                if(index == 1){
                    maze.getUtil().setUndiscovered();
                }
                break;
        }

        this.initNpcs(maze);

        if (!end) {
            System.out.println("what should i call you, brave adventurer ? ");
            // cette ligne sert reellement a prendre le nom du joueur
            name = scan.nextLine();
            player = new Player(name, 100, 10, this);

            this.initPlayerBackpack();
            
            this.setPlayer(player);
            this.initEntities();
            // init items ici
            this.initItems();
            //Place le joueur a un endroit random
            this.place_random_player();

            System.out.println("Fine, let's begin.");

            System.out.println("\n=======================================================================================================================================================");
            System.out.println(Colors.colorize("\nIt's been so much time you're walking into this maze, and you just lost your Ariane thread : the precious item you could have used to get out of the maze...", Colors.GREEN));
            System.out.println(Colors.colorize("You will meet some guys called ~villageois~ in the maze. Don't hesitate to interact with them, they might give you some precious advice.\n", Colors.GREEN));
            System.out.println(Colors.colorize("You can get some more indications about how to play the game in the help menu\n", Colors.GREEN));
            System.out.println("=======================================================================================================================================================\n");
        }

        return end;

    }

    public void play(String choice) {
        Scanner scan = new Scanner(System.in);
        boolean end = this.intro(scan, choice);
        while (!end) {
            System.out.println(maze.toString());
            // prendre les items de la case si il y en a
            this.getAction().pickItemsOnGround();

            // choix du mouvement
            end = this.getAction().direction_interaction(scan);

            this.endTurnMovements();

        }
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public void place_random_player() {
        int rand_x = Math.min(this.maze.getWidth() - 1, (int) (Math.random() * this.maze.getWidth()));
        int rand_y = Math.min(this.maze.getHeight() - 1, (int) (Math.random() * this.maze.getHeight()));
        Cell rand_cell = this.maze.getCell(rand_x, rand_y);
        this.moveToCell(player, rand_cell);
    }


    /**
     * Moves an entity to a new cell
     * @param entity the entity to move
     * @param cell the destination cell
     */
    public void moveToCell(Entity entity, Cell cell){
        if (entity instanceof Player){
            if (entity.getCell() != null){
                entity.getCell().setPlayerOnCell(false);
            }
            cell.setPlayerOnCell(true);
            cell.setDiscovered(true);
        }
        else{
            entity.getCell().removeNPC((Npc) entity);
            cell.addNPC((Npc) entity);
        }
        entity.setCell(cell);
    }


    /**
     * Moves an entity by one cell towards a given direction
     * @param entity the entity to move
     * @param direction the direction to move towards
     */
    public void moveTo(Entity entity, Direction direction){
        Cell cell;
        Cell futureCell;
            switch (direction){
                case NORTH:
                    cell = entity.getCell();
                    futureCell = this.maze.getCell(cell.getX(), cell.getY()-1);
                    this.moveToCell(entity, futureCell);
                    break;
                case SOUTH:
                    cell = entity.getCell();
                    futureCell = this.maze.getCell(cell.getX(), cell.getY()+1);
                    this.moveToCell(entity, futureCell);
                    break;
                case WEST:
                    cell = entity.getCell();
                    futureCell = this.maze.getCell(cell.getX()-1, cell.getY());
                    this.moveToCell(entity, futureCell);
                    break;
                case EAST:
                    cell = entity.getCell();
                    futureCell = this.maze.getCell(cell.getX()+1, cell.getY());
                    this.moveToCell(entity, futureCell);
                    break;
            }
    }

    public boolean isMovementAllowed(Entity entity, Direction direction){
        return entity.getCell().getWall(direction) == Wall.ABSENT;
    }

    // EndTurnMovement For a NPC
    // Choose a random direction among all possible
    // reeated n times (where n is his movements by turn (not necessarily one))
    public void endTurnMovement(Npc npc){
        List<Direction> allowedMovements = new ArrayList<>();
        for (int i = 0; i < npc.getMovements(); i++){
            allowedMovements.clear();
            for (Direction direction: Direction.values()){
                if (isMovementAllowed(npc, direction)){
                    allowedMovements.add(direction);
                }
            }
            Direction finalDirection = randomDirection(allowedMovements);
            moveTo(npc, finalDirection);
        }
    }

    // End Turn movement for all npcs
    public void endTurnMovements(){
        for(Npc npc: NPCs){
            if (Math.random() > 0.5)
                this.endTurnMovement(npc);
        }
    }

    // choose a random direction among all allowed directions
    public Direction randomDirection(List<Direction> allowedMovements){
            Random rand = new Random();
            int index = rand.nextInt(allowedMovements.size());
            Direction direction = allowedMovements.get(index);
            return direction;
    }

    /**
     * Initialize the Npcs in the list of Npcs of the maze
     * @param maze the maze in which the list is
     */
    private void initNpcs(Maze maze){
        // Npc de base :
        Npc dogo = new Dog(this);
        Npc villager = new Villageois(this); /*Couturier en plus*/
        Npc bishop = new Bishop(100, 10, this);
        Sphynx sphynx = new Sphynx(50, 15, this);
        Trader trader = new Trader("trader", 130, 15, this); /*Name Quantity : HintScroll 10, TeleportScroll 10, Apple 10, ApplePie 10, Pickaxe 10, Iron 10, Wood 10, WoodenSword 10*/
        ArrayList<Npc> npcs = new ArrayList<>();

        initNpc(trader, (this.maze.getWidth() * this.maze.getHeight()) / 20, npcs);
        initNpc(dogo, 1, npcs);
        initNpc(villager, VillageoisPhrases.values().length + (this.maze.getWidth() * this.maze.getHeight()) / 25, npcs);
        initNpc(bishop, (this.maze.getWidth() * this.maze.getHeight()) / 50, npcs);
        initNpc(sphynx, (this.maze.getWidth() * this.maze.getHeight()) / 25, npcs);
        initNpc(this.minotaur, 1, npcs);
        initNpc(this.couturier, 1, npcs);

        this.maze = maze;
        this.NPCs = npcs;

        for(TraderPrices item : TraderPrices.values()) {
            if(item.getQuantity() != 0){
                trader.addItem(item.getItem(), item.getQuantity());
            }
        }
    }

    /**
     * Initialize a given quantity of Npc in the given list of Npcs
     * @param npc the npc to add
     * @param quantity the quantity of npcs to add
     * @param npcs the list in which the npcs are added
     */
    public void initNpc(Npc npc, int quantity, ArrayList<Npc> npcs){
        npc.addItem(new Gold(), 10);
        try{
        for(int i = 0; i < quantity; i++){
            if(!(npc instanceof Minotaur || npc instanceof Couturier)){
                npcs.add((Npc) npc.clone());
            } else {
                npcs.add(npc);
            }
        }
        }catch(Exception e){
            npcs.add(npc);
        }
    }
    /**
     * Initialises ("spawns") all entities in the maze
    */
    public void initEntities(){
        Random rand = new Random();
        int rand_x, rand_y;
        for (Npc npc : NPCs) {
            if(npc instanceof Minotaur){
                rand_x = rand.nextInt(this.maze.getWidth() / 3) + this.maze.getWidth() / 3;
                rand_y = rand.nextInt(this.maze.getHeight() / 3) + this.maze.getHeight() / 3;
            }
            else{
                rand_x = rand.nextInt(this.maze.getWidth());
                rand_y = rand.nextInt(this.maze.getHeight());
            }
            Cell rand_cell = this.maze.getCell(rand_x, rand_y);
            initEntity(npc, rand_cell);
        }
    }

    /**
     * Initialises ("spawns") an entity in a cell
     * @param entity the entity to spawn
     * @param cell the cell in which to spawn the entity
     */
    public void initEntity(Entity entity, Cell cell){
        if (entity instanceof Player){
            cell.setPlayerOnCell(true);
        }
        else{
            cell.addNPC((Npc) entity);
        }
        entity.setCell(cell);
    }

    /**
     * Init the items in the map
    */
    public void initItems(){

        int amountGold = this.maze.getWidth() * this.maze.getHeight() * 3;
        int amountWood = this.maze.getWidth() * this.maze.getHeight() / 5;
        int amountIron = this.maze.getWidth() * this.maze.getHeight() / 7;
        int amountHintScroll = this.maze.getWidth() * this.maze.getHeight() / 7;
        int amountTeleportScroll = this.maze.getWidth() * this.maze.getHeight() / 25;
        int amountApple = this.maze.getWidth() * this.maze.getHeight() / 10;

        initItem(new Gold(), amountGold, 5, 10);
        initItem(new Wood(), amountWood, 1, 2);
        initItem(new Iron(), amountIron, 1, 1);
        initItem(new Key(), 1, 1, 1);
        initItem(new HintScroll(), amountHintScroll, 1, 1);
        initItem(new HintScroll(), amountTeleportScroll, 1, 1);
        initItem(new HintScroll(), amountApple, 1, 1);
    }   

    /**
     * Dispatch the given element on the map
     * @param item the item to dispatch
     * @param quantity how much occurence of the item we want on the map
     * @param min the minimum quantity of the item we want on a cell (if it contains at least one occurence)
     * @param max the maximum quantity of the item we want to add on a cell (in a row)
     * commentaire bonus : si le random choisi 2 fois la même case on peu potentiellement avoir plus que "max" item sur la case
    */
    public void initItem(Item item, int quantity, int min, int max){
        Random rand = new Random();
        this.items.put(item, quantity);
        // while we still have some of the item left
        while(quantity > 0){
            // we choose a random cell in the map
            Cell cell = this.getMaze().getCell(rand.nextInt(this.getMaze().getWidth()), rand.nextInt(this.getMaze().getHeight()));
            int quantityToPut = 0;
            if (quantity < min) {
                quantityToPut = quantity;
            } else {
                // we choose a quantity between the minimum and the maximum (if enough quantity)
                quantityToPut = Math.max(min, rand.nextInt(max));
            }
            cell.addItem(item, quantityToPut);
            quantity -= quantityToPut;
            if(item instanceof Key){
                this.keyLocation = cell;
            }
        }
    }

    public void initPlayerBackpack(){
        player.addItem(new Gold(), 10);
        player.addItem(new Wood(), 2);
        player.addItem(new Iron(), 1);
        player.addItem(new HintScroll(this.player), 1);
        player.addItem(new Apple(), 1);
    }

    /**
     * Maze getter
     * @return the Maze
    */
    public Maze getMaze(){
        return this.maze;
    }

    /**
     * Player getter
     * @return the player
    */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Aciton getter
     * @return the action handler
    */
    public Action getAction() {
        return this.action;
    }

    /**
     * Items getter
     * @return all items with their quandtity
    */
    public HashMap<Item, Integer> getItems(){
        return this.items;
    }

    /**
     * Npc getter
     * @return the Npc list
    */
    public ArrayList<Npc> getNpcs(){
        return this.NPCs;
    }

    public CraftingTable getCraftingTable(){
        return this.ct;
    }

    public void setCraftingTable(CraftingTable ct){
        this.ct = ct;
    }

    public Cell getKeyLocation(){
        return this.keyLocation;
    }

    public Minotaur getMinotaur(){
        return this.minotaur;
    }

    public Couturier getCouturier(){
        return this.couturier;
    }
}
