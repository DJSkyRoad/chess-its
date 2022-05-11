package main.pieces;

import main.math.ChessPos;
import main.math.Move;

import java.awt.image.BufferedImage;
import java.util.List;

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
    public abstract List<Move> getMoves(ChessPos pos, ChessPiece[][] board);
    public abstract char getName();
    public BufferedImage getImage() {
        return this.image;
    }
}
