package main.pieces;

import main.Game;
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
    public void onMovedPre(GameScene scene, Move move) {
        super.onMovedPre(scene, move);
        ChessPos enemy = move.dest.add(0, this.getFaction().isWhite() ? 1 : -1);
        System.out.println(enemy);
        ChessPos dist = move.getDist();
        if (this.isEnemy(scene.getPiece(enemy)) && Math.abs(dist.x) == 1 && Math.abs(dist.y) == 1) {
            scene.setPiece(null, enemy);
        }
    }

    @Override
    public void onMovedPost(GameScene scene, Move move) {
        super.onMovedPost(scene, move);
        if ((this.getFaction().isWhite() && move.dest.y == 0)
        || (!this.getFaction().isWhite() && move.dest.y == Board.scale - 1)) {
            scene.setPiece(new Queen(this.getFaction()), move.dest);
        }
    }

    @Override
    public List<Move> getMoves(ChessPos pos, ChessPiece[][] board) {
        List<Move> moves = new ArrayList<>();

        int yDir = this.getFaction().isWhite() ? -1 : 1;
        Move enemyMove = Game.INSTANCE.getScene() instanceof GameScene ? ((GameScene)Game.INSTANCE.getScene()).lastMove : null;

        ChessPos dest = pos.add(0, yDir);
        if (dest.isValid() && board[dest.y][dest.x] == null) moves.add(new Move(pos, dest));

        dest = pos.add(1, yDir);
        if (dest.isValid() && (this.isEnemy(board[dest.y][dest.x])
                || this.canEnPassant(dest, pos, enemyMove, board))) moves.add(new Move(pos, dest));

        dest = pos.add(-1, yDir);
        if (dest.isValid() && (this.isEnemy(board[dest.y][dest.x])
                || this.canEnPassant(dest, pos, enemyMove, board))) moves.add(new Move(pos, dest));

        dest = pos.add(0, yDir * 2);
        if (dest.isValid() && board[dest.y][dest.x] == null
                && ((this.getFaction().isWhite() && pos.y == Board.scale - 2)
                    || (!this.getFaction().isWhite() && pos.y == 1))) moves.add(new Move(pos, dest));

        return moves;
    }

    private boolean canEnPassant(ChessPos dest, ChessPos pos, Move enemyMove, ChessPiece[][] board) {
        return enemyMove != null
                && this.isEnemy(board[pos.y][dest.x]) && board[pos.y][dest.x] instanceof Pawn
                && enemyMove.dest.equals(new ChessPos(dest.x, pos.y)) && Math.abs(enemyMove.getDist().y) == 2;
    }
}
