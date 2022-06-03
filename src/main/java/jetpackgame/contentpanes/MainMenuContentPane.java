package main.java.jetpackgame.contentpanes;

import main.java.jetpackgame.ContentController;
import main.java.jetpackgame.contentpanes.subcontentpanes.MenuView;

public class MainMenuContentPane extends MenuContentPane {
    public MainMenuContentPane() {
        this.addComponents();
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

    @Override
    public void escKeyPressed() {

    }

    @Override
    public void enterKeyPressed() {
        this.playButtonAction();
    }

    void playButtonAction() {
        ContentController.setCurrentContentPaneStatic(new PreviewLevel(Levels.LEVEL_1));
    }

    void selectLevelButtonAction() {
        ContentController.setCurrentContentPaneStatic(new SelectLevelContentPane());
    }

    void buildLevelButtonAction() {
        ContentController.setCurrentContentPaneStatic(new LevelDesigner());
    }

    void howToButtonAction() {
        ContentController.setCurrentContentPaneStatic(new HowToContentController());
    }

    void creditsButtonAction() {
        ContentController.setCurrentContentPaneStatic(new CreditsContentPane());
    }

    void quitButtonAction() {
        System.exit(0);
    }
}
