package main.math;

import main.pieces.ChessPiece;

public class MultiMove extends Move {
    public final ChessPos pos2;
    public final ChessPos dest2;

    public MultiMove(ChessPos pos, ChessPos dest, ChessPos pos2, ChessPos dest2) {
        super(pos, dest);
        this.pos2 = pos2;
        this.dest2 = dest2;
    }

    public ChessPiece[][] getTestPosCopy(ChessPiece[][] original) {
        ChessPiece[][] test = super.getTestPosCopy(original);

        ChessPiece piece2 = test[this.pos2.y][this.pos2.x];
        test[this.pos2.y][this.pos2.x] = null;
        test[this.dest2.y][this.dest2.x] = piece2;

        return test;
    }
}
