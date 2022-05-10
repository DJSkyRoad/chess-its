package main.pieces;

import main.math.ChessPos;

import java.awt.image.BufferedImage;

public abstract class ChessPiece {
    public BufferedImage image;
	public final boolean white;

    public ChessPiece(boolean white) {
        this.white = white;
    }

    public boolean canMoveTo(ChessPos piece, ChessPos dest, boolean foeOnDest) {
        return this.canMoveTo(piece.x, piece.y, dest.x, dest.y, foeOnDest);
    }
    public abstract boolean canMoveTo(int xPiece, int yPiece, int xDest, int yDest, boolean foeOnDest);
    public abstract String toString();
    public abstract char getName();
    public BufferedImage getImage() {
        return this.image;
    }
}
