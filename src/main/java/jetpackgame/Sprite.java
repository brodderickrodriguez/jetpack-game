package main.java.jetpackgame;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


interface SpriteManager {
    ArrayList<Sprite> allSprites = new ArrayList<>();
    PriorityQueue<Sprite> spriteAddQueue = new PriorityQueue<>();
    PriorityQueue<Sprite> spriteRemoveQueue = new PriorityQueue<>();

    static List<Sprite> getSprites() {
        return SpriteManager.allSprites;
    }

    static List<Sprite> getEverySprite() {
        ArrayList<Sprite> all = new ArrayList<>();
        all.addAll(SpriteManager.allSprites);
        all.addAll(SpriteManager.spriteAddQueue);
        all.addAll(SpriteManager.spriteRemoveQueue);
        return all;
    }

    static void addSprite(Sprite sprite) {
        SpriteManager.spriteAddQueue.add(sprite);
    }

    static void removeSprite(Sprite sprite) {
        SpriteManager.spriteRemoveQueue.add(sprite);
    }

    static void removeAllSprites() {
        for (Sprite sprite: SpriteManager.getEverySprite()) {
            SpriteManager.removeSprite(sprite);
        }
    }

    static void update() {
        for (Sprite sprite : SpriteManager.getSprites()) {
            sprite.update();
        }

        while (SpriteManager.spriteRemoveQueue.size() > 0) {
            Sprite sprite = SpriteManager.spriteRemoveQueue.poll();
            Game.getCurrentGame().remove(sprite);
            SpriteManager.allSprites.remove(sprite);
        }

        while (SpriteManager.spriteAddQueue.size() > 0) {
            Sprite sprite = SpriteManager.spriteAddQueue.poll();
            Game.getCurrentGame().add(sprite, 0);
            SpriteManager.allSprites.add(sprite);
            sprite.repaint();
        }
    }
}

class Sprite extends JLabel implements Comparable<Sprite> {
    private final double[] vel = {0, 0};

    Sprite(Rectangle bounds) {
        this.setBounds(bounds);
        this.setOpaque(true);
        SpriteManager.addSprite(this);
    }

    void update() {
        if (!(this instanceof Player)) {
            double[] vel = this.getVel();
            this.moveSprite((int)vel[0], (int)vel[1]);
        }
    }

    double[] getVel() {
        return this.vel;
    }

    void modifyVelX(double x) {
        this.vel[0] += x;
    }

    void modifyVelY(double y) {
        this.vel[1] += y;
    }

    void setVel(double[] vel) {
        this.vel[0] = vel[0];
        this.vel[1] = vel[1];
    }

    void setVelX(double x) {
        this.vel[0] = x;
    }

    void  setVelY(double y) {
        this.vel[1] = y;
    }

    Point getCenter() {
        Rectangle bounds = this.getBounds();
        return new Point(bounds.x + (bounds.width / 2), bounds.y + (bounds.height / 2));
    }

    public void moveSprite(int x, int y) {
        Rectangle bounds = this.getBounds();
        bounds.x += x;
        bounds.y += y;
        this.setBounds(bounds);
    }

    public void setXY(int x, int y) {
        Rectangle bounds = this.getBounds();
        bounds.x = x;
        bounds.y = y;
        this.setBounds(bounds);
    }

    public void moveSprite(double x, double y) {
        this.moveSprite((int)x, (int)y);
    }


    public double distanceTo(Sprite other) {
        double dx = Math.pow((other.getX() - this.getX()), 2);
        double dy = Math.pow((other.getY() - this.getY()), 2);
        return Math.sqrt(dx + dy);
    }

    public int getDirectionTo(Sprite other) {
        if (other.getX() < this.getX())
            return -1;
        return 1;
    }

    public float angleBetween(Sprite other) {
        Rectangle otherBounds = other.getBounds();
        Rectangle thisBounds = this.getBounds();
        return (float) Math.toDegrees(Math.atan2(otherBounds.y - thisBounds.y, otherBounds.x - thisBounds.x));
    }

    public boolean intersects(Sprite other) {
        Rectangle intersection = this.getBounds().intersection(other.getBounds());
        return intersection.width > 0 && intersection.height > 0;
    }

    public void intersectedWith(Sprite other) { }

    public boolean intersectedAbove(Sprite other) {
        Rectangle otherBounds = other.getBounds();
        return other.getVel()[1] > 0 && this.getBounds().y > otherBounds.y + otherBounds.height - (Const.ACC_VERTICAL_MAX * 2);
    }

    public boolean intersectedBelow(Sprite other) {
        Rectangle thisBounds = this.getBounds();
        return other.getVel()[1] < 0 && other.getBounds().y > thisBounds.y + thisBounds.height - (Const.ACC_VERTICAL_MAX * 2);
    }

    public boolean intersectedLeft(Sprite other) {
        Rectangle otherBounds = other.getBounds();
        return other.getVel()[0] > 0 && this.getBounds().x > otherBounds.x + otherBounds.width - (Const.ACC_HORIZONTAL_MAX * 2);
    }

    public boolean intersectedRight(Sprite other) {
        Rectangle thisBounds = this.getBounds();
        return other.getVel()[0] < 0 && other.getBounds().x > thisBounds.x + thisBounds.width - (Const.ACC_HORIZONTAL_MAX * 2);
    }

    public boolean otherSpriteConfinedWithinThisBounds(Sprite other) {
        boolean leftWithin = other.getX() >= this.getX();
        boolean rightWithin = other.getRightMostPoint() <= this.getRightMostPoint();
        boolean topWithin = other.getY() >= this.getY();
        boolean bottomWithin = other.getBottomMostPoint() <= this.getBottomMostPoint();
        return leftWithin && rightWithin && topWithin && bottomWithin;
    }

    public int getRightMostPoint() {
        return this.getX() + this.getWidth();
    }

    public int getBottomMostPoint() {
        return this.getY() + this.getHeight();
    }


    @Override
    public int compareTo(Sprite o) {
        if (o == this) {
            return 1;
        }
        return 0;
    }

}