package main.java.jetpackgame.sprites;

import java.awt.*;

public class Platform extends Obstacle {
    public Platform(int x, int y) {
        super(new Rectangle(x, y, 500, 40), Color.gray);
        this.setBackground(Color.gray);
    }

}
