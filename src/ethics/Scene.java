package ethics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * This class is used by a Display object to draw all entities. A Scene is a
 * wrapper around an array list of entities. A Scene contains the background
 * color for a scene. A Scene can also be used to turn on/off Entity bounding 
 * box lines for the associated display.
 *
 * @author Jonathan Cooper
 */
public class Scene {

	ArrayList<Entity> entities = new ArrayList<>();
	private boolean showingBoundingBoxes = false;
	private Color backgroundColor = Color.LIGHT_GRAY;

	/**
	 * Adds an entity to the scene.
	 *
	 * @param e Entity to add.
	 */
	public void addEntity(Entity e) {
		entities.add(e);
	}

	/**
	 * Removes all entities from the scene.
	 */
	public void clearScene() {
		entities.clear();
	}

	/**
	 * Removes a given entity from the scene.
	 *
	 * @param e Entity to remove.
	 */
	public void removeEntity(Entity e) {
		entities.remove(e);
	}

	/**
	 * Draws this scene to a graphics object. No double buffering is done and it
	 * is recommended that the graphics object supplied is not immediately used
	 * for display to the screen.
	 *
	 * @param gfx Graphics object to paint the scene to.
	 */
	public void draw(Graphics gfx) {
		for (Entity e : entities) {
			e.draw(gfx);
		}
		if (showingBoundingBoxes) {
			Color oldColor = gfx.getColor();
			gfx.setColor(Color.RED);
			for (Entity e : entities) {
				double x = e.getX(), y = e.getY(), width = e.getWidth(), height = e.getHeight();
				if (width == 0 || height == 0) {
					continue;
				}
				gfx.drawRect((int) x, (int) y, (int) width, (int) height);
			}
		}
	}

	/**
	 * Enables or disables bounding boxes.
	 * @param showingBoundingBoxes  If true, bounding boxes are shown.
	 */
	public void setShowingBoundingBoxes(boolean showingBoundingBoxes) {
		this.showingBoundingBoxes = showingBoundingBoxes;
	}

	/**
	 * Toggles bounding boxes.
	 */
	public void toggleShowingBoundingBoxes() {
		setShowingBoundingBoxes(!showingBoundingBoxes);
	}

	/**
	 * Gets the internal array list of entities for the scene.
	 * 
	 * @return Internal ArrayList;
	 */
	public ArrayList<Entity> getEntities() {
		return entities;
	}

	/**
	 * Sets the new background color for the scene.
	 * 
	 * @param backgroundColor New background color.
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * Gets the current background color of the scene.
	 * 
	 * @return Background color
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}
}
