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
    public void onMoved(GameScene scene, Move move) {
        super.onMoved(scene, move);

        //Castle
        ChessPos dist = move.getDist();
        if (Math.abs(dist.x) == 2 && dist.y == 0) {
            int x = dist.x == 2 ? Board.scale - 1 : 0;
            int y = this.getFaction().isWhite() ? Board.scale - 1 : 0;
            ChessPos pos = new ChessPos(x, y);
            ChessPiece piece = scene.getPiece(pos);
            if (this.canCastleWith(piece)) scene.performMove(new Move(pos, pos.add(dist.x == 2 ? -2 : 3, 0)));
        }
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

        // Add Castle Moves
        if (!this.checked && this.neverMoved) {
            for (int x = pos.x + 1; x < Board.scale; x++) {
                ChessPiece piece = board[pos.y][x];
                if ((x < Board.scale - 1 && piece != null) || (x == Board.scale - 1 && !this.canCastleWith(piece))) break;
                addIfPossible(moves, pos, pos.add(2, 0), board);
            }
            for (int x = pos.x - 1; x >= 0; x--) {
                ChessPiece piece = board[pos.y][x];
                if ((x > 1 && piece != null) || (x == 0 && !this.canCastleWith(piece))) break;
                addIfPossible(moves, pos, pos.add(-2, 0), board);
            }
        }

        return moves;
    }

    private boolean canCastleWith(ChessPiece piece) {
        return piece != null && piece instanceof Rook && piece.neverMoved && piece.getFaction() == this.getFaction();
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
