public class King extends ChessPiece {
    public King(int x, int y, boolean white) {
        super(x, y, white);
    }

    @Override
    public String getName() {
        return white ? "k" : "K";
    }

    @Override
    public String toString() {
        return white ? "\u265A" : "\u2654";
    }
}
