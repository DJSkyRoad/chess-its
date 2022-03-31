public abstract class ChessPiece {
    public int x;
    public int y;
    public final boolean white;

    public ChessPiece(int x, int y, boolean white) {
        this.x = x;
        this.y = y;
        this.white = white;
    }

    public abstract String toString();
    public abstract String getName();
}
