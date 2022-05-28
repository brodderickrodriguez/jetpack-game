package main.java.jetpackgame;

import java.awt.*;

class Platform extends Obstacle {
    Platform(int x, int y) {
        super(new Rectangle(x, y, 500, 40), Color.gray);
        this.setBackground(Color.gray);
    }

}
