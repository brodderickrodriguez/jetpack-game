public class Util {

    public static double clamp(double x, double min, double max) {
        x = Math.min(x, max);
        x = Math.max(x, min);
        return x;
    }
}
