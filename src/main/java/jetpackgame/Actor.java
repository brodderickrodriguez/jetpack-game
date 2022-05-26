package main.java.jetpackgame;
import java.awt.*;
import java.util.ArrayList;


public class Actor extends Sprite {
    private int direction = 1;
    int life = Const.MAX_ACTOR_LIFE;
    int bulletDelay = 200;
    long timeOfLastBullet;
    long timeOfLastLifeIncrease;
    Color bodyColor;

    private final ArrayList<ActorExtremity> extremities = new ArrayList<>();


    Actor(int x, int y, Color bodyColor) {
        super(new Rectangle(x, y, 42, 74));
        this.bodyColor = bodyColor;

        this.addExtremity(new LifeIndicator(this, bodyColor));
        this.addExtremity(new ActorBody(this, this.bodyColor));
        this.addExtremity(new LegSeperator(this));

        this.timeOfLastBullet = System.currentTimeMillis();
        this.timeOfLastLifeIncrease = System.currentTimeMillis();
    }

    public void addExtremity(ActorExtremity extremity) {
        this.extremities.add(extremity);
        this.add(extremity, 0);
    }

    @Override
    public void intersectedWith(Sprite other) {
        if (other instanceof Bullet bullet) {
            if (bullet.originator != this) {
                new ParticleCloud(other.getX(), other.getY(), Color.red);
                this.life -= ((Bullet)other).power;
                this.moveSprite(0.2 * bullet.getVel()[0], 0);
            }
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        double maxAbsTheta = 0.15;
        double theta = (this.getVel()[0] / Const.ACC_HORIZONTAL_MAX) * maxAbsTheta;

        int halfWidth = this.getWidth() / 2;
        int halfHeight = this.getHeight() / 2;

        ((Graphics2D) g).rotate(theta, halfWidth, halfHeight);
    }

    @Override
    void update() {
        super.update();

        for (ActorExtremity extremity: this.extremities) {
            extremity.update();
        }

        double[] thisVel = this.getVel();
        this.modifyVelY(Const.GRAVITY);

        if (thisVel[0] > 0) {
            this.direction = 1;
            this.modifyVelX(-Const.PLAYER_HORIZONTAL_ACC_DECAY);
        }
        if (thisVel[0] < 0) {
            this.direction = -1;
            this.modifyVelX(Const.PLAYER_HORIZONTAL_ACC_DECAY);
        }

        this.setVelX(Util.clamp(thisVel[0], -Const.ACC_HORIZONTAL_MAX, Const.ACC_HORIZONTAL_MAX));
        this.setVelY(Util.clamp(thisVel[1], -Const.ACC_VERTICAL_MAX, Const.ACC_VERTICAL_MAX));

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

    public void headInDirection(int direction) {
        double dx = direction * Const.PLAYER_HORIZONTAL_ACC;
        this.modifyVelX(dx * 0.8);

    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return this.direction;
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

