package main.sound;

import main.Game;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class AudioPlayer {
    public static final URL PLACE_PIECE = Game.class.getResource("/resources/sounds/place_piece.wav");
    public static final URL BUTTON_CLICK = Game.class.getResource("/resources/sounds/button_click.wav");
    public static final URL BGM = Game.class.getResource("/resources/sounds/bgm.wav");

    private final Sound sound = new Sound();
    private final Sound music = new Sound();

    public void playMusic(URL url) {
        this.music.setFile(url);
        this.music.play();
        this.music.loop();
    }

    public void playSound(URL url) {
        this.sound.setFile(url);
        this.sound.play();
    }

    public void setSoundVolume(float value) {
        this.sound.setVolume(value);
    }

    public void setMusicVolume(float value) {
        this.music.setVolume(value);
    }

    public float getSoundVolume() {
        return this.sound.getVolume();
    }

    public float getMusicVolume() {
        return this.music.getVolume();
    }
}
