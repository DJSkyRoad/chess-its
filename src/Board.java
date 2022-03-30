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
        s += "  a b c d e f g h\n";
        for (int r = 0; r < scale; r++) {
            for (int l = 0; l < Tile.height; l++) {
                for (int c = 0; c < scale; c++) {
                    s += this.tiles[r][c].toString(l);
                }
            }
        }
        s += "  a b c d e f g h\n";
        return s;
    }
}
