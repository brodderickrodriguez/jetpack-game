package main.java.jetpackgame.contentpanes;

import main.java.jetpackgame.Const;
import main.java.jetpackgame.ContentController;
import main.java.jetpackgame.SpriteManager;
import main.java.jetpackgame.sprites.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PreviewLevel extends ContentPane implements ActionListener {
    private Timer timer;
    private Sprite lowestSprite;
    private final Levels level;

    public PreviewLevel(Levels level) {
        this.level = level;
    }

    @Override
    public void init() {
        WorldBorder.makeBorders();
        GameLevelFactory.buildLevel(level);
        SpriteManager.update();

        double zoomDelta = this.getZoomOutDelta();
        this.zoomOut(zoomDelta);
        this.centerContents();
        this.repaint();

        this.lowestSprite = this.getLowestSprite();
        this.timer = new Timer(Const.KEY_DELAY, this);
        this.timer.start();
    }

    @Override
    public void escKeyPressed() {
        ContentController.setCurrentContentPaneStatic(new MainMenuContentPane());
    }

    @Override
    public void enterKeyPressed() {
        this.reachedBottom();
    }

    @Override
    public void dispose() {
        this.timer.stop();
    }

    private void reachedBottom() {
        this.dispose();
        ContentController.setCurrentContentPaneStatic(new Game(Levels.LEVEL_1));
    }

    double getZoomOutDelta() {
        double denom = (double)Const.WORLD_WIDTH + (2 * Const.BORDER_DIM);
        return (double)Const.WINDOW_WIDTH / denom;
    }

    void updateComponentSize(Component c, double delta) {
        Rectangle bounds = c.getBounds();
        bounds.x *= delta;
        bounds.y *= delta;
        bounds.width *= delta;
        bounds.height *= delta;
        c.setBounds(bounds);
    }

    void zoomOut(double delta) {
        for (Sprite sprite: SpriteManager.getEverySprite()) {
            this.updateComponentSize(sprite, delta);

            for (Component c: sprite.getComponents()) {
                this.updateComponentSize(c, delta);
            }
        }
    }

    Sprite getLowestSprite() {
        Sprite lowestSprite = null;

        for (Sprite sprite: SpriteManager.getEverySprite()) {
            if (lowestSprite == null) {
                lowestSprite = sprite;
            } else {
                if (sprite.getBottomMostPoint() > lowestSprite.getBottomMostPoint()) {
                    lowestSprite = sprite;
                }
            }

        }
        return lowestSprite;
    }

    void pan() {
        for (Sprite sprite: SpriteManager.getEverySprite()) {
            Rectangle bounds = sprite.getBounds();
            bounds.y -= 2;
            sprite.setBounds(bounds);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.lowestSprite.getBottomMostPoint() > Const.WINDOW_HEIGHT) {
            pan();
        } else {
            this.reachedBottom();
        }
    }
}
