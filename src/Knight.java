public class Knight extends ChessPiece {
    public Knight(boolean white) {
        super(white);
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
