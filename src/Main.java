import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Board brd = new Board();
        boolean whiteTurn = true;

        while (true) {
            System.out.println(brd);
            System.out.print(whiteTurn ? "White's turn: " : "Black's turn: ");
            String inp = scanner.nextLine(); // e.g. p2b-3b
            char name = inp.charAt(0);
            int xOld = (int)inp.charAt(1) - 97;
            int yOld = Board.scale - Character.getNumericValue(inp.charAt(2));
            int xDest = (int)inp.charAt(4) - 97;
            int yDest = Board.scale - Character.getNumericValue(inp.charAt(5));
            if (brd.canMovePieceTo(name, whiteTurn, xOld, yOld, xDest, yDest)) {
                brd.movePieceTo(xOld, yOld, xDest, yDest);
                whiteTurn = !whiteTurn;
            } else {
                System.out.println("Invalid command! Please try again.");
            }
        }
    }
}
