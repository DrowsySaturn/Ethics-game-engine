package demogame.actors;

import ethics.Entity;
import ethics.drawables.AnimatedSprite;
import ethics.drawables.Drawable;
import java.io.IOException;

/**
 * Sprite used as the goal of a level in the demo game.
 *
 * @author Jonathan Cooper
 */
public class Flag extends Entity {

	private boolean fallen = false;

	public Flag(double x, double y) throws IOException {
		super(null, x, y, 32, 64);
		setDrawable(createDrawable());
	}

	public void fall() {
		if (!fallen) {
			fallen = true;
			((AnimatedSprite) getDrawable()).playAnimationFromStart("falling");
		}
	}

	private Drawable createDrawable() throws IOException {
		AnimatedSprite sprite = new AnimatedSprite("images/random/flag.png", 32, 64);
		sprite.setFrameLength(3);
		sprite.addAnimation("static", new int[]{0});
		sprite.addAnimation("falling", new int[]{1, 2, 3, 4, -1});
		sprite.playAnimation("static");
		return sprite;
	}
}
