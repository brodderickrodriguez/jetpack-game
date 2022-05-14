import java.awt.*;


class Player extends Sprite {
    long timeOfLastBullet;

    JetPack jetPack;

    Player(int x, int y) {
        super(new Rectangle(x, y, 25, 50), true, true);
        this.timeOfLastBullet = System.currentTimeMillis();

        this.jetPack = new JetPack(this);

    }

    @Override
    void update() {
        super.update();





    }

    void fireBullet() {
        long currentTime = System.currentTimeMillis();

        if (currentTime > this.timeOfLastBullet + Const.BULLET_DELAY) {
            this.timeOfLastBullet = currentTime;
            Point center = this.center();
            center.x += direction * this.getBounds().width;
            new Bullet(center.x, center.y, (int)this.vel[0], this.direction);
        }


    }


}