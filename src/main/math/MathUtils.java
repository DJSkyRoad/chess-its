package main.math;

public class MathUtils {
    public static int getSign(int value) {
        return value == 0 ? 0 : value > 0 ? 1 : -1;
    }

    public static boolean inRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    public static float clamp(float value, float min, float max) {
        return value < min ? min : value > max ? max : value;
    }

    public static int clamp(int value, int min, int max) {
        return value < min ? min : value > max ? max : value;
    }
}
