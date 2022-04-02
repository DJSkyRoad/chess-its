public class Board {
    public static final int scale = 8;
    public static final int tileWidth = 3;
    public static final int tileHeight = 1;
    public ChessPiece[][] pos = {
            {new Rook(false), new Knight(false), new Bishop(false), new Queen(false), new King(false), new Bishop(false), new Knight(false), new Rook(false)},
            {new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false)},
            {null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null},
            {new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true)},
            {new Rook(true), new Knight(true), new Bishop(true), new Queen(true), new King(true), new Bishop(true), new Knight(true), new Rook(true)}
    };

    public boolean canMovePieceTo(char name, boolean whiteTurn, int xPiece, int yPiece, int xDest, int yDest) {
        ChessPiece piece = pos[xPiece][yPiece];
        ChessPiece destPiece = pos[xDest][yDest];
        if (piece == null
                || piece.getName() != name
                || piece.white != whiteTurn
                || (destPiece != null && destPiece.white == whiteTurn)) return false;
        return piece.canMoveTo(xPiece, yPiece, xDest, yDest);
    }

    public void movePieceTo(int xPiece, int yPiece, int xDest, int yDest) {
        ChessPiece piece = pos[xPiece][yPiece];
        pos[xPiece][yPiece] = null;
        pos[xDest][yDest] = piece;
    }

    public String toString() {
        String s = "";
        s += this.getLetterString();
        for (int r = 0; r < scale; r++) {
            for (int l = 0; l < tileHeight; l++) {
                for (int c = 0; c < scale; c++) {
                    s += this.getTileString(c, r, l);
                }
            }
        }
        s += this.getLetterString();
        return s;
    }

    private String getTileString(int x, int y, int line) {
        if (line >= tileHeight || line < 0) return "INVALID LINE INDEX";
        String s = "";
        if (x == 0) {
            if (line == tileHeight / 2) s += (Board.scale - y) + " ";
            else s += "  ";
        }
        if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) s += "\u001B[47m";
        for (int column = 0; column < tileWidth; column++) {
            if (line == tileHeight / 2 && column == tileWidth / 2 && pos[y][x] != null) s += pos[y][x];
            else s += "\u2003";
        }
        s += "\u001B[0m";
        if (x == Board.scale - 1) {
            if (line == tileHeight / 2) s += " " + (Board.scale - y);
            s += '\n';
        }
        return s;
    }

    private String getLetterString() {
        String s = "  ";
        for (int tileX = 0; tileX < scale; tileX++) {
            for (int x = 0; x < tileWidth; x++) {
                if (tileX != 0 && x == 0) continue;
                if (x == tileWidth / 2) s += (char)(97 + tileX);
                else s += " ";
            }
        }
        s += "\n";
        return s;
    }
}
