package main.java.jetpackgame;



/*
- implement level design engine
- level design
 */



public class Main {
    public static void main(String[] args) throws Exception {
        Controller window = new Controller();
//        window.queueContentPane(new MenuContentPane());
//        window.queueContentPane(new PreviewLevel(Levels.LEVEL_1));
        window.queueContentPane(new Game(Levels.LEVEL_1));
//        window.queueContentPane(new LevelDesigner());
        window.startNextContentPane();
    }
}
