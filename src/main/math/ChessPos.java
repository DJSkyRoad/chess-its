package main.math;

import main.Board;

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

    public boolean isValid() {
        return MathUtils.inRange(this.x, 0, Board.scale - 1)
                && MathUtils.inRange(this.y, 0, Board.scale - 1);
    }

    @Override
    public String toString() {
        return "ChessPos{x=" + x + ", y=" + y + '}';
    }
}
