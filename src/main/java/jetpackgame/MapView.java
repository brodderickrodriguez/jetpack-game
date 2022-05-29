package main.java.jetpackgame;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

class MapViewSprite extends JLabel {
    MapViewSprite(Sprite fromSprite, double deltaSize, double offsetX, double offsetY) {
        super();
        this.setBackground(fromSprite.getMainColor());
        this.setOpaque(true);

        int x = (int) ((fromSprite.getX() + offsetX) * deltaSize);
        int y = (int) ((fromSprite.getY() + offsetY) * deltaSize);
        int width = Math.max(2, (int) (deltaSize * fromSprite.getWidth()));
        int height = Math.max(2, (int) (deltaSize * fromSprite.getHeight()));

        this.setBounds(new Rectangle(x, y, width, height));
    }
}

public class MapView extends JLabel {
    private final int height;

    public double getDeltaSize() {
        return ((double)this.height / Const.WORLD_HEIGHT);
    }

    MapView(int height) {
        super();
        this.height = height;
        int width = (int)(((double)Const.WORLD_WIDTH / (double)Const.WORLD_HEIGHT) * height);
        this.setBounds(new Rectangle(5, 5, width, height));
        this.setBackground(new Color(255, 255, 255, 255));
        this.setOpaque(true);
    }

    public void update() {
        this.removeAll();
        HashMap<String, Double> minMax = SpriteManager.getMinMaxSpriteLocations();

        for (Sprite sprite: SpriteManager.getSprites()) {
            if (sprite instanceof WorldBorder) {
                continue;
            } else if (sprite.getWidth() <= 10 || sprite.getHeight() <= 10) {
                continue;
            }

            double xOffset = -minMax.get("minX") - Const.BORDER_DIM;
            double yOffset = -minMax.get("minY") - Const.BORDER_DIM;

            MapViewSprite miniSprite = new MapViewSprite(sprite, this.getDeltaSize(), xOffset, yOffset);
            this.add(miniSprite, 0);
        }



    }
}
