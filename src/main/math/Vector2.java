package main.math;

public class Vector2 {
    public static final Vector2 ZERO = new Vector2(0, 0);

    public int x;
    public int y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean compare(int x, int y) {
        return this.x == x && this.y == y;
    }

    public boolean compare(Vector2 vec) {
        return this.x == vec.x && this.y == vec.y;
    }

    @Override
    public String toString() {
        return "Vector2{x=" + x + ", y=" + y + '}';
    }
}
