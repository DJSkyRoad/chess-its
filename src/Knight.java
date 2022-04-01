public class Knight extends ChessPiece {
    public Knight(boolean white) {
        super(white);
    }

    @Override
    public char getName() {
        return white ? 'n' : 'N';
    }

    @Override
    public String toString() {
        return white ? "\u265E" : "\u2658";
    }
}
