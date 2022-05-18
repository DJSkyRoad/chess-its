package main.pieces;

import main.math.ChessPos;
import main.math.Move;

import java.awt.image.BufferedImage;
import java.util.List;

public abstract class ChessPiece {
    protected BufferedImage image;
	private final boolean white;

    public ChessPiece(boolean white) {
        this.white = white;
    }
    public abstract String toString();
    public abstract List<Move> getMoves(ChessPos pos, ChessPiece[][] board);

    public BufferedImage getImage() {
        return this.image;
    }
    public boolean isWhite() {
        return this.white;
    }
    public boolean isKing() {
        return false;
    }
}
