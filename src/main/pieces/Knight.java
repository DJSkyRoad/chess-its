package main.pieces;

import main.math.ChessPos;
import main.math.Move;
import main.scenes.GameScene;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Knight extends ChessPiece {
    public Knight(GameScene.Faction faction) {
        super(faction);
        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass()
                    .getResourceAsStream(faction.isWhite() ? "/resources/white_knight.png" : "/resources/black_knight.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.getFaction().isWhite() ? "\u265E" : "\u2658";
    }

    @Override
    public List<Move> getMoves(ChessPos pos, ChessPiece[][] board) {
        List<Move> moves = new ArrayList<>();

        addIfPossible(moves, pos, pos.add(2, 1), board);
        addIfPossible(moves, pos, pos.add(1, 2), board);
        addIfPossible(moves, pos, pos.add(-2, 1), board);
        addIfPossible(moves, pos, pos.add(-1, 2), board);
        addIfPossible(moves, pos, pos.add(2, -1), board);
        addIfPossible(moves, pos, pos.add(1, -2), board);
        addIfPossible(moves, pos, pos.add(-2, -1), board);
        addIfPossible(moves, pos, pos.add(-1, -2), board);

        return moves;
    }

    private void addIfPossible(List<Move> moves, ChessPos pos, ChessPos dest, ChessPiece[][] board) {
        if (dest.isValid() && (board[dest.y][dest.x] == null || board[dest.y][dest.x].getFaction() != this.getFaction()))
            moves.add(new Move(pos, dest));
    }
}
