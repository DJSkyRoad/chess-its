package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;

public class AudioPlayer {

    public void play() {
        new Thread(this::playAudio).start();
    }

    private void playAudio() {
        try {
            Clip clip = AudioSystem.getClip();
            InputStream resource = Game.class.getResourceAsStream("resources/sounds/" + "sound.wav");
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(resource);
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
