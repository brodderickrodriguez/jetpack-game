package main.java.jetpackgame;



/*
- implement level design engine
- level design
 */


import main.java.jetpackgame.contentpanes.MainMenuContentPane;

public class Main {
    public static void main(String[] args) throws Exception {
        ContentController window = new ContentController();
        window.queueContentPane(new MainMenuContentPane());

//        window.queueContentPane(new MenuContentPane());
//        window.queueContentPane(new PreviewLevel(Levels.LEVEL_1));
//        window.queueContentPane(new Game(Levels.LEVEL_1));
//        window.queueContentPane(new LevelDesigner());
        window.startNextContentPane();
    }
}
