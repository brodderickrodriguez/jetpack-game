package main.java.jetpackgame;

import java.awt.*;


public class Bullet extends Sprite {
    final Actor originator;
    int power = 3;
    static int prevDirection = 1;

    Bullet(int x, int y, Actor originator, Color color) {
        super(new Rectangle(x, y, 10, 3));
        this.setBackground(color);
        this.originator = originator;

        int direction = this.originator.getDirection();
        double newVel = (direction * Const.MAX_BULLET_VEL) + this.originator.getVel()[0];
        this.setVelX(newVel);

        Bullet.prevDirection = direction;
    }

    @Override
    public void intersectedWith(Sprite other) {
        if (other != this.originator && !(other instanceof Bullet)) {
            this.power = 0;
            SpriteManager.removeSprite(this);
        }
    }

}
