package main.java.jetpackgame.contentpanes;

import main.java.jetpackgame.Const;
import main.java.jetpackgame.SpriteManager;
import main.java.jetpackgame.sprites.Sprite;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;


public abstract class ContentPane extends JComponent {
    ContentPane() {
        this.setBounds(new Rectangle(0, 0, Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT));
        this.setOpaque(true);
    }

    abstract public void init();

    abstract public void escKeyPressed();

    abstract public void enterKeyPressed();

    public abstract void dispose();

    public void centerContents() {
        HashMap<String, Double> minMax = SpriteManager.getMinMaxSpriteLocations();

        for (Sprite sprite: SpriteManager.getEverySprite()) {
            Rectangle bounds = sprite.getBounds();
            bounds.x -= minMax.get("minX");
            bounds.y -= minMax.get("minY");
            sprite.setBounds(bounds);
        }
    }

}