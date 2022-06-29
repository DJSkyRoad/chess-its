package main.pieces;

import main.gui.Board;
import main.math.ChessPos;
import main.math.Move;
import main.scenes.GameScene;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece {
    public Pawn(GameScene.Faction faction) {
        super(faction, "pawn");
    }

    @Override
    public String toString() {
        return this.getFaction().isWhite() ? "\u265F" : "\u2659";
    }

    @Override
    public List<Move> getMoves(ChessPos pos, ChessPiece[][] board) {
        List<Move> moves = new ArrayList<>();

        if (this.getFaction().isWhite()) {
            ChessPos dest = pos.add(0, -1);
            if (dest.isValid() && board[dest.y][dest.x] == null) {
                moves.add(new Move(pos, dest));
            }
            dest = pos.add(1, -1);
            if (dest.isValid() && board[dest.y][dest.x] != null && !board[dest.y][dest.x].getFaction().isWhite()) {
                moves.add(new Move(pos, dest));
            }
            dest = pos.add(-1, -1);
            if (dest.isValid() && board[dest.y][dest.x] != null && !board[dest.y][dest.x].getFaction().isWhite()) {
                moves.add(new Move(pos, dest));
            }
            dest = pos.add(0, -2);
            if (dest.isValid() && board[dest.y][dest.x] == null && pos.y == Board.scale - 2) {
                moves.add(new Move(pos, dest));
            }
        }
        else {
            ChessPos dest = pos.add(0, 1);
            if (dest.isValid() && board[dest.y][dest.x] == null) {
                moves.add(new Move(pos, dest));
            }
            dest = pos.add(1, 1);
            if (dest.isValid() && board[dest.y][dest.x] != null && board[dest.y][dest.x].getFaction().isWhite()) {
                moves.add(new Move(pos, dest));
            }
            dest = pos.add(-1, 1);
            if (dest.isValid() && board[dest.y][dest.x] != null && board[dest.y][dest.x].getFaction().isWhite()) {
                moves.add(new Move(pos, dest));
            }
            dest = pos.add(0, 2);
            if (dest.isValid() && board[dest.y][dest.x] == null && pos.y == 1) {
                moves.add(new Move(pos, dest));
            }
        }

        return moves;
    }
}
