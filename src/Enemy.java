import java.awt.*;

public class Enemy extends Actor {
    int bulletDecay = 400;

    Enemy(int x, int y) {
        super(new Rectangle(x, y, 25, 50));
        this.setBackground(Color.orange);
        new LifeIndicator(this);
    }



    @Override
    void update() {
        super.update();

        if (life <= 0) {
            SpriteManager.removeSprite(this);
        }

        double playerDistance = this.distanceTo(Game.getCurrentGame().getPlayer());

        if (playerDistance < 350) {
            if (playerDistance > 100)
                this.trackX(Game.getCurrentGame().getPlayer(), 0.5);

            this.fireBullet(Color.red);
        }



    }
}
