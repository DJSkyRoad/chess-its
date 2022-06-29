package main.pieces;

import main.gui.Board;
import main.math.ChessPos;
import main.math.Move;
import main.scenes.GameScene;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece {
    private boolean checked;

    public King(GameScene.Faction faction) {
        super(faction, "king");
    }

    @Override
    public String toString() {
        return this.getFaction().isWhite() ? "\u265A" : "\u2654";
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean isChecked() {
        return checked;
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

        // Add Rochade Moves
        if (!this.checked && this.neverMoved) {
            if (this.getFaction().isWhite()) {
                ChessPiece rook = board[Board.scale - 1][Board.scale - 1];
                if (rook != null && rook.neverMoved) addIfPossible(moves, pos, pos.add(2, 0), board);
                rook = board[Board.scale - 1][0];
                if (rook != null && rook.neverMoved) addIfPossible(moves, pos, pos.add(2, 0), board);
            } else {
                ChessPiece rook = board[Board.scale - 1][Board.scale - 1];
                if (rook != null && rook.neverMoved) addIfPossible(moves, pos, pos.add(2, 0), board);
            }

            for (int x = pos.x; x < Board.scale; x++) {
                ChessPiece piece = board[pos.y][x];
                if (piece != null
                || (x == Board.scale - 1
                        && (piece == null || !(piece instanceof Rook) || !piece.neverMoved || piece.getFaction() != this.getFaction()))) break;
                moves.add(new Move(pos, pos.add(2, 0)));
            }
            for (int x = pos.x; x >= 0; x--) {
                ChessPiece piece = board[pos.y][x];
                if (piece != null
                        || (x == Board.scale - 1
                        && (piece == null || !(piece instanceof Rook) || !piece.neverMoved || piece.getFaction() != this.getFaction()))) break;
                moves.add(new Move(pos, pos.add(-2, 0)));
            }
        }

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
