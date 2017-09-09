package ethics.drawables;

import ethics.imagetools.Tilesheet;
import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;

/**
 * Supports sprite sheet animations. If a sprite is contained in more than a
 * single image, ethics.imagetools.TilesheetGenerator may be used to create a
 * single image at run time.
 *
 * @author Jonathan Cooper
 */
public class AnimatedSprite implements Drawable {

	private int frameLength = 1;
	private int tileWidth;
	private int tileHeight;
	private Tilesheet sheet;
	private HashMap<String, int[]> animations = new HashMap<>();
	private int frameCounter = 0;
	private String currentAnimation = null;

	/**
	 * Creates a new animated sprite. The supplied source image must have no
	 * gaps or else it will be included in the drawn sprite.
	 *
	 * @param sourceFile Path to the source image to splice up into some
	 * animations.
	 * @param tileWidth Width of each tile to split up.
	 * @param tileHeight Height of each tile to split up.
	 * @throws IOException If the image at the specified path doesn't exist or
	 * fails to read.
	 */
	public AnimatedSprite(String sourceFile, int tileWidth, int tileHeight) throws IOException {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		sheet = new Tilesheet(sourceFile, tileWidth, tileHeight);
	}

	/**
	 * Creates a new animated sprite. The supplied source image must have no
	 * gaps or else it will be included in the drawn sprite.
	 *
	 * @param sourceFile Source image to splice up into some animations.
	 * @param tileWidth Width of each tile to split up.
	 * @param tileHeight Height of each tile to split up.
	 */
	public AnimatedSprite(Image sourceFile, int tileWidth, int tileHeight) {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		sheet = new Tilesheet(sourceFile, tileWidth, tileHeight);
	}

	/**
	 * Creates a new animation that can be played. The animation should be
	 * indexes of the tiles in the source sprite sheet. The image is indexed
	 * from left to right, and then top to down. An image might be indexed as
	 * follows:
	 * <table>
	 * <tr><td>0</td><td>1</td><td>2</td></tr>
	 * <tr><td>3</td><td>4</td><td>5</td></tr>
	 * <tr><td>6</td><td>7</td><td>8</td></tr>
	 * <tr><td>9</td><td>10</td><td>11</td></tr>
	 * </table>
	 *
	 * @param name Name to save the animation as.
	 * @param animation Array of indices containing the animation.
	 */
	public void addAnimation(String name, int[] animation) {
		animations.put(name, animation);
	}

	/**
	 * Changes the current animation while maintaining the frame counter.
	 *
	 * @param name New animation to play.
	 */
	public void playAnimation(String name) {
		currentAnimation = name;
	}

	/**
	 * Changes the current animation and resets the frame counter.
	 *
	 * @param name New animation to play.
	 */
	public void playAnimationFromStart(String name) {
		currentAnimation = name;
		frameCounter = 0;
	}

	/**
	 * How many frames before going to the next tile in the sprite sheet.
	 *
	 * @param frameLength Number of frames before switching images.
	 */
	public void setFrameLength(int frameLength) {
		this.frameLength = frameLength;
	}

	/**
	 * Gets the next frame to be drawn to the scene.
	 *
	 * @return Frame
	 */
	@Override
	public Image getImage() {
		if (currentAnimation == null) {
			throw new RuntimeException("No animation selected but there was an attempt to draw.");
		}
		int frameIndex = (frameCounter++) / frameLength;
		int[] animation = animations.get(currentAnimation);
		int animationTileIndex = animation[frameIndex % animation.length];
		if (animationTileIndex < 0) {
			frameCounter += animationTileIndex - 1;
			return getImage();
		}
		return sheet.getTile(animationTileIndex);
	}
}
