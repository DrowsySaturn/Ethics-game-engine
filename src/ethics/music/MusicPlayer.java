package ethics.music;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicPlayer {
    private HashMap<String, Sound> loadedSounds = new HashMap<>();
    
    public void preloadSampledSound(String soundFile) throws IOException {
        try {
            loadedSounds.put(soundFile, new SampledSound(soundFile));
        } catch (UnsupportedAudioFileException | LineUnavailableException ex) {
            throw new RuntimeException(String.format("Unable to parse \"%s\".", soundFile));
        }
    }
    
    public Sound getSound(String soundFile) {
        if (loadedSounds.containsKey(soundFile))
            return loadedSounds.get(soundFile);
        else
            throw new RuntimeException(String.format("%s hasn't been loaded.", soundFile));
    }
}
