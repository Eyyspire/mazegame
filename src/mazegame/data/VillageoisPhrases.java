package mazegame.data;

public enum VillageoisPhrases{

    A(new String("The Ariane thread has been split into two different parts, but only one of them may be enough to get out of this labyrinth.")),
    B(new String("First of all, you will need to find a key to succeed in your quest.")),
    C(new String("A monster is hidden in this maze...")),
    D(new String("I heard about a key that will allow you to open up some trapdoors, somewhere in the maze.")),
    E(new String("The monster hidden in the center of this labyrinth is called the minotaur, a mythological monster. It is very powerful, you may need some stuff to defeat it.")),
    F(new String("Last day, I saw the minotaur with a strange thread, the thread was shining...")),
    G(new String("The minotaur now lives under a trapdoor, a special key may open it...")),
    H(new String("A strange guy named Couturier told me he found out a splendid thread...")),
    I(new String("The Couturier guy is very avaricious. He sometimes sells his creations. But they are very expensive.")),
    J(new String("Once, Couturier sold me a stupid dress for 50 golds ! I can't even imagine if he was selling a magic thread to get out of this maze...")),
    K(new String("Like the Minotaur, the Couturier is now hidden under a trapdoor.")),
    L(new String("Once you have opened one of the two trapdoors, the key will break, and you won't be able to open the other one."));
    
    private String hint;

    private VillageoisPhrases(String hint){
        this.hint = hint;
    }

    public String getHint(){
        return hint;
    }

}
