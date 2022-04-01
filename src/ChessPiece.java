public abstract class ChessPiece {
    public final boolean white;

    public ChessPiece(boolean white) {
        this.white = white;
    }

    public boolean canMoveTo(int xOld, int yOld, int xDest, int yDest) {
        return true;
    }

    public abstract String toString();
    public abstract char getName();
}
