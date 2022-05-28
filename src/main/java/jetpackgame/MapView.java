package main.java.jetpackgame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;



class MapViewSprite extends JLabel {
    final Sprite fromSprite;
    private final double deltaSize;

    MapViewSprite(Sprite fromSprite, double deltaSize, double offsetX, double offsetY) {
        super();
        this.fromSprite = fromSprite;
        this.deltaSize = deltaSize;
        this.setBackground(fromSprite.getMainColor());
        this.setOpaque(true);

        int x = (int) ((this.fromSprite.getX() + offsetX) * MapView.DELTA_SIZE);
        int y = (int) ((this.fromSprite.getY() + offsetY) * MapView.DELTA_SIZE);
        int width = Math.max(2, (int) (deltaSize * fromSprite.getWidth()));
        int height = Math.max(2, (int) (deltaSize * fromSprite.getHeight()));

        this.setBounds(new Rectangle(x, y, width, height));
    }
}

public class MapView extends JLabel {
    public static final int HEIGHT = 250;
    public static final double DELTA_SIZE = ((double)MapView.HEIGHT / Const.WORLD_HEIGHT);

    MapView() {
        super();
        int width = (int)(((double)Const.WORLD_WIDTH / (double)Const.WORLD_HEIGHT) * MapView.HEIGHT);
        this.setBounds(new Rectangle(5, 5, width, MapView.HEIGHT));
        this.setBackground(new Color(255, 255, 255, 128));
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

            MapViewSprite miniSprite = new MapViewSprite(sprite, MapView.DELTA_SIZE, xOffset, yOffset);
            this.add(miniSprite, 0);
        }



    }
}
