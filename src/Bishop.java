public class Bishop extends ChessPiece {
    public Bishop(boolean white) {
        super(white);
    }

    @Override
    public char getName() {
        return white ? 'b' : 'B';
    }

    @Override
    public String toString() {
        return white ? "\u265D" : "\u2657";
    }
}
