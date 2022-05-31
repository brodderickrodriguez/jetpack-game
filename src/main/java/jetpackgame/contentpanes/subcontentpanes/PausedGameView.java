package main.java.jetpackgame.contentpanes.subcontentpanes;

import main.java.jetpackgame.contentpanes.Game;

public class PausedGameView extends MenuView {
    @Override
    public void init() {
        this.addLabel("paused", 36);
        this.addLabel("jetpack-game", 12);
        this.addButton("resume", 24, Game.getCurrentGame()::playPause);
        this.addButton("main menu", 24, null);

        MapView mapView = new MapView(380);
        mapView.update();
        this.add(mapView);
        this.repaint();
    }
}
