package main.java.jetpackgame.sprites;

import main.java.jetpackgame.*;
import main.java.jetpackgame.contentpanes.Game;
import java.util.List;

interface EnemyHiveMind {
    static List<Enemy> getAllEnemies() {
        return SpriteManager.getSprites()
                .stream()
                .filter(sprite -> sprite instanceof Enemy)
                .map(Enemy.class::cast)
                .toList();
    }

    default List<Enemy> getFlockmates() {
        Enemy thisEnemy = (Enemy) this;

        return EnemyHiveMind.getAllEnemies()
                .stream()
                .filter(other -> thisEnemy.distanceTo(other) < Const.ENEMY_VISION_RADIUS)
                .toList();
    }

    default Enemy getNearestFlockmate(List<Enemy> list) {
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

    default Enemy getBestFlockmate(List<Enemy> list) {
        Enemy bestFlockmate = (Enemy)this;
        long bestFlockmateTime = ((Enemy)this).getTimeOfLastPlayerContact();

        for (Enemy other: list) {
            if (other.getTimeOfLastPlayerContact() < bestFlockmateTime) {
                bestFlockmateTime = other.getTimeOfLastPlayerContact();
                bestFlockmate = other;
            }
        }
        return bestFlockmate;
    }

    default void updatePolicy() {
        boolean couldFireBullet = false;
        Enemy thisEnemy = (Enemy) this;
        Player player = Game.getCurrentGame().getPlayer();

        if ((System.currentTimeMillis() - thisEnemy.getTimeOfLastPlayerContact()) < Const.ENEMY_GIVE_UP_AFTER) {
            if (thisEnemy.distanceTo(player) > Const.ENEMY_MIN_SEPARATION) {
                thisEnemy.headInDirection(thisEnemy.getPlayerDirectionAtTimeOfLastContact());
            } else {
                int newDirection = thisEnemy.getDirectionTo(player);
                thisEnemy.setDirection(newDirection);
            }

            couldFireBullet = true;
        } else {
            List<Enemy> flockmates = this.getFlockmates();

            if (!flockmates.isEmpty()) {
                Enemy bestFlockmate = this.getBestFlockmate(flockmates);

                if ((System.currentTimeMillis() - bestFlockmate.getTimeOfLastPlayerContact()) < Const.ENEMY_GIVE_UP_AFTER) {
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
    private long timeOfLastPlayerContact;
    private int playerDirectionAtTimeOfLastContact;

    public Enemy(int x, int y) {
        super(x, y, Const.ENEMY_COLOR);
        this.setBulletDelay(Const.ENEMY_BULLET_DELAY);
        this.timeOfLastPlayerContact = System.currentTimeMillis();
    }

    public long getTimeOfLastPlayerContact() {
        return this.timeOfLastPlayerContact;
    }

    public int getPlayerDirectionAtTimeOfLastContact() {
        return this.playerDirectionAtTimeOfLastContact;
    }

    @Override
    public void update() {
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
