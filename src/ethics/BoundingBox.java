package ethics;

/**
 * Class for doing axis-aligned bounding box collision logic.
 * @author Jonathan Cooper
 */
public class BoundingBox {
	private int x;
	private int y;
	private int width;
	private int height;

	/**
	 * Creates a new bounding box.
	 * 
	 * @param x X coordinate of the top left corner of the box
	 * @param y Y coordinate of the top left corner of the box
	 * @param width Width of the box
	 * @param height Height of the box
	 */
	public BoundingBox(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Axis aligned bounding box collision check.
	 *
	 * @param other Bounding box to check against
	 * @return True if the boxes collide
	 */
	public boolean collidesWith(BoundingBox other) {
		return (this.x < other.getX() + other.getWidth()
				&& other.getX() < this.x + this.width
				&& this.y < other.getY() + other.getHeight()
				&& other.getY() < this.y + this.height);
	}

	/**
	 * Gets the X coordinate of the top left of the box.
	 * 
	 * @return X coordinate of the top left of the box.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the X coordinate of the top left of the box.
	 * 
	 * @param x X coordinate of the top left of the box.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the Y coordinate of the top left of the box.
	 * 
	 * @return Y coordinate of the top left of the box.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the Y coordinate of the top left of the box.
	 * 
	 * @param y Y coordinate of the top left of the box.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Gets the width of the box.
	 * 
	 * @return Width of the box.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width of the box.
	 * 
	 * @param width Width of the box.
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Gets the height of the box.
	 * 
	 * @return Height of the box.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height of the box.
	 * 
	 * @param height New height of the box.
	 */
	public void setHeight(int height) {
		this.height = height;
	}

}
