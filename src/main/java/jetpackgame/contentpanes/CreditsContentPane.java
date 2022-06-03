package main.java.jetpackgame.contentpanes;

import main.java.jetpackgame.ContentController;
import main.java.jetpackgame.contentpanes.subcontentpanes.MenuView;

public class CreditsContentPane extends MenuContentPane {
    public CreditsContentPane() {
        this.addComponents();
    }

    void addComponents() {
        MenuView menu = this.getMenu();
        menu.addLabel("jetpack-game", 48);
        menu.addLabel("Credits", 12);

        menu.addButton("Main Menu", 24, this::mainMenuButtonAction);

        menu.addLabel("", 12);
        menu.addLabel("I created this", 24);
        menu.addLabel("- brodderick", 12);
    }

    @Override
    public void escKeyPressed() {
        this.mainMenuButtonAction();
    }
    private void mainMenuButtonAction() {
        ContentController.setCurrentContentPaneStatic(new MainMenuContentPane());
    }

}
