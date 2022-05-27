package main.java.jetpackgame;

import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;


/*
- implement level design engine
- level design
 */


public class Game extends Window implements ActionListener {
    private Player player;
    private static Game currentGame;

    Game() {
        super();
    }

    @Override
    void init() {
        Game.currentGame = this;
        SpriteManager.setCurrentWindow(this);
        this.setGame(Levels.LEVEL_0);
    }

    public static Game getCurrentGame() {
        return Game.currentGame;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setGame(Levels level) {
        if (this.isRunning())
            this.playPause();

        SpriteManager.removeAllSprites();
        WorldBorder.makeBorders();
        this.player = GameLevelFactory.buildLevel(level);
        this.centerPlayer();
        this.playPause();
    }

    public Boolean isRunning() {
        if (Objects.isNull(this.timer))
            return false;

        return this.timer.isRunning();
    }

    void playPause() {
        if (this.isRunning()) {
            this.timer.stop();
        } else {
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
        for (int keyCode: this.currentKeys) {
            switch (keyCode) {
                case KeyEvent.VK_LEFT -> this.player.moveLeft();
                case KeyEvent.VK_RIGHT -> this.player.moveRight();
                case KeyEvent.VK_UP ->  this.player.goUp();
                case KeyEvent.VK_SPACE -> this.player.fireBullet();
            }
        }

        this.updatePlayerLocation();
        this.collisionDetection();
        SpriteManager.update();
    }

}
