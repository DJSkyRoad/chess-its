package main.math;

public class ChessPos {
    public int x;
    public int y;

    public ChessPos(int x, int y) {
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

    public boolean compare(ChessPos vec) {
        return this.x == vec.x && this.y == vec.y;
    }

    @Override
    public String toString() {
        return "ChessPos{x=" + x + ", y=" + y + '}';
    }
}
