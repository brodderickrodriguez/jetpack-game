package main.java.jetpackgame.contentpanes;

import main.java.jetpackgame.ContentController;
import main.java.jetpackgame.contentpanes.subcontentpanes.MenuView;

public class SelectLevelContentPane extends MenuContentPane {
    public SelectLevelContentPane() {
        this.addComponents();
    }

    void addComponents() {
        MenuView menu = this.getMenu();
        menu.addLabel("jetpack-game", 48);
        menu.addLabel("Select Level", 12);
        menu.addButton("Main Menu", 24, this::mainMenuButtonAction);
        menu.addSpacer(12);

        for (int i = 1; i <= 10; i++) {
            final int finalI = i;
            menu.addButton("level " + i, 24, () -> {this.setLevelButtonAction(finalI);});
        }
    }

    private void setLevelButtonAction(int level) {
        ContentController.setCurrentContentPaneStatic(new PreviewLevel(Levels.get(level)));
    }

    @Override
    public void escKeyPressed() {
        this.mainMenuButtonAction();
    }

    @Override
    public void enterKeyPressed() {
        this.setLevelButtonAction(1);
    }

    private void mainMenuButtonAction() {
        ContentController.setCurrentContentPaneStatic(new MainMenuContentPane());
    }

}
