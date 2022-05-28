package main.java.jetpackgame;
import java.awt.*;
import java.util.ArrayList;


public class Actor extends Sprite {
    private int direction = 1;
    private int life = Const.MAX_ACTOR_LIFE;
    private int bulletDelay;
    private long timeOfLastBullet;
    private long timeOfLastLifeIncrease;
    private final ArrayList<ActorExtremity> extremities = new ArrayList<>();

    Actor(int x, int y, Color mainColor) {
        super(new Rectangle(x, y, 42, 74), mainColor);

        this.addExtremity(new LifeIndicator(this, this.getMainColor()));
        this.addExtremity(new ActorBody(this, this.getMainColor()));
        this.addExtremity(new LegSeperator(this));

        this.timeOfLastBullet = System.currentTimeMillis();
        this.timeOfLastLifeIncrease = System.currentTimeMillis();
    }

    public int getLife() {
        return this.life;
    }

    public int getBulletDelay() {
        return this.bulletDelay;
    }

    public void setBulletDelay(int bulletDelay) {
        this.bulletDelay = bulletDelay;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return this.direction;
    }

    public void addExtremity(ActorExtremity extremity) {
        this.extremities.add(extremity);
        this.add(extremity, 0);
    }

    public void headInDirection(int direction) {
        double dx = direction * Const.PLAYER_HORIZONTAL_ACC;
        this.modifyVelX(dx);

    }

    void fireBullet(Color color) {
        long currentTime = System.currentTimeMillis();

        if (currentTime > this.timeOfLastBullet + this.getBulletDelay()) {
            this.timeOfLastBullet = currentTime;
            Point center = this.getCenter();
            center.x += direction * this.getBounds().width;
            new Bullet(center.x, center.y, this);
        }
    }

    void poofAnimation() {
        Rectangle bounds = this.getBounds();
        int deltaY = bounds.height / 3;
        int x = bounds.x + (bounds.width / 2);

        for (int i = bounds.y; i < bounds.y + bounds.height; i += deltaY) {
            new ParticleCloud(x, i, this.getMainColor());
        }
    }

    @Override
    public void intersectedWith(Sprite other) {
        if (other instanceof Bullet bullet) {
            if (bullet.getOriginator() != this) {
                new ParticleCloud(other.getX(), other.getY(), Color.red);
                this.life -= ((Bullet)other).getPower();
                this.moveSprite(bullet.getVel()[0] * Const.ACTOR_BULLET_RECOIL, 0);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        double maxAbsTheta = Const.ACTOR_MAX_ABS_ROTATION;
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
            this.poofAnimation();
            SpriteManager.removeSprite(this);
        }
    }
}

