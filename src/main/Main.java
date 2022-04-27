package main;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
    	JFrame window = new JFrame();
    	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Chess");

		BufferedImage icon = null;
		try {
			icon = ImageIO.read(Main.class
					.getResourceAsStream("/resources/black_king.png"));
			window.setIconImage(icon);
		} catch (IOException e) {
			e.printStackTrace();
		}

		window.setResizable(false);

    	GamePanel panel = new GamePanel();
		window.add(panel);
		window.pack();

		window.setLocationRelativeTo(null);
		window.setVisible(true);
    	
    	panel.startGameThread();

        /*Scanner scanner = new Scanner(System.in);

        main.Board brd = new main.Board();
        boolean whiteTurn = true;

        while (true) {
            System.out.println(brd);
            System.out.print(whiteTurn ? "White's turn: " : "Black's turn: ");
            String inp = scanner.nextLine(); // e.g. pb2-b3
            char name = inp.charAt(0);
            int xPiece = (int)inp.charAt(1) - 97;
            int yPiece = main.Board.scale - Character.getNumericValue(inp.charAt(2));
            int xDest = (int)inp.charAt(4) - 97;
            int yDest = main.Board.scale - Character.getNumericValue(inp.charAt(5));
            if (brd.check(name, whiteTurn, xPiece, yPiece, xDest, yDest)) {
                if (brd.canEscapeCheckTo(name, whiteTurn, xPiece, yPiece, xDest, yDest)) {    //check
                    brd.movePieceTo(xPiece, yPiece, xDest, yDest);
                    whiteTurn = !whiteTurn;
                } else {
                    System.out.println("Invalid command! Please try again.");
                }
            } else {
                if (brd.canMovePieceTo(name, whiteTurn, xPiece, yPiece, xDest, yDest)) {
                    brd.movePieceTo(xPiece, yPiece, xDest, yDest);
                    whiteTurn = !whiteTurn;
                } else {
                    System.out.println("Invalid command! Please try again.");
                }
            }
        }*/
    }
}
