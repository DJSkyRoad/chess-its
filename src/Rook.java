public class Rook extends ChessPiece {
    public Rook(boolean white) {
        super(white);
    }

    @Override
    public char getName() {
        return white ? 'r' : 'R';
    }

    @Override
    public String toString() {
        return white ? "\u265C" : "\u2656";
    }
}
