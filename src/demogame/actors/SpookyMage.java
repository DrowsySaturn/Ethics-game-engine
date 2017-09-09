package demogame.actors;

import ethics.Entity;
import ethics.Scene;
import ethics.drawables.AnimatedSprite;
import ethics.drawables.Drawable;
import ethics.imagetools.TilesheetGenerator;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A protagonist in the demo game. The image used for this class was created by
 * Stephen Challener (Redshrike) and Zi Ye, hosted by OpenGameArt.org. The
 * source code for this class and game engine was written by Jonathan Cooper.
 * All art aside from the mage image was created by Jonathan Cooper.
 *
 * @author Jonathan Cooper
 */
public class SpookyMage extends Entity {

	private static final int MOVE_SPEED = 8;
	private static final int GRAVITY_INFLUENCE = 10;
	private static final int JUMP_POWER = 21;

	private ArrayList<Entity> groundTiles = new ArrayList<>();
	private ArrayList<Entity> spikes = new ArrayList<>();
	private boolean won = false;
	private boolean dead = false;
	private int deadFrames = 0;
	private double jumpPower = 0;
	private boolean doneJumping = true;
	private int lastDirection = 1;
	private boolean busy = false;
	private int wonFrames = 0;

	public SpookyMage(double x, double y) throws IOException {
		super(null, x, y, 64, 64);
		setDrawable(createDrawable());
	}

	public boolean isDead() {
		return dead && deadFrames > 20;
	}

	public void jump() {
		if (doneJumping) {
			jumpPower = JUMP_POWER;
			doneJumping = false;
		}
	}

	public void move(int direction, ArrayList<Entity> groundPieces, ArrayList<Entity> spikes, Entity flag, boolean moveScene) {
		if (dead) {
			++deadFrames;
			return;
		}
		if (won) {
			++wonFrames;
		}
		checkSpikeCollision(spikes);
		checkFlagCollision(flag, moveScene, direction);
		gravity(groundPieces);
		if (direction == 0) {
			if (lastDirection == 1) {
				((AnimatedSprite) getDrawable()).playAnimation("facing_right");
			} else if (lastDirection == -1) {
				((AnimatedSprite) getDrawable()).playAnimation("facing_left");
			}
		} else {
			if (lastDirection != direction) {
				((AnimatedSprite) getDrawable()).playAnimation(direction == 1 ? "walking_right" : "walking_left");
			}
			if (moveScene) {
				moveSceneObjects(groundPieces, -direction * MOVE_SPEED);
				moveSceneObjects(spikes, -direction * MOVE_SPEED);
			} else {
				moveToUntilCollision(groundPieces, direction * MOVE_SPEED, 0);
			}
		}
		lastDirection = direction;
	}

	public boolean hasReachedFlag() {
		return won && wonFrames > 40;
	}

	public void generateGround(ArrayList<Entity> groundPieces, Scene scene, int y) throws IOException {
		double maxX = -32;
		for (Entity groundPiece : groundPieces) {
			maxX = Math.max(maxX, groundPiece.getX());
		}
		while (maxX < 16 * 32) {
			maxX += 32;
			GrassTile temp = new GrassTile(maxX, y);
			groundPieces.add(temp);
			scene.addEntity(temp);
		}
	}

	private void gravity(ArrayList<Entity> groundPieces) {
		if (!super.moveToUntilCollision(groundPieces, 0, GRAVITY_INFLUENCE - jumpPower)) {
			doneJumping = true;
		}
		if (jumpPower > 0.1) {
			--jumpPower;
		}
	}

	private void moveSceneObjects(ArrayList<Entity> entities, double offsetX) {
		for (Entity e : entities) {
			e.setPosition(e.getX() + offsetX, e.getY());
		}
	}

	private void checkSpikeCollision(ArrayList<Entity> spikes) {
		for (Entity spike : spikes) {
			if (collidesWith(spike)) {
				dead = true;
				((AnimatedSprite) getDrawable()).playAnimationFromStart("dieing");
				((Spike) spike).setBloodied(true);
			}
		}
	}

	private void checkFlagCollision(Entity flag, boolean movesScene, int direction) {
		if (flag != null) {
			if (movesScene) {
				flag.setPosition(flag.getX() - direction * MOVE_SPEED, flag.getY());
			}
			if (flag.collidesWith(this)) {
				won = true;
				((Flag) flag).fall();
			}
		}
	}

	private Drawable createDrawable() throws IOException {
		TilesheetGenerator generator = new TilesheetGenerator(64, 64);
		for (int i = 9; i < 18; ++i) {
			generator.addFromTilesheet("images/mage/mage_walking.png", i);
		}
		for (int i = 27; i < 36; ++i) {
			generator.addFromTilesheet("images/mage/mage_walking.png", i);
		}
		for (int i = 27; i < 36; ++i) {
			generator.addFromTilesheet("images/mage/mage_casting.png", i);
		}
		for (int i = 0; i < 6; ++i) {
			generator.addFromTilesheet("images/mage/mage_falling.png", i);
		}
		AnimatedSprite sprite = new AnimatedSprite(generator.generateImage(), 64, 64);
		sprite.addAnimation("walking_left", new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8});
		sprite.addAnimation("walking_right", new int[]{9, 10, 11, 12, 13, 14, 15, 16, 17});
		sprite.addAnimation("facing_right", new int[]{9});
		sprite.addAnimation("facing_left", new int[]{0});
		sprite.addAnimation("casting", new int[]{18, 19, 20, 21, 22, 23, 24, 25, 26, -1});
		sprite.addAnimation("dieing", new int[]{27, 28, 29, 30, 31, 32, -1});
		sprite.playAnimation("facing_right");
		return sprite;
	}
}
