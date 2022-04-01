public class Queen extends ChessPiece {
    public Queen(boolean white) {
        super(white);
    }

    @Override
    public char getName() {
        return white ? 'q' : 'Q';
    }

    @Override
    public String toString() {
        return white ? "\u265B" : "\u2655";
    }
}
