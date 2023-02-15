package mazegame.items;

public class Apple extends HealingItem {

    int healing;
    
    /* Constructor with undefined owner */
    public Apple(){
        super("apple");
        this.healing = 25;
    }

    /* heal the user */
    public void use(){
        this.getEntity().changeHealthPoints(this.healing);
        super.use();
    }

    public int getHealing(){
        return this.healing;
    }
}


