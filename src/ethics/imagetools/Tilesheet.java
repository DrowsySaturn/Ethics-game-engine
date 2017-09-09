package ethics.imagetools;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The purpose of this class is to split up an image into smaller images called
 * tiles.
 *
 * @author Jonathan Cooper
 */
public class Tilesheet {

	private Image source;
	private int tileWidth;
	private int tileHeight;

	public Tilesheet(Image source, int tileWidth, int tileHeight) {
		this.source = source;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}

	public Tilesheet(String source, int tileWidth, int tileHeight) throws IOException {
		this(ImageLoader.loadImage(source), tileWidth, tileHeight);
	}

	/**
	 * Takes a small portion of the source image.
	 *
	 * @param x The X pixel coordinate of the top left of the clipping
	 * @param y The Y pixel coordinate of the top right of the clipping
	 * @param width The width of the clipping
	 * @param height The height of the clipping
	 * @return The clipping
	 */
	public Image clipSource(int x, int y, int width, int height) {
		BufferedImage clipping = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		clipping.getGraphics().drawImage(source, -x, -y, null);
		return clipping;
	}

	/**
	 * Gets a tile in a tile sheet
	 *
	 * @param x The X tile coordinate of the tile
	 * @param y The Y tile coordinate of the tile
	 * @return Clipping generated from the tile
	 */
	public Image getTile(int x, int y) {
		int xPixel = x * tileWidth;
		int yPixel = y * tileHeight;
		return clipSource(xPixel, yPixel, tileWidth, tileHeight);
	}

	/**
	 * Gets the tile from left to right, bottom to top indicies.
	 *
	 * @param index Index of the tile
	 * @return Tile clipping from the source
	 */
	public Image getTile(int index) {
		int tilesWide = source.getWidth(null) / tileWidth;
		int y = index / tilesWide;
		int x = index % tilesWide;
		return getTile(x, y);
	}
}
