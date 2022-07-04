package main.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {
    private Clip clip;
    private float volume = 1F;
    private FloatControl fc;

    public void setFile(URL url) {
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(url);
            this.clip = AudioSystem.getClip();
            this.clip.open(inputStream);
            this.fc = (FloatControl)this.clip.getControl(FloatControl.Type.MASTER_GAIN);
            this.fc.setValue(this.volume <= 0 ? -80 : (1 - this.volume) * -50);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (this.clip != null) this.clip.start();
    }

    public void loop() {
        if (this.clip != null) this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void setVolume(float value) {
        this.volume = value;
        if (this.fc != null) this.fc.setValue(this.volume <= 0 ? -80 : (1 - this.volume) * -50);
    }

    public float getVolume() {
        return this.volume;
    }
}
