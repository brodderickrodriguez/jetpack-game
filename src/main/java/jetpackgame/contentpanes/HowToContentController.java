package main.java.jetpackgame.contentpanes;

import main.java.jetpackgame.ContentController;
import main.java.jetpackgame.contentpanes.subcontentpanes.MenuView;

import javax.swing.*;
import java.awt.*;

public class HowToContentController extends MenuContentPane {
    public HowToContentController() {
        this.addComponents();
    }

    void addComponents() {
        MenuView menu = this.getMenu();
        menu.addLabel("jetpack-game", 48);
        menu.addLabel("How To", 12);

        menu.addButton("Main Menu", 24, this::mainMenuButtonAction);

        menu.addSpriteListItem(12);
    }

    @Override
    public void escKeyPressed() {
        this.mainMenuButtonAction();
    }
    private void mainMenuButtonAction() {
        ContentController.setCurrentContentPaneStatic(new MainMenuContentPane());
    }

}
