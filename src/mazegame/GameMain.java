package mazegame;


public class GameMain {
    public static void main(String[] args) {
        Game game = new Game();
        if(args.length<1){
            game.play("No args");
        }
        else{
            game.play(args[0]);
        }
        
    }
}
