public class Pawn extends ChessPiece {
    public Pawn(boolean white) {
        super(white);
    }

    @Override
    public char getName() {
        return white ? 'p' : 'P';
    }

    @Override
    public String toString() {
        return white ? "\u265F" : "\u2659";
    }
}
