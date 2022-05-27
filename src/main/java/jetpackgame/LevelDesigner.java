package main.java.jetpackgame;

import java.awt.*;


public class LevelDesigner extends ContentPane  {
    @Override
    public void init() {
        WorldBorder.makeBorders();
        SpriteManager.update();

        double zoomDelta = this.getZoomOutDelta();
        this.zoomOut(zoomDelta);
        this.centerContents();
        this.repaint();
    }

    double getZoomOutDelta() {
        double denom = (double)Const.WORLD_HEIGHT + (2 * Const.BORDER_DIM);
        return (double)Const.WINDOW_HEIGHT / denom;
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

}
