public class Queen extends ChessPiece {
    public Queen(int x, int y, boolean white) {
        super(x, y, white);
    }

    @Override
    public String getName() {
        return white ? "q" : "Q";
    }

    @Override
    public String toString() {
        return white ? "\u265B" : "\u2655";
    }
}
