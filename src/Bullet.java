import java.awt.*;


public class Bullet extends Sprite {
    final int direction;
    final int initialVel;
    int power = 3;
    static int prevDirection = 1;

    Bullet(int x, int y, int initialVel, int direction) {
        super(new Rectangle(x, y, 10, 2));
        this.setBackground(Color.green);
        this.initialVel = initialVel;
        this.direction = direction;
        Bullet.prevDirection = this.direction;

        System.out.println(initialVel);

        this.vel[0] = (this.direction * Const.MAX_BULLET_VEL) + this.initialVel;
    }

    @Override
    public void intersectedWith(Sprite other) {
        if (!(other instanceof Player)) {
            this.power = 0;
            SpriteManager.removeSprite(this);
        }
    }

}
