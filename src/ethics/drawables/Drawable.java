package ethics.drawables;

import java.awt.Image;

/**
 * Implemented by all drawable objects. The image supplied by getImage()
 * will be drawn to the screen. The position and when this is displayed is 
 * managed by the Scene and Entity objects.
 * 
 * @author Jonathan Cooper
 */
public interface Drawable {
	/**
	 * Image to draw to the screen.
	 * @return Image to show on the screen.
	 */
    public Image getImage();
}
