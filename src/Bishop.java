import javax.imageio.ImageIO;
import java.io.IOException;

public class Bishop extends ChessPiece {
    public Bishop(boolean white) {
        super(white);
        try {
            this.image = ImageIO.read(getClass()
                    .getResourceAsStream(white ? "/resources/white_bishop.png" : "/resources/black_bishop.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean canMoveTo(int xPiece, int yPiece, int xDest, int yDest, boolean foeOnDest) {
        return Math.abs(xDest - xPiece) == Math.abs(yDest - yPiece);
    }

    @Override
    public char getName() {
        return white ? 'b' : 'B';
    }

    @Override
    public String toString() {
        return white ? "\u265D" : "\u2657";
    }
}
