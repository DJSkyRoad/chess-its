package main;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
    	JFrame window = new JFrame();
    	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Chess");

		BufferedImage icon = null;
		try {
			icon = ImageIO.read(Objects.requireNonNull(Main.class
					.getResourceAsStream("/resources/black_king.png")));
			window.setIconImage(icon);
		} catch (IOException e) {
			e.printStackTrace();
		}

    	Game panel = new Game();
		window.addKeyListener(panel.input);
		window.add(panel);
		window.pack();

		window.setMinimumSize(new Dimension(Game.panelSize + 200, Game.panelSize + 200));
		window.setLocationRelativeTo(null);
		window.setVisible(true);
    	
    	panel.startGameThread();
    }
}
