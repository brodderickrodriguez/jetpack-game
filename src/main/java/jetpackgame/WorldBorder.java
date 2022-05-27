package main.java.jetpackgame;

import java.awt.*;


public class WorldBorder extends Obstacle {
    WorldBorder(Rectangle bounds) {
        super(bounds);
        this.setBackground(Color.black);
    }

    public static void makeBorders() {
        int left = Const.MAX_LEFT - Const.BORDER_DIM;
        int top = Const.MAX_TOP - Const.BORDER_DIM;
        int height = Const.WORLD_HEIGHT + (2 * Const.BORDER_DIM);
        int width = Const.WORLD_WIDTH + (2 * Const.BORDER_DIM);

        // left
        new WorldBorder(new Rectangle(left, top, Const.BORDER_DIM, height));

        // right
        new WorldBorder(new Rectangle(Const.MAX_RIGHT, top, Const.BORDER_DIM, height));

        // top
        new WorldBorder(new Rectangle(left, top, width, Const.BORDER_DIM));

        // bottom
        new WorldBorder(new Rectangle(left, Const.MAX_BOTTOM, width, Const.BORDER_DIM));
    }

}

