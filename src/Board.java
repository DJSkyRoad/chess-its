public class Board {
    public static final int scale = 8;
    private final Tile[][] tiles = new Tile[scale][scale];

    public Board() {
        for (int i = 0; i < this.tiles.length; i++) {
            for (int s = 0; s < this.tiles[i].length; s++) {
                this.tiles[i][s] = new Tile(s, i);
            }
        }
    }

    public String toString() {
        String s = "";
        s += this.addLetters();
        for (int r = 0; r < scale; r++) {
            for (int l = 0; l < Tile.height; l++) {
                for (int c = 0; c < scale; c++) {
                    s += this.tiles[r][c].toString(l);
                }
            }
        }
        s += this.addLetters();
        return s;
    }

    private String addLetters() {
        String s = "  ";
        for (int tileX = 0; tileX < scale; tileX++) {
            for (int x = 0; x < Tile.width; x++) {
                if (tileX != 0 && x == 0) continue;
                if (x == Tile.width / 2) s += (char)(97 + tileX);
                else s += " ";
            }
        }
        s += "\n";
        return s;
    }
}
