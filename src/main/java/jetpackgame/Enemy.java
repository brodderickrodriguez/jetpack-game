package main.java.jetpackgame;

import java.util.ArrayList;


interface EnemyHiveMind {
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
            if (((Enemy)this).distanceTo(other) < Const.ENEMY_VISION_RADIUS) {
                flockmates.add(other);
            }
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

        for (Enemy other: list) {
            if (other.timeOfLastPlayerContact < bestFlockmateTime) {
                bestFlockmateTime = other.timeOfLastPlayerContact;
                bestFlockmate = other;
            }
        }
        return bestFlockmate;
    }

    default void updatePolicy() {
        boolean couldFireBullet = false;
        Enemy thisEnemy = (Enemy) this;
        Player player = Game.getCurrentGame().getPlayer();

        if ((System.currentTimeMillis() - thisEnemy.timeOfLastPlayerContact) < Const.ENEMY_GIVE_UP_AFTER) {
            if (thisEnemy.distanceTo(player) > Const.ENEMY_MIN_SEPARATION) {
                thisEnemy.headInDirection(thisEnemy.playerDirectionAtTimeOfLastContact);
            } else {
                int newDirection = thisEnemy.getDirectionTo(player);
                thisEnemy.setDirection(newDirection);
            }

            couldFireBullet = true;
        } else {
            ArrayList<Enemy> flockmates = this.getFlockmates();

            if (!flockmates.isEmpty()) {
                Enemy bestFlockmate = this.getBestFlockmate(flockmates);

                if ((System.currentTimeMillis() - bestFlockmate.timeOfLastPlayerContact) < Const.ENEMY_GIVE_UP_AFTER) {
                    int newDirection = thisEnemy.getDirectionTo(bestFlockmate);
                    thisEnemy.headInDirection(newDirection);
                    couldFireBullet = true;
                }
            }
        }

        Enemy nearestFlockmate = this.getNearestFlockmate();

        if (nearestFlockmate != null) {
            if (thisEnemy.distanceTo(nearestFlockmate) < Const.ENEMY_MIN_SEPARATION) {
                int newDirection = -thisEnemy.getDirectionTo(nearestFlockmate);
                thisEnemy.headInDirection(newDirection);
            }
        }

        if (couldFireBullet) {
            thisEnemy.fireBullet(Const.ENEMY_COLOR);
        }
    }
}

public class Enemy extends Actor implements EnemyHiveMind {
    long timeOfLastPlayerContact;
    int playerDirectionAtTimeOfLastContact;

    Enemy(int x, int y) {
        super(x, y, Const.ENEMY_COLOR);
        this.setBulletDelay(Const.ENEMY_BULLET_DELAY);
        this.timeOfLastPlayerContact = System.currentTimeMillis();
    }

    @Override
    void update() {
        super.update();
        this.updatePolicy();

        if (this.getLife() <= 0) {
            SpriteManager.removeSprite(this);
        }

        double playerDistance = this.distanceTo(Game.getCurrentGame().getPlayer());

        if (playerDistance < Const.ENEMY_VISION_RADIUS) {
            this.timeOfLastPlayerContact = System.currentTimeMillis();
             this.playerDirectionAtTimeOfLastContact = this.getDirectionTo(Game.getCurrentGame().getPlayer());
        }

    }
}
