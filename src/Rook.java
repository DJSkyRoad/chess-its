import javax.imageio.ImageIO;
import java.io.IOException;

public class Rook extends ChessPiece {
    public Rook(boolean white) {
        super(white);
        try {
            this.image = ImageIO.read(getClass()
                    .getResourceAsStream(white ? "/resources/white_rook.png" : "/resources/black_rook.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean canMoveTo(int xPiece, int yPiece, int xDest, int yDest, boolean foeOnDest) {
        return !(Math.abs(xDest - xPiece) > 0 && Math.abs(yDest - yPiece) > 0);
    }

    @Override
    public char getName() {
        return white ? 'r' : 'R';
    }

    @Override
    public String toString() {
        return white ? "\u265C" : "\u2656";
    }
}
