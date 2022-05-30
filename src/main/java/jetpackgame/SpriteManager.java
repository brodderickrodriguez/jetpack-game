package main.java.jetpackgame;

import main.java.jetpackgame.sprites.Sprite;
import java.util.*;


public class SpriteManager {
    private static ContentPane currentContentPane = null;
    private static final ArrayList<Sprite> allSprites = new ArrayList<>();
    private static final PriorityQueue<Sprite> spriteAddQueue = new PriorityQueue<>();
    private static final PriorityQueue<Sprite> spriteRemoveQueue = new PriorityQueue<>();

    public static void setCurrentContentPane(ContentPane contentPane) {
        SpriteManager.currentContentPane = contentPane;
    }

    public static ContentPane getCurrentContentPane() {
        return SpriteManager.currentContentPane;
    }

    public static List<Sprite> getSprites() {
        return SpriteManager.allSprites;
    }

    static List<Sprite> getEverySprite() {
        ArrayList<Sprite> all = new ArrayList<>();
        all.addAll(SpriteManager.allSprites);
        all.addAll(SpriteManager.spriteAddQueue);
        all.addAll(SpriteManager.spriteRemoveQueue);
        return all;
    }

    public static void addSprite(Sprite sprite) {
        SpriteManager.spriteAddQueue.add(sprite);
    }

    public static void removeSprite(Sprite sprite) {
        SpriteManager.spriteRemoveQueue.add(sprite);
    }

    static void removeAllSprites() {
        for (Sprite sprite: SpriteManager.getEverySprite()) {
            SpriteManager.removeSprite(sprite);
        }
    }

    static void update() {
        while (SpriteManager.spriteRemoveQueue.size() > 0) {
            Sprite sprite = SpriteManager.spriteRemoveQueue.poll();
            SpriteManager.getCurrentContentPane().remove(sprite);
            SpriteManager.allSprites.remove(sprite);
        }

        for (Sprite sprite: SpriteManager.getSprites()) {
            sprite.update();
        }

        while (SpriteManager.spriteAddQueue.size() > 0) {
            Sprite sprite = SpriteManager.spriteAddQueue.poll();
            SpriteManager.getCurrentContentPane().add(sprite, 0);
            SpriteManager.allSprites.add(sprite);
            sprite.repaint();
        }
    }


    static HashMap<String, Double> getMinMaxSpriteLocations() {
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;

        for (Sprite sprite: SpriteManager.getEverySprite()) {
            minX = Math.min(minX, sprite.getX());
            minY = Math.min(minY, sprite.getY());
            maxX = Math.max(maxX, sprite.getRightMostPoint());
            maxY = Math.max(maxY, sprite.getBottomMostPoint());
        }

        HashMap<String, Double> map = new HashMap<>();
        map.put("minX", minX);
        map.put("minY", minY);
        map.put("maxX", maxX);
        map.put("maxY", maxY);
        return map;
    }
}