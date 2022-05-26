package main.java.jetpackgame;

public class Util {

    public static double clamp(double x, double min, double max) {
        double x2 = Math.min(x, max);
        return Math.max(x2, min);
    }
}
