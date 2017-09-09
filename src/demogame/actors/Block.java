package demogame.actors;


import ethics.Entity;
import ethics.drawables.AnimatedSprite;
import ethics.drawables.Drawable;
import java.io.IOException;

/**
 * Static sprite used to be walked on for the demo game.
 * 
 * @author Jonathan Cooper
 */
public class Block extends Entity {
	public Block(double x, double y) throws IOException {
		super(null, x, y, 32, 32);
		super.setDrawable(createDrawable());
	}

	private Drawable createDrawable() throws IOException {
		AnimatedSprite sprite = new AnimatedSprite("images/ground/tiles.png", 32, 32);
		sprite.addAnimation("", new int[]{0});
		sprite.playAnimation("");
		return sprite;
	}
}
