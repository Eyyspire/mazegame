package mazegame.data;

public enum Colors {
    RED("\u001B[31m"),
    CYAN("\u001B[36m"),
    YELLOW("\u001B[33m"),
    BRIGHTYELLOW("\u001B[93m"),
    GREEN("\u001B[32m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[38:5:130m"),
    WHITE("\u001B[37m"),
    BLACK("\u001B[30m"),
    BRIGHTBLACK("\u001B[38:5:246m"),
    RESET("\u001B[0m");

    private String color;

    private Colors(String color){
        this.color = color;
    }

    public String getColor(){
        return color;
    }

    public static String colorize(String toColorize, Colors color){
        return color.getColor() + toColorize + Colors.RESET.getColor();
    }

}
