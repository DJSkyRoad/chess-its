package main.pieces;

import main.math.ChessPos;
import main.math.Move;
import main.scenes.GameScene;

import java.util.List;

public class Rook extends ChessPiece {
    public Rook(GameScene.Faction faction) {
        super(faction, "rook");
    }

    @Override
    public String toString() {
        return this.getFaction().isWhite() ? "\u265C" : "\u2656";
    }

    @Override
    public List<Move> getMoves(ChessPos pos, ChessPiece[][] board) {
        return this.getLines(pos, board);
    }
}
