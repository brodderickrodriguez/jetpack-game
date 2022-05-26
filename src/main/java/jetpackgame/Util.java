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
