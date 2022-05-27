package main.java.jetpackgame;

import java.awt.*;

public class Obstacle extends Sprite {
    Obstacle(Rectangle bounds) {
        super(bounds);
    }


    @Override
    public void intersectedWith(Sprite other) {
        Rectangle intersection = this.getBounds().intersection(other.getBounds());

        if (this.intersectedBelow(other)) {
            other.setVelY(0);
            other.moveSprite(0, intersection.height);
        }
        if (this.intersectedAbove(other)) {
            other.setVelY(0);
            other.moveSprite(0, -intersection.height);
        }
        if (this.intersectedLeft(other)) {
            other.setVelX(0);
            other.moveSprite(-intersection.width, 0);
        }
        if (this.intersectedRight(other)) {
            other.setVelX(0);
            other.moveSprite(intersection.width, 0);
        }
    }

}