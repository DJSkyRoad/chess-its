package main.scenes;

import main.gui.Board;
import main.Game;
import main.math.ChessPos;
import main.math.Move;
import main.pieces.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScene extends Scene {
    private final Board board = new Board();
    private final GameMode gameMode;
    private final Random random = new Random();
    private final Faction playerFaction;
    public Faction currentTurn;

    private List<Move> moves;
    private Move lastMove;

    public GameScene(GameMode gameMode, Faction playerFaction) {
        this.gameMode = gameMode;
        this.playerFaction = playerFaction;
        this.currentTurn = Faction.WHITE;

        if (gameMode == GameMode.PVPHOST) Game.INSTANCE.startServer();
        else if (gameMode == GameMode.PVPGUEST) Game.INSTANCE.startClient();

        this.generateMoves(this.currentTurn);

        if (this.currentTurn != this.playerFaction && this.gameMode == GameMode.PVC) {
            this.doAITurn();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());

        this.board.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Helvetia", Font.BOLD, 30));
        Game.drawCenteredString(g2, this.currentTurn+"'s turn", this.getWidth() / 2, 30);
    }

    @Override
    public void onMouseHover(int x, int y) {
        ChessPos chessPos = new ChessPos(x / Game.tileSize - 1, y / Game.tileSize - 1);
        if (this.canSelect(chessPos)) this.board.hovered = chessPos;
        else this.board.hovered = new ChessPos(-1, -1);
    }

    @Override
    public void onMouseClick(int x, int y) {
        ChessPos chessPos = new ChessPos(x / Game.tileSize - 1, y / Game.tileSize - 1);
        if (this.board.selected.equals(chessPos)) this.board.selected = new ChessPos(-1, -1);
        else if (this.canMoveSelectedTo(chessPos)) {
            this.performMove(new Move(this.board.selected, chessPos));
            this.changeTurn();
        }
        else if (this.canSelect(chessPos)) this.board.selected = chessPos;
    }

    private void changeTurn() {
        this.board.selected = new ChessPos(-1, -1);

        this.generateMoves(this.currentTurn);
        boolean checked = this.isChecked(this.moves, this.board.pos, this.currentTurn.opposite());
        this.generateMoves(this.currentTurn.opposite());
        if (this.moves.isEmpty()) {
            this.onGameOver(checked);
            return;
        }

        this.currentTurn = this.currentTurn.opposite();

        if (this.currentTurn != this.playerFaction && this.gameMode == GameMode.PVC) {
            this.doAITurn();
        }
    }

    private void onGameOver(boolean checked) {
        String title = checked ? "Checkmate" : "Patt";
        String subtitle = this.currentTurn+" won";
        Game.INSTANCE.closeNetworkManager();
        Game.INSTANCE.setScene(new GameOverScene(title, subtitle));
    }

    private void doAITurn() {
        List<Move> possibleMoves = new ArrayList<>();
        int priority = 0;
        for (Move move : this.moves) {
            ChessPiece enemy = this.getPiece(move.dest);
            ChessPiece[][] postBoard = move.getTestPosCopy(this.board.pos);
            List<Move> postMoves = this.getLegalMoves(this.generateAllMoves(postBoard, this.currentTurn), this.currentTurn);
            int p = 0;

            if (enemy != null) {
                p = 1;
                if (enemy.isKing()) {
                    p = 2;
                }
            }
            if (this.isChecked(postMoves, postBoard, this.currentTurn.opposite())) {
                p = 3;
            }

            if (p >= priority) {
                if (p > priority) possibleMoves.clear();
                possibleMoves.add(move);
                priority = p;
            }
        }

        int i = this.random.nextInt(possibleMoves.size());
        this.performMove(possibleMoves.get(i));
        this.changeTurn();
    }

    private ChessPiece getPiece(ChessPos chessPos) {
        return this.board.pos[chessPos.x][chessPos.y];
    }

    private void generateMoves(Faction faction) {
        this.moves = this.getLegalMoves(this.generateAllMoves(this.board.pos, faction), faction);
    }

    private List<Move> generateAllMoves(ChessPiece[][] pos, Faction faction) {
        List<Move> moves = new ArrayList<>();
        for (int y = 0; y < pos.length; y++) {
            for (int x = 0; x < pos.length; x++) {
                ChessPiece piece = pos[y][x];
                if (piece != null && piece.getFaction() == faction) moves.addAll(piece.getMoves(new ChessPos(x, y), pos));
            }
        }
        return moves;
    }

    private List<Move> getLegalMoves(List<Move> moves, Faction faction) {
        List<Move> newMoves = new ArrayList<>();
        for (Move move : moves) {
            // test move
            ChessPiece[][] postField = move.getTestPosCopy(this.board.pos);
            List<Move> postMoves = this.generateAllMoves(postField, faction.opposite());
            // check whether the move was illegal
            if (!this.isChecked(postMoves, postField, faction)) newMoves.add(move);
        }
        return newMoves;
    }

    private boolean isChecked(List<Move> moves, ChessPiece[][] board, Faction faction) {
        for (Move postMove : moves) {
            ChessPiece piece = board[postMove.dest.y][postMove.dest.x];
            if (piece != null && piece.isKing() && piece.getFaction() == faction) return true;
        }
        return false;
    }

    private boolean canSelect(ChessPos chessPos) {
        return chessPos.isValid() && ((this.board.pos[chessPos.y][chessPos.x] != null
                && this.board.pos[chessPos.y][chessPos.x].getFaction() == this.currentTurn)
                || (this.board.selected.isValid() && this.canMoveSelectedTo(chessPos)));
    }

    private boolean canMoveSelectedTo(ChessPos dest) {
        if (!this.board.selected.isValid() || !dest.isValid()) return false;
        for (Move move : this.moves) {
            if (move.pos.equals(this.board.selected) && move.dest.equals(dest)) return true;
        }
        return false;
    }

    private void performMove(Move move) {
        ChessPiece piece = this.board.pos[move.pos.y][move.pos.x];
        this.board.pos[move.pos.y][move.pos.x] = null;
        this.board.pos[move.dest.y][move.dest.x] = piece;
        piece.isMoved = true;
        this.lastMove = move;
    }

    public enum Faction {
        WHITE, BLACK;

        public Faction opposite() {
            switch(this) {
                case WHITE: return BLACK;
                default:
                case BLACK: return WHITE;
            }
        }

        @Override
        public String toString() {
            switch(this) {
                default:
                case WHITE: return "White";
                case BLACK: return "Black";
            }
        }

        public boolean isWhite() {
            return this == WHITE;
        }
    }

    public enum GameMode {
        OFFLINEPVP, PVC, PVPHOST, PVPGUEST;

        @Override
        public String toString() {
            switch(this) {
                default:
                case OFFLINEPVP: return "Offline PvP";
                case PVC: return "PvC";
                case PVPHOST: return "PvP Host";
                case PVPGUEST: return "PvP Guest";
            }
        }

        public boolean isOnline() {
            return this == PVPHOST || this == PVPGUEST;
        }

        public GameMode next() {
            int i = this.ordinal()+1 < values().length ? this.ordinal()+1 : 0;
            return values()[i];
        }
    }
}
