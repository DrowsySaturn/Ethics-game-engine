package ethics.imagetools;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 * The purpose of this class is to just cache images loaded.
 * 
 * @author Jonathan Cooper
 */
public class ImageLoader {
    private static HashMap<String, Image> loadedImages = new HashMap<String, Image>();
    
    public static Image loadImage(String path) throws IOException {
        if (!loadedImages.containsKey(path)){
            loadedImages.put(path, ImageIO.read(new File(path)));
            return loadImage(path);
        }
        return loadedImages.get(path);
    }
}
