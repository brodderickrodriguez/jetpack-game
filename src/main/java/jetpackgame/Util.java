package main.java.jetpackgame;

import java.awt.*;
import java.util.Random;

public class Util {

    public static double clamp(double x, double min, double max) {
        double x2 = Math.min(x, max);
        return Math.max(x2, min);
    }

    public static double randNorm() {
        Random rn = new Random();
        return ((rn.nextDouble() * 2) - 1);
    }

    public static Color randomColor() {
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return new Color(r, g, b);
    }

    public static Color[] flameColors() {
        Color[] colors = {
                new Color(255, 100, 52),
                new Color(238, 115, 52),
                new Color(238, 145, 55),
                new Color(245, 187, 72),
                new Color(249, 222, 100),
        };
        return colors;
    }

    static class WrapAroundGenerator<T> {
        private final T[] items;
        private int current;
        WrapAroundGenerator(T[] items, int offset) {
            this.items = items;
            this.current = offset;
        }
        WrapAroundGenerator(T[] items) {
            this(items, 0);
        }
        T getNext() {
            int idx = this.current++ % this.items.length;
            return this.items[idx];
        }
    }
}