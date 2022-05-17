import java.awt.*;
import java.util.ArrayList;
import java.util.OptionalDouble;

interface EnemyHiveMind {
    static final int VISION_RADIUS = 350;
    // BOIDS flocking

    static ArrayList<Enemy> getAllEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();

        for (Sprite sprite: SpriteManager.getSprites())
            if (sprite instanceof Enemy)
                enemies.add((Enemy) sprite);

        return enemies;
    }

    default ArrayList<Enemy> getFlockmates() {
        ArrayList<Enemy> flockmates = new ArrayList<>();

        for (Enemy other: EnemyHiveMind.getAllEnemies()) {
            if (((Enemy)this).distanceTo(other) < EnemyHiveMind.VISION_RADIUS)
                flockmates.add(other);
        }

        return flockmates;
    }

    default Enemy getNearestFlockmate(ArrayList<Enemy> list) {
        double nearestDistance = Double.POSITIVE_INFINITY;
        Enemy nearestEnemy = null;

        for (Enemy other: list) {
            if (other == this)
                continue;

            double distance = ((Enemy)this).distanceTo(other);

            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestEnemy = other;
            }
        }

        return nearestEnemy;
    }

    default Enemy getNearestFlockmate() {
        return this.getNearestFlockmate(this.getFlockmates());
    }

    default Enemy getBestFlockmate(ArrayList<Enemy> list) {
        Enemy bestFlockmate = (Enemy)this;
        long bestFlockmateTime = ((Enemy)this).timeOfLastPlayerContact;

        for (Enemy other: this.getFlockmates()) {
            if (other.timeOfLastPlayerContact < bestFlockmateTime) {
                bestFlockmateTime = other.timeOfLastPlayerContact;
                bestFlockmate = other;
            }
        }

        return bestFlockmate;
    }

    default Enemy getBestFlockmate() {
        return this.getBestFlockmate(this.getFlockmates());
    }

    default void updatePolicy() {
        int minSep = 50;
        int giveUpElapse = 1000;
        boolean couldFireBullet = false;
        Enemy thisEnemy = (Enemy) this;

        if ((System.currentTimeMillis() - thisEnemy.timeOfLastPlayerContact) < giveUpElapse) {
            thisEnemy.headInDirection(thisEnemy.playerDirectionAtTimeOfLastContact);
            couldFireBullet = true;
        } else {
            ArrayList<Enemy> flockmates = this.getFlockmates();

            if (flockmates.size() > 0) {
                Enemy bestFlockmate = this.getBestFlockmate(flockmates);

                if ((System.currentTimeMillis() - bestFlockmate.timeOfLastPlayerContact) < giveUpElapse) {
                    int newDirection = thisEnemy.getDirectionTo(bestFlockmate);
                    thisEnemy.headInDirection(newDirection);
                    couldFireBullet = true;
                }
            }
        }

        Enemy nearestFlockmate = this.getNearestFlockmate();

        if (nearestFlockmate != null) {
            if (thisEnemy.distanceTo(nearestFlockmate) < 50) {
                int newDirection = -thisEnemy.getDirectionTo(nearestFlockmate);
                thisEnemy.headInDirection(newDirection);
            }
        }

        if (couldFireBullet) {
            thisEnemy.fireBullet(Color.red);
        }

    }

}

public class Enemy extends Actor implements EnemyHiveMind {
    long timeOfLastPlayerContact;
    int playerDirectionAtTimeOfLastContact;

    Enemy(int x, int y) {
        super(x, y, Color.orange);
        this.bulletDelay = 800;
        this.timeOfLastPlayerContact = System.currentTimeMillis();
    }

    @Override
    void update() {
        super.update();

        if (life <= 0) {
            SpriteManager.removeSprite(this);
        }

        this.updatePolicy();

        double playerDistance = this.distanceTo(Game.getCurrentGame().getPlayer());

        if (playerDistance < 350) {
            this.timeOfLastPlayerContact = System.currentTimeMillis();
            this.playerDirectionAtTimeOfLastContact = this.getDirectionTo(Game.getCurrentGame().getPlayer());
        }


    }
}


/*

    default void align() {
        Enemy bestFlockmate = this.getBestFlockmate();

        if (bestFlockmate == null)
            return;

        ((Enemy)this).headInDirection(bestFlockmate.direction);
    }

    default void separate() {
        Enemy nearestFlockmate = this.getNearestFlockmate();

        if (nearestFlockmate == null)
            return;

        int oppositeDirection = nearestFlockmate.direction == 1 ? -1: 1;
        ((Enemy)this).headInDirection(oppositeDirection);
    }

    default void cohere() {
        ArrayList<Integer> directions = new ArrayList<Integer>();

        for (Enemy enemy: this.getFlockmates()) {
            if (enemy == this)
                continue;

            directions.add(enemy.direction);
        }

        OptionalDouble meanDirection = directions.stream().mapToDouble(a -> a).average();

        if (meanDirection.isEmpty())
            return;

        int newDirection = meanDirection.getAsDouble() >= 0 ? 1: -1;
        ((Enemy)this).headInDirection(newDirection);
    }
 */