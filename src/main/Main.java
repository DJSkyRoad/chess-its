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

    	Game panel = new Game();
		window.addKeyListener(panel.keyInput);
		window.add(panel);
		window.pack();

		window.setLocationRelativeTo(null);
		window.setVisible(true);
    	
    	panel.startGameThread();
    }
}
