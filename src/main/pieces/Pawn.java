package main.pieces;

import main.Board;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Pawn extends ChessPiece {
    public Pawn(boolean white) {
        super(white);
        try {
            this.image = ImageIO.read(getClass()
                    .getResourceAsStream(white ? "/resources/white_pawn.png" : "/resources/black_pawn.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean canMoveTo(int xPiece, int yPiece, int xDest, int yDest, boolean foeOnDest) {
        int yDist = yDest - yPiece;
        int xDist = xDest - xPiece;
        return ((this.white && (yDist == -1 || (yDist == -2 && yPiece == Board.scale - 1)))
                || (!this.white && (yDist == 1 || (yDist == 2 && yPiece == 1))))
                && (xDist == 0 || (Math.abs(xDist) == 1 && foeOnDest));
    }

    @Override
    public char getName() {
        return white ? 'p' : 'P';
    }

    @Override
    public String toString() {
        return white ? "\u265F" : "\u2659";
    }
}
