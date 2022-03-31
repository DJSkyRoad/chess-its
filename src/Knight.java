public class Knight extends ChessPiece {
    public Knight(int x, int y, boolean white) {
        super(x, y, white);
    }

    @Override
    public String getName() {
        return white ? "n" : "N";
    }

    @Override
    public String toString() {
        return white ? "\u265E" : "\u2658";
    }
}
