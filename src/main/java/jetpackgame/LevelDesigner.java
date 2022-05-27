package main.java.jetpackgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LevelDesigner extends ContentPane implements ActionListener {
    private Timer timer;
    private Sprite lowestSprite;

    LevelDesigner(Levels level) {
        WorldBorder.makeBorders();
        GameLevelFactory.buildLevel(level);

        double zoomDelta = this.getZoomOutDelta();
        this.zoomOut(zoomDelta);
        this.centerWorld();
        this.repaint();
    }

    @Override
    public void init() {
        this.lowestSprite = this.getLowestSprite();
        this.timer = new Timer(Const.KEY_DELAY, this);
        this.timer.start();
    }

    double getZoomOutDelta() {
        double denom = (double)Const.WORLD_WIDTH + (2 * Const.BORDER_DIM);
        return (double)Const.WINDOW_WIDTH / denom;
    }

    void centerWorld() {
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;

        for (Sprite sprite: SpriteManager.getEverySprite()) {
            minX = Math.min(minX, sprite.getX());
            minY = Math.min(minY, sprite.getY());
        }

        for (Sprite sprite: SpriteManager.getEverySprite()) {
            Rectangle bounds = sprite.getBounds();
            bounds.x -= minX;
            bounds.y -= minY;
            sprite.setBounds(bounds);
        }
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
            this.timer.stop();
            this.dispose();
        }
    }
}
