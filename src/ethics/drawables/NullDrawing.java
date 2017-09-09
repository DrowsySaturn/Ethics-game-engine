package ethics.drawables;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * A Drawing that doesn't draw anything. Used for Entities that shouldn't be
 * drawn.
 *
 * @author Jonathan Cooper
 */
public class NullDrawing implements Drawable {

	private static BufferedImage buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

	public Image getImage() {
		return buffer;
	}
}
