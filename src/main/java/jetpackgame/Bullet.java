package main.java.jetpackgame;

import java.awt.*;


public class Bullet extends Sprite {
    private final Actor originator;
    private int power = Const.BULLET_POWER;

    Bullet(int x, int y, Actor originator) {
        super(new Rectangle(x, y, 10, 3), originator.getMainColor());
        this.setBackground(originator.getMainColor());
        this.originator = originator;

        int direction = this.originator.getDirection();
        double newVel = (direction * Const.MAX_BULLET_VEL) + this.originator.getVel()[0];
        this.setVelX(newVel);
    }

    Actor getOriginator() {
        return this.originator;
    }

    int getPower() {
        return this.power;
    }

    @Override
    public void intersectedWith(Sprite other) {
        if (other != this.originator && !(other instanceof Bullet)) {
            this.power = 0;
            SpriteManager.removeSprite(this);
        }
    }

}
