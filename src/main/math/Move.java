package main.math;

import main.pieces.ChessPiece;

public record Move(ChessPos pos, ChessPos dest) {

    public ChessPiece[][] getTestPosCopy(ChessPiece[][] original) {
        ChessPiece[][] test = new ChessPiece[original.length][original.length];
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, test[i], 0, original.length);
        }

        ChessPiece piece = test[this.pos.y][this.pos.x];
        test[this.pos.y][this.pos.x] = null;
        test[this.dest.y][this.dest.x] = piece;

        return test;
    }
}