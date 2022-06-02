package main.java.jetpackgame;

import main.java.jetpackgame.contentpanes.*;

/*
make content controller static
make sure sprite manager is static
resolve issues with reference to current game
add dispose call to dealloc timers
make timer an attr of content pane
 */

public class Main {
    public static void main(String[] args) throws Exception {
        ContentController window = new ContentController();

        window.setCurrentContentPane(new MainMenuContentPane());

//
//        window.queueContentPane(new MainMenuContentPane());
//
////        window.queueContentPane(new MenuContentPane());
//        window.queueContentPane(new PreviewLevel(Levels.LEVEL_1));
//        window.queueContentPane(new Game(Levels.LEVEL_1));
////        window.queueContentPane(new LevelDesigner());
//        window.startNextContentPane();
    }
}
