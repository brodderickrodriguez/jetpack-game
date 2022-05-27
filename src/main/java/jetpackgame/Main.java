package main.java.jetpackgame;



/*
- implement level design engine
- level design
 */



public class Main {
    public static void main(String[] args) throws Exception {
        Controller window = new Controller();
        window.queueContentPane(new PreviewLevel(Levels.LEVEL_0));
        window.queueContentPane(new Game(Levels.LEVEL_0));
        window.startNextContentPane();

    }
}
