import java.awt.*;
import java.util.ArrayList;

interface EnemyHiveMind {
    // BOIDS flocking

    static ArrayList<Enemy> getAllEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();

        for (Sprite sprite: SpriteManager.getSprites())
            if (sprite instanceof Enemy)
                enemies.add((Enemy) sprite);

        return enemies;
    }

    // TODO: assign waypoints

}

public class Enemy extends Actor {

    Enemy(int x, int y) {
        super(x, y, Color.orange);
        this.bulletDelay = 800;
        new LifeIndicator(this, Color.orange);
    }


    @Override
    void update() {
        super.update();

        if (life <= 0) {
            SpriteManager.removeSprite(this);
        }

        double playerDistance = this.distanceTo(Game.getCurrentGame().getPlayer());

        if (playerDistance < 350) {
            if (playerDistance > 150)
                this.trackX(Game.getCurrentGame().getPlayer(), 0.5);
            else
                this.direction = this.getDirectionTo(Game.getCurrentGame().getPlayer());

            this.fireBullet(Color.red);
        }



    }
}
