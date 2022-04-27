package main.math;

import main.Board;

public class ChessPos {
    public int x;
    public int y;

    public ChessPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean compare(int x, int y) {
        return this.x == x && this.y == y;
    }

    public boolean compare(ChessPos pos) {
        return this.x == pos.x && this.y == pos.y;
    }

    public boolean isValid() {
        return MathUtils.inRange(this.x, 0, Board.scale - 1)
                && MathUtils.inRange(this.y, 0, Board.scale - 1);
    }

    public ChessPos next(ChessPos dest) {
        return new ChessPos(this.x + MathUtils.getSign(dest.x - this.x),
                this.y + MathUtils.getSign(dest.y - this.y));
    }

    @Override
    public String toString() {
        return "ChessPos{x=" + x + ", y=" + y + '}';
    }
}
