package main.scenes;

import main.Game;
import main.gui.Button;
import main.gui.Slider;

import java.awt.*;


public class SettingsScene extends Scene {
    @Override
    public void init() {
        super.init();
        this.addWidget(new Slider(Game.INSTANCE.audioPlayer.getMusicVolume(), this.getWidth() / 2, this.getHeight() / 2 - 60, 200, 50, (slider) -> {
            Game.INSTANCE.audioPlayer.setMusicVolume(slider.getValue());
        }));
        this.addWidget(new Slider(Game.INSTANCE.audioPlayer.getSoundVolume(), this.getWidth() / 2, this.getHeight() / 2 + 40, 200, 50, (slider) -> {
            Game.INSTANCE.audioPlayer.setSoundVolume(slider.getValue());
        }));
        this.addWidget(new Button("Quit to Title", this.getWidth() / 2, this.getHeight() / 2 + 160, 200, 50, (button) -> {
            Game.INSTANCE.setScene(new TitleScene());
            Game.INSTANCE.setOverlayScene(null);
        }));
    }

    @Override
    public void draw(Graphics2D g2) {
        this.drawBackground(g2);
        super.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(Game.INSTANCE.font.deriveFont(Font.BOLD, 50));
        drawCenteredString(g2, "Settings", this.getWidth() / 2, 100);
        g2.setFont(Game.INSTANCE.font.deriveFont(Font.PLAIN, 20));
        drawCenteredString(g2, "Music Volume", this.getWidth() / 2, this.getHeight() / 2 - 100);
        drawCenteredString(g2, "Sound Volume", this.getWidth() / 2, this.getHeight() / 2);
    }
}
