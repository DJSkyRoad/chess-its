public abstract class ChessPiece {
    public int x;
    public int y;

    public ChessPiece(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract String toString();
    public abstract String getName();
}
