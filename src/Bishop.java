public class Bishop extends ChessPiece {
    public Bishop(boolean white) {
        super(white);
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
