public class Queen extends ChessPiece {
    public Queen(boolean white) {
        super(white);
    }

    @Override
    public boolean canMoveTo(int xPiece, int yPiece, int xDest, int yDest, boolean foeOnDest) {
        int xDist = Math.abs(xDest - xPiece);
        int yDist = Math.abs(yDest - yPiece);
        return !(xDist > 0 && yDist > 0)
                || xDist == yDist;
    }

    @Override
    public char getName() {
        return white ? 'q' : 'Q';
    }

    @Override
    public String toString() {
        return white ? "\u265B" : "\u2655";
    }
}
