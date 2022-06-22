package main.pieces;

import main.math.ChessPos;
import main.math.Move;
import main.scenes.GameScene;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece {
    public King(GameScene.Faction faction) {
        super(faction, "king");
    }

    @Override
    public String toString() {
        return this.getFaction().isWhite() ? "\u265A" : "\u2654";
    }

    @Override
    public List<Move> getMoves(ChessPos pos, ChessPiece[][] board) {
        List<Move> moves = new ArrayList<>();

        addIfPossible(moves, pos, pos.add(1, 1), board);
        addIfPossible(moves, pos, pos.add(-1, -1), board);
        addIfPossible(moves, pos, pos.add(-1, 1), board);
        addIfPossible(moves, pos, pos.add(1, -1), board);

        addIfPossible(moves, pos, pos.add(1, 0), board);
        addIfPossible(moves, pos, pos.add(0, 1), board);
        addIfPossible(moves, pos, pos.add(-1, 0), board);
        addIfPossible(moves, pos, pos.add(0, -1), board);

        return moves;
    }

    private void addIfPossible(List<Move> moves, ChessPos pos, ChessPos dest, ChessPiece[][] board) {
        if (dest.isValid() && (board[dest.y][dest.x] == null || board[dest.y][dest.x].getFaction() != this.getFaction()))
            moves.add(new Move(pos, dest));
    }

    @Override
    public boolean isKing() {
        return true;
    }
}
