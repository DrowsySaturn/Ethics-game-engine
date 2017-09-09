package demogame.actors;

import ethics.Entity;
import ethics.drawables.AnimatedSprite;
import ethics.drawables.Drawable;
import java.io.IOException;

/**
 * A spike to be avoided in the demo game.
 * 
 * @author Jonathan Cooper
 */
public class Spike extends Entity {
	boolean wasBloodied = false;
	
	public Spike(double x, double y) throws IOException {
		super(null, x, y, 32, 32);
		super.setDrawable(createDrawable());
	}
	
	public void setBloodied(boolean bloodied) {
		if (wasBloodied != bloodied) {
			((AnimatedSprite)getDrawable()).playAnimationFromStart(bloodied ? "blood" : "no_blood");
			wasBloodied = bloodied;
		}
	}
	
	private Drawable createDrawable() throws IOException {
		AnimatedSprite sprite = new AnimatedSprite("images/ground/tiles.png", 32, 32);
		sprite.addAnimation("no_blood", new int[]{4});
		sprite.addAnimation("blood", new int[]{5, 6, 7, -1});
		sprite.playAnimation("no_blood");
		sprite.setFrameLength(3);
		return sprite;
	}
	
}
