package ethics.drawables;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

/**
 * Supports text as a drawable. Supports various fonts from java.awt.Font
 * (Family, size, color, decoration). Supports 1 px shadows with customizable
 * color.
 *
 * @author Jonathan Cooper
 */
public class TextDrawable implements Drawable {

	public static Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 16);

	private Font font = DEFAULT_FONT;
	private String text;
	private Color color = new Color(255, 255, 255);
	private Color shadowColor = new Color(0, 0, 0);
	private BufferedImage buffer = null;
	private boolean shadow = false;

	public TextDrawable(String text) {
		this.text = text;
	}

	public TextDrawable(String text, boolean shadow) {
		this(text);
		this.shadow = true;
	}

	public TextDrawable(String text, int fontSize, boolean fontBold) {
		this(text);
		font = new Font(DEFAULT_FONT.getFamily(), Font.BOLD, fontSize);
	}

	/**
	 * Changes font. Call updateBuffer() for getImage() to change.
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * Changes shadow color. Call updateBuffer() for getImage() to change.
	 */
	public void setShadowColor(Color shadowColor) {
		this.shadowColor = shadowColor;
	}

	/**
	 * Changes color. Call updateBuffer() for getImage() to change.
	 * @param color New color of the text.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Changes text. Call updateBuffer() for getImage() to change.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Enables/disables shadows. Call updateBuffer() for getImage() to change.
	 */
	public void setShadow(boolean shadow) {
		this.shadow = shadow;
	}

	/**
	 * Gets the image of the pre-generated text sprite.
	 */
	@Override
	public Image getImage() {
		if (buffer == null) {
			updateBuffer();
		}
		return buffer;
	}

	/**
	 * Updates the internal image supplied when getImage() is called. The
	 * internal image must be updated if any property is changed. This method is
	 * invoked at the first drawing, any property changes after the initial
	 * drawing must be updated using this method.
	 */
	public void updateBuffer() {
		FontMetrics metrics = getFontMetrics();
		int width = metrics.stringWidth(text);
		int height = metrics.getHeight();
		if (shadow) {
			++width;
			++height;
		}
		int ascent = metrics.getAscent();
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) buffer.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(font);
		if (shadow) {
			g.setColor(shadowColor);
			g.drawString(text, 1, ascent + 1);
		}
		g.setColor(color);
		g.drawString(text, 0, ascent);
	}

	/**
	 * Gets the FontMetrics associated with the current font.
	 *
	 * @return FontMetrics associated with the current font.
	 */
	private FontMetrics getFontMetrics() {
		Graphics g = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();
		g.setFont(font);
		return g.getFontMetrics();
	}
}
