package main.java.jetpackgame.contentpanes.subcontentpanes;

import main.java.jetpackgame.ContentController;
import main.java.jetpackgame.contentpanes.Game;
import main.java.jetpackgame.contentpanes.MainMenuContentPane;

public class PausedGameView extends MenuView {
    public PausedGameView() {
        super();
        this.addLabel("paused", 36);
        this.addLabel("jetpack-game", 12);
        this.addButton("resume", 24, Game.getCurrentGame()::playPause);
        this.addButton("main menu", 24, this::mainMenuButtonAction);

        MapView mapView = new MapView(380);
        mapView.update();
        this.add(mapView);
    }

    private void mainMenuButtonAction() {
        ContentController.getCurrentController().queueContentPane(new MainMenuContentPane());
        ContentController.getCurrentController().disposeCurrentContentPane();
    }
}
