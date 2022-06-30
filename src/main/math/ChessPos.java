package main.math;

import main.gui.Board;

public class ChessPos {
    public int x;
    public int y;

    public ChessPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ChessPos distanceTo(ChessPos pos) {
        return new ChessPos(pos.x - this.x, pos.y - this.y);
    }

    public boolean equals(ChessPos pos) {
        return this.x == pos.x && this.y == pos.y;
    }

    public boolean isValid() {
        return MathUtils.inRange(this.x, 0, Board.scale - 1)
                && MathUtils.inRange(this.y, 0, Board.scale - 1);
    }

    public ChessPos add(int x, int y) {
        return new ChessPos(this.x + x, this.y + y);
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
