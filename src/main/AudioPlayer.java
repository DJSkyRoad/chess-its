package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class AudioPlayer {
    public static final URL PLACE_PIECE = Game.class.getResource("/resources/sounds/place_piece.wav");
    public static final URL BUTTON_CLICK = Game.class.getResource("/resources/sounds/button_click.wav");

    private Clip clip;

    public void start() {
        if (this.clip != null) this.clip.start();
    }

    public void open(URL url) {
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(url);
            this.clip = AudioSystem.getClip();
            this.clip.open(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
