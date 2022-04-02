public class King extends ChessPiece {
    public King(boolean white) {
        super(white);
    }

    @Override
    public boolean canMoveTo(int xPiece, int yPiece, int xDest, int yDest, boolean foeOnDest) {
        int xDist = Math.abs(xDest - xPiece);
        int yDist = Math.abs(yDest - yPiece);
        return !(xDist > 1 || yDist > 1);
    }

    @Override
    public char getName() {
        return white ? 'k' : 'K';
    }

    @Override
    public String toString() {
        return white ? "\u265A" : "\u2654";
    }
}
