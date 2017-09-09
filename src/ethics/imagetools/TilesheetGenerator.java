package ethics.imagetools;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The purpose of this class is to generate a new tile sheet from smaller
 * sprites/tiles.
 * @author Jonathan Cooper
 */
public class TilesheetGenerator {
	private int tileWidth;
	private int tileHeight;
	private ArrayList<Image> tiles = new ArrayList<>();

	public TilesheetGenerator(int tileWidth, int tileHeight) {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}
	
	public void addFromTilesheet(Tilesheet sheet, int tileX, int tileY) {
		addFromImage(sheet.getTile(tileX, tileY));
	}
	
	public void addFromTilesheet(Tilesheet sheet, int tileIndex) {
		addFromImage(sheet.getTile(tileIndex));
	}
	
	public void addFromTilesheet(Image image, int tileX, int tileY) {
		addFromTilesheet(new Tilesheet(image, tileWidth, tileHeight), tileX, tileY);
	}
	
	public void addFromTilesheet(String source, int tileX, int tileY) throws IOException {
		addFromTilesheet(new Tilesheet(source, tileWidth, tileHeight), tileX, tileY);
	}
	
	public void addFromTilesheet(Image source, int tileIndex) {
		addFromTilesheet(new Tilesheet(source, tileWidth, tileHeight), tileIndex);
	}
	
	/**
	 * From a tile sheet a tile is added to the generator.
	 * @param source Path to the image file to load as a tile sheet.
	 * @param tileIndex Index of the image in the tile sheet.
	 * @throws IOException If the file is unable to be loaded.
	 */
	public void addFromTilesheet(String source, int tileIndex) throws IOException {
		addFromTilesheet(new Tilesheet(source, tileWidth, tileHeight), tileIndex);
	}
	
	/**
	 * Adds an image to the generator. This will be used by TilesheetGenerator.generateImage.
	 * @param image Image to add
	 * @throws IllegalArgumentException If the image is not the same size as the previously specified tileWidth and tileHeight
	 */
	public void addFromImage(Image image) {
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		if ((width >= 0 && height >= 0) && (tileWidth != width || tileHeight != height)) {
			throw new IllegalArgumentException("Bad image dimensions");
		}
		tiles.add(image);
	}
	
	/**
	 * Generates an image from previously added images. Image is tileWidth*imageCount x tileHeight in size.
	 * Images are added from left to right in order they were added. If you added A, B, C to the generated then
	 * called this method, the result would be A B C as an image.
	 * @return Generated image
	 */
	public Image generateImage() {
		BufferedImage result = new BufferedImage(tiles.size()*tileWidth, tileHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = result.getGraphics();
		for (int i = 0; i < tiles.size(); ++i) {
			graphics.drawImage(tiles.get(i), i*tileWidth, 0, null);
		}
		return result;
	}
}
