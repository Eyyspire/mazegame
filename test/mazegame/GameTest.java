package mazegame;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

import mazegame.Maze;
import mazegame.cells.*;
import mazegame.entities.*;
import mazegame.items.*;
import mazegame.generators.*;

public class GameTest {
    private Game game;
    private Player player;

    @Before
    public void initialize(){
        game = new Game(new Maze(10, 10), new ArrayList<Npc>());
        player = new Player("merlin", 10, 10, game);
    }

    @Test
    public void placeRandomPlayerSetsPlayerOnARandomCellOfTheMaze(){
        game.place_random_player();
        Cell cell = player.getCell();
        assertTrue(cell.getX() < game.getMaze().getWidth());
        assertTrue(cell.getX() >= 0);
        assertTrue(cell.getY() < game.getMaze().getHeight());
        assertTrue(cell.getY() >= 0);
    }

    @Test
    public void MoveToCellCorrectlyMovesThePlayer(){
        game.place_random_player();
        Cell cell = player.getCell();
        game.moveToCell(player, game.getMaze().getCell((cell.getX() + 1)%10, (cell.getX() + 1)%10));
        Cell cell2 = player.getCell();
        assertFalse(cell.equals(cell2));
        assertTrue(player.getCell().equals(cell2));
        assertTrue(cell2.isPlayerOnCell());
        assertFalse(cell.isPlayerOnCell());
    }

    @Test
    public void MoveToCellCorrectlyMovesTheNpc(){
        Villageois villageois = new Villageois(game);
        Trader trader = new Trader("trader", 10, 10, game);
        game.initEntity(villageois, game.getMaze().getCell(5,5));
        game.initEntity(trader, game.getMaze().getCell(3,4));
        Cell cell = villageois.getCell();
        Cell cell2 = trader.getCell();
        assertTrue(cell.getNPCs().contains(villageois));
        assertTrue(cell2.getNPCs().contains(trader));
        game.moveToCell(villageois, game.getMaze().getCell((cell.getX() + 1)%10, (cell.getX() + 1)%10));
        game.moveToCell(trader, game.getMaze().getCell((cell.getX() + 1)%10, (cell.getX() + 1)%10));
        Cell cell3 = villageois.getCell();
        Cell cell4 = trader.getCell();
        assertFalse(cell.getNPCs().contains(villageois));
        assertFalse(cell2.getNPCs().contains(trader));
        assertTrue(cell3.getNPCs().contains(villageois));
        assertTrue(cell4.getNPCs().contains(trader));
        assertTrue(villageois.getCell() == cell3);
        assertTrue(trader.getCell() == cell4);
    }

    @Test
    public void endTurnMovementAndMoveToMovesEntitiesOneCellAwayOrLess(){
        Villageois villageois = new Villageois(game);
        Trader trader = new Trader("trader", 10, 10, game);
        game.initEntity(villageois, game.getMaze().getCell(5,5));
        game.initEntity(trader, game.getMaze().getCell(3,4));
        int x = 5;
        int y = 5;
        int x2 = 3;
        int y2 = 4;
        game.endTurnMovement(villageois);
        game.endTurnMovement(trader);
        Cell cell = villageois.getCell();
        Cell cell2 = trader.getCell();
        assertTrue(Math.abs((x+y) - (cell.getX() + cell.getY())) <= 1);
        assertTrue(Math.abs((x2+y2) - (cell2.getX() + cell2.getY())) == 0);
    }

   @Test
   public void allowedMovementsPreventsForbiddenMovements(){
       game.place_random_player();
       game.moveToCell(player, game.getMaze().getCell(0,0));
       assertFalse(game.isMovementAllowed(player, Direction.WEST));
       assertFalse(game.isMovementAllowed(player, Direction.NORTH));
       if(player.getCell().getWall(Direction.SOUTH) == Wall.ABSENT){
        assertTrue(game.isMovementAllowed(player, Direction.SOUTH));
       } else{
        assertFalse(game.isMovementAllowed(player, Direction.SOUTH));
       } 
       if(player.getCell().getWall(Direction.EAST) == Wall.ABSENT){
        assertTrue(game.isMovementAllowed(player, Direction.EAST));
       } else{
        assertFalse(game.isMovementAllowed(player, Direction.EAST));
       } 
   } 

    @Test
    public void NpcsAreAddedToTheGameNpcList(){
        ArrayList<Npc> list = new ArrayList<Npc>();
        int quantity = 2;
        Npc npc = new Villageois(game);
        game.initNpc(npc, quantity, list);
        assertTrue(list.size() == 2);
        assertTrue(list.get(0) instanceof Villageois);
        assertTrue(list.get(1) instanceof Villageois);
        Npc npc2 = new Trader("trader", 10, 10, game);
        game.initNpc(npc2, quantity, list);
        assertTrue(list.size() == 4);
        assertTrue(list.get(2) instanceof Trader);
        assertTrue(list.get(3) instanceof Trader);
    }

    @Test 
    public void EntityIsWellInitInTheMaze(){
        Villageois villageois = new Villageois(game);
        game.initEntity(villageois, game.getMaze().getCell(5,5));
        assertTrue(this.game.getMaze().getCell(5,5).getNPCs().get(0) instanceof Villageois);
        assertTrue(villageois.getCell() == this.game.getMaze().getCell(5,5));
    }

    @Test 
    public void initItemsCreatesTheCorrectNumberOfInstances(){
        game.initItem(new Gold(), 100, 10, 15);
        boolean check = true;
        int res = 0;
        for(int x = 0; x<game.getMaze().getWidth(); x++){
            for(int y= 0; y<game.getMaze().getWidth(); y++){
                if (game.getMaze().getCell(x,y).getItems().keySet().contains(new Gold())){
                    if(game.getMaze().getCell(x,y).getItems().get(new Gold()) > 15){
                        check = false;
                    }
                    res += game.getMaze().getCell(x,y).getItems().get(new Gold());
                }
            }   
        }
        assertTrue(res == 100);
        assertTrue(check);
    }

    @Test
    public void InitBackPacksGivesCorrectItems(){
        game.initPlayerBackpack();
        assertTrue(player.getItems().get(new Gold()) == 10);
        assertTrue(player.getItems().get(new Wood())==2);
        assertTrue(player.getItems().get(new Iron())==1);
        assertTrue(player.getItems().get(new HintScroll(this.player))==1);
        assertTrue(player.getItems().get(new Apple())==1);
    }

    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(GameTest.class);
    }
}
