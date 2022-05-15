import java.awt.*;

public class Enemy extends Actor {

    Enemy(int x, int y) {
        super(new Rectangle(x, y, 25, 50));
        this.setBackground(Color.orange);
    }



    @Override
    void update() {
        super.update();

        if (life <= 0) {
            SpriteManager.removeSprite(this);
        }

        double playerDistance = this.distanceTo(Game.getCurrentGame().getPlayer());

        if (playerDistance < 250 && playerDistance > 50) {
            this.trackX(Game.getCurrentGame().getPlayer(), 0.5);

            this.fireBullet(Color.red);
        }



    }
}
