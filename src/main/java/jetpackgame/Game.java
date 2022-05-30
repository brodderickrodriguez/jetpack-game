package main.java.jetpackgame;

import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class Game extends ContentPane implements ActionListener {
    private Timer timer;
    private Player player;
    private static Game currentGame;
    private final Levels level;
    private MapView mapView;
    private MenuView pausedGameView;

    Game(Levels level) {
        super();
        this.level = level;
        this.mapView = new MapView(200);
    }

    private MenuView getPausedGameView() {
        if (this.pausedGameView != null) {
            this.pausedGameView.init();
            return this.pausedGameView;
        }

        this.pausedGameView = new MenuView();

        this.pausedGameView.addLabel("paused", 36);
        this.pausedGameView.addLabel("jetpack-game", 12);

        this.pausedGameView.addButton("resume", 24, this::playPause);
        this.pausedGameView.addButton("main menu", 24, null);


        MapView mapView = new MapView(380);
        mapView.update();
        this.pausedGameView.add(mapView);

        return this.pausedGameView;
    }

    private Void playPause(Void unused) {
        this.playPause();
        return null;
    }

    private void addPauseView() {
        this.pausedGameView = this.getPausedGameView();
        this.add(this.pausedGameView, 0);
        this.pausedGameView.init();
        this.repaint();
    }



    @Override
    public void init() {
        Game.currentGame = this;
        WorldBorder.makeBorders();
        this.player = GameLevelFactory.buildLevel(level);
        this.centerPlayer();
        this.add(this.mapView, 0);
        this.playPause();
    }

    public static Game getCurrentGame() {
        return Game.currentGame;
    }

    public Player getPlayer() {
        return this.player;
    }


    public Boolean isRunning() {
        if (Objects.isNull(this.timer))
            return false;

        return this.timer.isRunning();
    }



    void playPause() {
        if (this.isRunning()) {
            this.timer.stop();
            this.addPauseView();
        } else {
            if (this.pausedGameView != null) {
                this.remove(this.pausedGameView);
                this.pausedGameView = null;
                this.repaint();
            }

            this.timer = new Timer(Const.KEY_DELAY, this);
            this.timer.start();
        }
    }

    void panWorld(Boolean x, Boolean y) {
        int xMove = x ? 1: 0;
        int yMove = y ? 1: 0;
        for (Sprite sprite: SpriteManager.getSprites()) {
            if (!(sprite instanceof Player)) {
                double[] playerVel = this.player.getVel();
                sprite.moveSprite(-playerVel[0] * xMove, -playerVel[1] * yMove);
            }
        }
    }

    void centerPlayer() {
        int destinationX = Const.WINDOW_WIDTH / 2;
        int destinationY = Const.WINDOW_HEIGHT / 2;

        int deltaX = destinationX - this.player.getX();
        int deltaY = destinationY - this.player.getBounds().y;

        for (Sprite sprite: SpriteManager.getEverySprite()) {
            sprite.moveSprite(deltaX, deltaY);
        }

    }

    void updatePlayerLocation() {
        Rectangle playerRect = this.player.getBounds();
        double[] playerVel = this.player.getVel();

        if ((playerRect.x > Const.WINDOW_WIDTH * (1 - Const.PAN_WORLD_FRAC) && playerVel[0] > 0) ||
                playerRect.x < Const.WINDOW_WIDTH * Const.PAN_WORLD_FRAC && playerVel[0] < 0) {
            this.panWorld(true, false);
        }
        else {
            this.player.moveSprite((int)playerVel[0], 0);
        }

        if ((playerRect.y > Const.WINDOW_HEIGHT * (1 - Const.PAN_WORLD_FRAC) && playerVel[1] > 0) ||
                playerRect.y < Const.WINDOW_HEIGHT * Const.PAN_WORLD_FRAC && playerVel[1] < 0) {
            this.panWorld(false, true);
        }
        else {
            this.player.moveSprite(0, (int)playerVel[1]);
        }
    }

    void collisionDetection() {
        List<Sprite> sprites = SpriteManager.getSprites();

        for (int i = 0; i < sprites.size(); i++) {
            for (int j = i + 1; j < sprites.size(); j++) {
                if (sprites.get(i).intersects(sprites.get(j))) {
                    sprites.get(i).intersectedWith(sprites.get(j));
                    sprites.get(j).intersectedWith(sprites.get(i));
                }
            }

            if (sprites.get(i).intersects(this.player)) {
                this.player.intersectedWith(sprites.get(i));
                sprites.get(i).intersectedWith(this.player);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int keyCode: Controller.getCurrentController().getCurrentKeys()) {
            switch (keyCode) {
                case KeyEvent.VK_LEFT -> this.player.moveLeft();
                case KeyEvent.VK_RIGHT -> this.player.moveRight();
                case KeyEvent.VK_UP ->  this.player.goUp();
                case KeyEvent.VK_SPACE -> this.player.fireBullet();
            }
        }

        this.mapView.update();
        this.updatePlayerLocation();
        this.collisionDetection();
        SpriteManager.update();

        if (this.player.getLife() <= 0) {
            this.playPause();
            this.dispose();
        }

    }

}
