package main.pieces;

import main.math.ChessPos;
import main.math.Move;
import main.scenes.GameScene;

import java.util.List;

public class Bishop extends ChessPiece {
    public Bishop(GameScene.Faction faction) {
        super(faction, "bishop");
    }

    @Override
    public String toString() {
        return this.getFaction().isWhite() ? "\u265D" : "\u2657";
    }

    @Override
    public List<Move> getMoves(ChessPos pos, ChessPiece[][] board) {
        return this.getDiagonals(pos, board);
    }
}
