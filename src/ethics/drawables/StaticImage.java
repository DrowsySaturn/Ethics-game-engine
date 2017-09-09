package ethics.drawables;

import ethics.imagetools.ImageLoader;
import java.awt.Image;
import java.io.IOException;

/**
 * Used to show a single frame animation. 
 * @author Jonathan Cooper
 */
public class StaticImage implements Drawable {
    private Image image;
    
    public StaticImage(String fileName) throws IOException {
        this(ImageLoader.loadImage(fileName));
    }
    
    public StaticImage(Image image) {
        this.image = image;
    }
    
    @Override
    public Image getImage() {
        return image;
    }
}
