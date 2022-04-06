import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
    public static void main(String[] args) {
    	JFrame window = new JFrame();
    	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Chess");
		window.setResizable(false);

    	GamePanel panel = new GamePanel();
		window.add(panel);
		window.pack();

		window.setLocationRelativeTo(null);
		window.setVisible(true);
    	
    	panel.startGameThread();

        /*Scanner scanner = new Scanner(System.in);

        Board brd = new Board();
        boolean whiteTurn = true;

        while (true) {
            System.out.println(brd);
            System.out.print(whiteTurn ? "White's turn: " : "Black's turn: ");
            String inp = scanner.nextLine(); // e.g. pb2-b3
            char name = inp.charAt(0);
            int xPiece = (int)inp.charAt(1) - 97;
            int yPiece = Board.scale - Character.getNumericValue(inp.charAt(2));
            int xDest = (int)inp.charAt(4) - 97;
            int yDest = Board.scale - Character.getNumericValue(inp.charAt(5));
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
