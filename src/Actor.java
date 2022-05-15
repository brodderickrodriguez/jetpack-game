import java.awt.*;

public class Actor extends Sprite {
    int life = 50;
    long timeOfLastBullet;


    Actor(Rectangle bounds) {
        super(bounds);
        this.timeOfLastBullet = System.currentTimeMillis();

    }

    @Override
    public void intersectedWith(Sprite other) {
        if (other instanceof Bullet) {
            Bullet bullet = (Bullet) other;

            if (bullet.originator != this) {
                new ParticleCloud(other.getX(), other.getY(), Color.red);
                this.life -= ((Bullet)other).power;

                this.vel[0] += (int)(0.2 * bullet.vel[0]);

            }
        }
    }

    @Override
    void update() {
        super.update();

        this.vel[1] += Const.GRAVITY;

        if (this.vel[0] > 0) {
            this.vel[0] -= Const.PLAYER_HORIZONTAL_ACC_DECAY;
        }
        if (this.vel[0] < 0) {
            this.vel[0] += Const.PLAYER_HORIZONTAL_ACC_DECAY;
        }

        this.vel[0] = Util.clamp(this.vel[0], -Const.ACC_HORIZONTAL_MAX, Const.ACC_HORIZONTAL_MAX);
        this.vel[1] = Util.clamp(this.vel[1], -Const.ACC_VERTICAL_MAX, Const.ACC_VERTICAL_MAX);

    }

    public void trackX(Sprite other, double speed) {
        double dx = other.getX() - this.getX();
        dx = Util.clamp(dx, -speed, speed);
        this.vel[0] += dx;
    }


    void fireBullet(Color color) {
        long currentTime = System.currentTimeMillis();

        if (currentTime > this.timeOfLastBullet + Const.BULLET_DELAY) {
            this.timeOfLastBullet = currentTime;
            Point center = this.center();
            center.x += direction * this.getBounds().width;
            new Bullet(center.x, center.y, this, color);
        }


    }
}
