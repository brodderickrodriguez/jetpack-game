package main.java.jetpackgame.contentpanes;

public class MainMenuContentPane extends MenuContentPane {
    public MainMenuContentPane() {
        this.addComponents();
    }

    void addComponents() {
        this.getMenu().addLabel("jetpack-game", 48);
    }
}
