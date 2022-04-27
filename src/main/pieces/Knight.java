package main.pieces;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Knight extends ChessPiece {
    public Knight(boolean white) {
        super(white);
        try {
            this.image = ImageIO.read(getClass()
                    .getResourceAsStream(white ? "/resources/white_knight.png" : "/resources/black_knight.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean canMoveTo(int xPiece, int yPiece, int xDest, int yDest, boolean foeOnDest) {
        int xDist = Math.abs(xDest - xPiece);
        int yDist = Math.abs(yDest - yPiece);
        return (xDist == 2 && yDist == 1) || (xDist == 1 && yDist == 2);
    }

    @Override
    public char getName() {
        return white ? 'n' : 'N';
    }

    @Override
    public String toString() {
        return white ? "\u265E" : "\u2658";
    }
}
