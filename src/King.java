public class King extends ChessPiece {
    public King(boolean white) {
        super(white);
    }

    @Override
    public char getName() {
        return white ? 'k' : 'K';
    }

    @Override
    public String toString() {
        return white ? "\u265A" : "\u2654";
    }
}
