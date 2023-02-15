package mazegame.entities;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Before.*;

import java.util.HashMap;

import mazegame.entities.*;
import mazegame.items.*;
import mazegame.Game;

public class SphynxTest{
    private Game game;
    private Sphynx sphynx;

    @Before
    public void initalize(){
        game = new Game();
        sphynx = new Sphynx(10, 10, game);
    }

    @Test 
    public void lol(){
        sphynx.interact();
    }

    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(SphynxTest.class);
    }
}
