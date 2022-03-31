public class Bishop extends ChessPiece {
    public Bishop(int x, int y, boolean white) {
        super(x, y, white);
    }

    @Override
    public String getName() {
        return white ? "b" : "B";
    }

    @Override
    public String toString() {
        return white ? "\u265D" : "\u2657";
    }
}
