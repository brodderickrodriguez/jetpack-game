package main.java.jetpackgame.contentpanes;

import main.java.jetpackgame.ContentController;
import main.java.jetpackgame.contentpanes.subcontentpanes.MenuView;

public class MainMenuContentPane extends MenuContentPane {
    public MainMenuContentPane() {
        this.addComponents();
        System.out.println("here");
    }

    void addComponents() {
        MenuView menu = this.getMenu();
        menu.addLabel("jetpack-game", 48);
        menu.addLabel("save the princess", 12);
        menu.addButton("play", 24, this::playButtonAction);
        menu.addButton("select level", 24, this::selectLevelButtonAction);
        menu.addButton("build level", 24, this::buildLevelButtonAction);
        menu.addButton("how-to", 24, this::howToButtonAction);
        menu.addButton("credits", 24, this::creditsButtonAction);
        menu.addButton("quit", 24, this::quitButtonAction);
    }


    void playButtonAction() {
        ContentController.getCurrentController().queueContentPane(new PreviewLevel(Levels.LEVEL_0));
        ContentController.getCurrentController().queueContentPane(new Game(Levels.LEVEL_0));
        ContentController.getCurrentController().disposeCurrentContentPane();
    }

    void selectLevelButtonAction() {

    }

    void buildLevelButtonAction() {

    }

    void howToButtonAction() {

    }

    void creditsButtonAction() {

    }

    void quitButtonAction() {
        System.exit(0);
    }
}
