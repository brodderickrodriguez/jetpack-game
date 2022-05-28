package main.java.jetpackgame;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;


abstract class ContentPane extends JComponent implements Comparable<ContentPane> {
    ContentPane() {
        this.setBounds(new Rectangle(0, 0, Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT));
        this.setOpaque(true);
    }

    abstract public void init();

    @Override
    public int compareTo(ContentPane o) {
        if (o == this) {
            return 1;
        }
        return 0;
    }

    public void dispose() {
        Controller.getCurrentController().disposeCurrentContentPane();
    }

    public void centerContents() {
        HashMap<String, Double> minMax = SpriteManager.getMinMaxSprites();

        for (Sprite sprite: SpriteManager.getEverySprite()) {
            Rectangle bounds = sprite.getBounds();
            bounds.x -= minMax.get("minX");
            bounds.y -= minMax.get("minY");
            sprite.setBounds(bounds);
        }
    }

}