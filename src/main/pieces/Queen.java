package main.pieces;

import main.math.ChessPos;
import main.math.Move;
import main.scenes.GameScene;

import java.util.ArrayList;
import java.util.List;

public class Queen extends ChessPiece {
    public Queen(GameScene.Faction faction) {
        super(faction, "queen");
    }

    @Override
    public String toString() {
        return this.getFaction().isWhite() ? "\u265B" : "\u2655";
    }

    @Override
    public List<Move> getMoves(ChessPos pos, ChessPiece[][] board) {
        List<Move> moves = new ArrayList<>();
        moves.addAll(this.getLines(pos, board));
        moves.addAll(this.getDiagonals(pos, board));
        return moves;
    }
}
