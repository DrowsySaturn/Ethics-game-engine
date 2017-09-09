package demogame.actors;

import ethics.Entity;
import ethics.drawables.AnimatedSprite;
import ethics.drawables.Drawable;
import java.io.IOException;

/**
 * A ground piece for use in the demo game.
 *
 * @author Jonathan Cooper
 */
public class GrassTile extends Entity {

	private static int creationCounter = 0;

	public GrassTile(double x, double y) throws IOException {
		super(null, x, y, 32, 32);
		super.setDrawable(createDrawable());
	}

	private Drawable createDrawable() throws IOException {
		AnimatedSprite sprite = new AnimatedSprite("images/ground/tiles.png", 32, 32);
		sprite.addAnimation("ground_normal", new int[]{2});
		sprite.addAnimation("ground_weird", new int[]{3});
		sprite.playAnimation(creationCounter++ % 3 == 0 ? "ground_weird" : "ground_normal");
		return sprite;
	}
}
