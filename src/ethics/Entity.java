package ethics;

import ethics.drawables.Drawable;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * A screen object. All things in drawn to a display are attached to an Entity
 * as a Drawable. AABB collision is supported by using BoundBox objects
 * internally in each Entity. An entity may be set to non-collidable and make it
 * not have any effect for collisions.
 *
 * @author Jonathan Cooper
 */
public class Entity {

	private double x;
	private double y;
	private int width;
	private int height;
	private Drawable drawable;
	private BoundingBox boundingBox;
	private boolean collidable = true;

	/**
	 * Creates a new entity for use in a scene.
	 *
	 * @param drawable Object the determines how the entity shows on screen.
	 * @param x The starting X coordinate of the entity.
	 * @param y The starting Y coordinate of the entity.
	 * @param width The width of the entity.
	 * @param height The height of the entity.
	 */
	public Entity(Drawable drawable, double x, double y, int width, int height) {
		this.drawable = drawable;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		boundingBox = new BoundingBox((int) x, (int) y, height, width);
	}

	/**
	 * Gets the drawable object of the entity.
	 *
	 * @return Drawable object for the entity.
	 */
	public Drawable getDrawable() {
		return drawable;
	}

	/**
	 * Changes the drawable object of the entity.
	 *
	 * @param drawable New drawable object for the entity.
	 */
	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	/**
	 * Turns on/off collision for the entity. If either entity has collision of,
	 * it is not possible for either entity to collide with the other.
	 *
	 * @param collidable If collision should be on for this entity.
	 */
	public void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}

	/**
	 * Changes the position of the entity.
	 *
	 * @param x The new X coordinate for the entity.
	 * @param y The new Y coordinate for the entity.
	 */
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
		boundingBox.setX((int) x);
		boundingBox.setY((int) y);
	}

	/**
	 * Changes the size of the entity.
	 *
	 * @param width New width of the entity.
	 * @param height New height of the entity.
	 */
	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Returns true if the entity can be detected for collision with another
	 * entity.
	 *
	 * @return True if collision is enabled.
	 */
	public boolean isCollidable() {
		return collidable;
	}

	/**
	 * Overridable method that is invoked before paint is invoked. Can be used
	 * in subclasses for a callback that happens before an entity is drawn.
	 */
	protected void beforeUpdate() {
	}

	/**
	 * Draws the entity to a graphics object.
	 *
	 * @param g Graphics object to draw to.
	 */
	public void draw(Graphics g) {
		beforeUpdate();
		g.drawImage(drawable.getImage(), (int) x, (int) y, null);
	}

	/**
	 * Moves the entity until it collides or reaches its goal.
	 *
	 * @param entities List of entities in a scene
	 * @param dX Change in X
	 * @param dY Change in Y
	 * @return Returns true if it doesn't collide with anything
	 */
	public boolean moveToUntilCollision(ArrayList<Entity> entities, double dX, double dY) {
		double longestDistance = Math.max(Math.abs(dX), Math.abs(dY));
		double fraction = 1.0 / longestDistance;
		double startX = x, startY = y;
		for (float i = 0; i <= 1; i += fraction) {
			if (!setPositionUnlessCollides(entities, startX + dX * i, startY + dY * i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Changes the position of the entity if the new position doesn't cause
	 * collisions.
	 *
	 * @param entities List of entities in a scene
	 * @param x New x
	 * @param y New y
	 * @return Returns true if position was changed and didn't collide with
	 * anything
	 */
	public boolean setPositionUnlessCollides(ArrayList<Entity> entities, double x, double y) {
		double startingX = this.x;
		double startingY = this.y;
		setPosition(x, y);
		if (collidesWithAny(entities)) {
			setPosition(startingX, startingY);
			return false;
		}
		return true;
	}

	/**
	 * Determines if this entity collides with any entity in the supplied array
	 * list. An entity can only collide if both entities overlap and both are
	 * set as collidable.
	 *
	 * @param entities
	 * @return
	 */
	public boolean collidesWithAny(ArrayList<Entity> entities) {
		return entities.stream().anyMatch((e) -> collidesWith(e));
	}

	/**
	 * Determines if this entity collides with a bounding box. This function
	 * ignores if the entity is set as collidable.
	 *
	 * @param other Bounding box to check for collision with.
	 * @return True if the bounding box overlaps with this entity.
	 */
	public boolean collidesWith(BoundingBox other) {
		return boundingBox.collidesWith(other);
	}

	/**
	 * Determines if this entity collides with the supplied entity. An entity
	 * can only collide if both entities overlap and both are set as collidable.
	 * If the entity supplied is the same as this entity, false is returned.
	 *
	 * @param other Entity to check for collision with.
	 * @return True if the entities are different and overlap.
	 */
	public boolean collidesWith(Entity other) {
		if (other == this) {
			return false;
		}
		return this.collidable && other.isCollidable() && other.collidesWith(boundingBox);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
