import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


/*
- player rotation
- level design
- enemy design

- refactor border
- refactor actor
- refactor center player
- find way to test game elements
- repaint on init
- fix bullet speed - being clamped
 */

interface KeySetManager extends KeyListener {
    HashSet<Integer> currentKeys = new HashSet<>();

    @Override
    default void keyTyped(KeyEvent event) { }

    @Override
    default void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE)
            Game.getCurrentGame().playPause();
        else
            this.currentKeys.add(event.getKeyCode());
    }

    @Override
    default void keyReleased(KeyEvent event) {
        this.currentKeys.remove(event.getKeyCode());
    }
}

public class Game extends JFrame implements ActionListener, KeySetManager {
    private Timer timer = null;
    private Player player;
    private static Game currentGame;



    Game() {
        Game.currentGame = this;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(false);
        this.setTitle("game time");
        this.setSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);
        this.setLocationRelativeTo(null);
        this.addKeyListener(this);

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
                sprite.moveSprite((int)-this.player.vel[0] * xMove, (int)-this.player.vel[1] * yMove);
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

        if ((playerRect.x > Const.WINDOW_WIDTH * 0.8 && this.player.vel[0] > 0) ||
                playerRect.x < Const.WINDOW_WIDTH * 0.2 && this.player.vel[0] < 0) {
            this.panWorld(true, false);
        }

        else {
            this.player.moveSprite((int)this.player.vel[0], 0);
        }

        if ((playerRect.y > Const.WINDOW_HEIGHT * 0.8 && this.player.vel[1] > 0) ||
                playerRect.y < Const.WINDOW_HEIGHT * 0.2 && this.player.vel[1] < 0) {
            this.panWorld(false, true);
        }
        else {
            this.player.moveSprite(0, (int)this.player.vel[1]);
        }
    }

    void collisionDetection() {
        ArrayList<Sprite> sprites = SpriteManager.getSprites();

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
                case KeyEvent.VK_LEFT -> this.player.vel[0] -= Const.PLAYER_HORIZONTAL_ACC;
                case KeyEvent.VK_RIGHT -> this.player.vel[0] += Const.PLAYER_HORIZONTAL_ACC;
                case KeyEvent.VK_UP ->  this.player.vel[1] -= Const.PLAYER_VERTICAL_ACC;
                case KeyEvent.VK_SPACE -> this.player.fireBullet();
            }
        }

        this.updatePlayerLocation();
        this.collisionDetection();
        SpriteManager.update();
    }

}