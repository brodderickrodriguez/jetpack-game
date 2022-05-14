import java.awt.*;

public class Enemy extends Actor {
    int life = 100;

    Enemy(int x, int y) {
        super(new Rectangle(x, y, 25, 50));
        this.setBackground(Color.orange);
    }

    @Override
    public void intersectedWith(Sprite other) {
        if (other instanceof Bullet) {
            new ParticleCloud(other.getX(), other.getY(), Color.red);
            this.life -= ((Bullet)other).power;
        }
    }

    @Override
    void update() {
        super.update();

        if (life <= 0) {
            SpriteManager.removeSprite(this);
        }

        double playerDistance = this.distanceTo(Game.getCurrentGame().getPlayer());

        if (playerDistance < 150 && playerDistance > 25) {
            this.track(Game.getCurrentGame().getPlayer(), 0.5);
        }



    }
}
