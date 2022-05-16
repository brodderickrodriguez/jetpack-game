import javax.swing.*;
import java.awt.*;

class BodyPart extends JLabel {
    Actor actor;
    BodyPart(Actor actor) {
        this.actor = actor;
        this.setOpaque(true);
    }

    void update() { }
}

class ActorBody extends BodyPart {
    ActorBody(Actor actor, Color color) {
        super(actor);
        this.setBackground(color);
        this.setBounds(new Rectangle(9, 24, 24, 50));
    }
}

class LegSeperator extends BodyPart {
    LegSeperator(Actor actor) {
        super(actor);
        this.setBounds(new Rectangle(20, 54, 2, 20));
        this.setBackground(Color.white);
    }
}


class LifeIndicator extends BodyPart {
    private final Actor actor;
    private Color color;

    LifeIndicator(Actor actor, Color color) {
        super(actor);
        this.setBounds(new Rectangle(9, 0, 24, 24));
        this.setBackground(null);
        this.color = color;
        this.actor = actor;
    }

    @Override
    void update() {
        this.paintComponent(this.getGraphics());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.actor.life <= 0)
            return;

        double angle = (1 - ((double)this.actor.life / (double)Const.MAX_ACTOR_LIFE)) * 360;

        g.setColor(this.color);
        g.fillArc(0, 0, this.getWidth(), this.getHeight(), 0, 360);

        g.setColor(Color.gray);
        g.fillArc(0, 0, this.getWidth(), this.getHeight(), 90, -(int)angle);
    }
}


public class Actor extends Sprite {
    int direction = 1;
    int life = Const.MAX_ACTOR_LIFE;
    int bulletDelay = 200;
    long timeOfLastBullet;
    long timeOfLastLifeIncrease;
    LifeIndicator lifeIndicator;
    Color bodyColor;

    Actor(int x, int y, Color bodyColor) {
        super(new Rectangle(x, y, 42, 74));
        this.bodyColor = bodyColor;

        this.lifeIndicator = new LifeIndicator(this, bodyColor);
        this.add(lifeIndicator, 0);
        this.add(new ActorBody(this, this.bodyColor), 0);
        this.add(new LegSeperator(this), 0);

        this.timeOfLastBullet = System.currentTimeMillis();
        this.timeOfLastLifeIncrease = System.currentTimeMillis();
    }

    @Override
    public void intersectedWith(Sprite other) {
        if (other instanceof Bullet bullet) {
            if (bullet.originator != this) {
                new ParticleCloud(other.getX(), other.getY(), Color.red);
                this.life -= ((Bullet)other).power;
                this.moveSprite((int)(0.2 * bullet.vel[0]), 0);
            }
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        double maxAbsTheta = 0.15;
        double theta = (this.vel[0] / Const.ACC_HORIZONTAL_MAX) * maxAbsTheta;

        int halfWidth = this.getWidth() / 2;
        int halfHeight = this.getHeight() / 2;

        ((Graphics2D) g).rotate(theta, halfWidth, halfHeight);
    }

    @Override
    void update() {
        super.update();
        this.lifeIndicator.update();

        this.vel[1] += Const.GRAVITY;

        if (this.vel[0] > 0) {
            this.direction = 1;
            this.vel[0] -= Const.PLAYER_HORIZONTAL_ACC_DECAY;
        }
        if (this.vel[0] < 0) {
            this.direction = -1;
            this.vel[0] += Const.PLAYER_HORIZONTAL_ACC_DECAY;
        }

        this.vel[0] = Util.clamp(this.vel[0], -Const.ACC_HORIZONTAL_MAX, Const.ACC_HORIZONTAL_MAX);
        this.vel[1] = Util.clamp(this.vel[1], -Const.ACC_VERTICAL_MAX, Const.ACC_VERTICAL_MAX);

        long currentTime = System.currentTimeMillis();

        if (currentTime > this.timeOfLastLifeIncrease +  Const.LIFE_INCREASE_DELAY) {
            if (this.life < Const.MAX_ACTOR_LIFE) {
                this.life++;
                this.timeOfLastLifeIncrease = currentTime;
            }
        }

        if (this.life < 1) {
            SpriteManager.removeSprite(this);
        }
    }

    public void trackX(Sprite other, double speed) {
        double dx = other.getX() - this.getX();
        dx = Util.clamp(dx, -speed, speed);
        this.vel[0] += dx;
    }

    void fireBullet(Color color) {
        long currentTime = System.currentTimeMillis();

        if (currentTime > this.timeOfLastBullet + this.bulletDelay) {
            this.timeOfLastBullet = currentTime;
            Point center = this.center();
            center.x += direction * this.getBounds().width;
            new Bullet(center.x, center.y, this, color);
        }
    }
}

