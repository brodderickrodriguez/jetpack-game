package main.java.jetpackgame;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class SpriteManager {
    private static final ArrayList<Sprite> allSprites = new ArrayList<>();
    private static final PriorityQueue<Sprite> spriteAddQueue = new PriorityQueue<>();
    private static final PriorityQueue<Sprite> spriteRemoveQueue = new PriorityQueue<>();

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