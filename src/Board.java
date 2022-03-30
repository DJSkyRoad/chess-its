public class Board {
    public String toString() {
        String brdStr = "";
        brdStr += "  a b c d e f g h\n";
        for (int r = 0; r < 8; r++) {
            brdStr += (8 - r) + "";
            for (int c = 0; c < 8; c++) {
                brdStr += " .";
            }
            brdStr += " " + (8 - r) + "\n";
        }
        brdStr += "  a b c d e f g h\n";
        return brdStr;
    }
}
