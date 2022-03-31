public class Pawn extends ChessPiece {
    public Pawn(int x, int y, boolean white) {
        super(x, y, white);
    }

    @Override
    public String getName() {
        return white ? "p" : "P";
    }

    @Override
    public String toString() {
        return white ? "\u265F" : "\u2659";
    }
}
