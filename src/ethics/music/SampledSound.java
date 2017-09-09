package ethics.music;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SampledSound extends Sound {
    private Clip clip;
    
    public SampledSound(String fileName) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File file = new File(fileName);
        AudioInputStream stream = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(stream);
    }

    @Override
    public void play() {
        clip.start();
    }

    @Override
    public void stop() {
        clip.stop();
    }

    @Override
    public void restart() {
        stop();
        clip.setFramePosition(0);
    }
    
    @Override
    public void loop() {
        restart();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    @Override
    public void loop(int numberOfTimes) {
        restart();
        clip.loop(numberOfTimes);
    }

    @Override
    public boolean finished() {
        return clip.getFramePosition() == clip.getFrameLength();
    }
}
