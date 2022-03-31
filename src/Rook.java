public class Rook extends ChessPiece {
    public Rook(int x, int y, boolean white) {
        super(x, y, white);
    }

    @Override
    public String getName() {
        return white ? "r" : "R";
    }

    @Override
    public String toString() {
        return white ? "\u265C" : "\u2656";
    }
}
