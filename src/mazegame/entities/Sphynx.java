package mazegame.entities;

import java.util.*;
import mazegame.*;
import mazegame.items.*;
import mazegame.data.SphynxRiddles;

public class Sphynx extends Npc{

    public static int RIDDLE_INDEX = 0;
    SphynxRiddles riddle;

    public Sphynx(int healthPoints, int strength, Game game) {
        super("Sphynx", healthPoints, strength, game, 0);
        this.addItem(new HintScroll(), 2);
        this.description = "A big cat, looks like a wise sphynx";
        this.riddle = null;
    }

    /**
     * interacts with the sphynx
     * the player can either :
     * - asks for a hint : the sphynx will ask the player a question and will give them a hint if the player answers correctly
     * - starts a fight sequence
     * - leave
     */
    public void interact(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Answer this question to get a hint");

        if (this.riddle == null) {
            this.riddle = SphynxRiddles.values()[RIDDLE_INDEX];
            RIDDLE_INDEX = (RIDDLE_INDEX + 1) % SphynxRiddles.values().length;
        }

        System.out.println(this.riddle.getQuestion());
        String playerAnswer = scan.nextLine();

        if (playerAnswer.equals(this.riddle.getAnswer())) {
            if (this.backPack.containsKey(new HintScroll())) {
                System.out.println("Well done ! Here are your hint scrolls.");
                this.game.getPlayer().addItem(new HintScroll(), 2);
                System.out.println("The big naked cat gave you 2 hint scrolls.");
                this.removeItem(new HintScroll(), 2);
            } else {
                System.out.println("Yeah, you already told me that last time.");
            }
        } else {
            System.out.println("Sorry, wrong answer. Better luck next time !");
        }
    }
}