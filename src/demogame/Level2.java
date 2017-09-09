package demogame;

import ethics.Entity;
import ethics.Scene;
import ethics.drawables.TextDrawable;
import ethics.events.GameKeyEvent;
import ethics.events.GameMouseEvent;
import demogame.actors.Block;
import demogame.actors.Flag;
import demogame.actors.Spike;
import demogame.actors.SpookyMage;
import java.awt.Color;
import static java.awt.event.KeyEvent.VK_BACK_QUOTE;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The 2nd level in the demo game.
 *
 * @author Jonathan Cooper
 */
public class Level2 extends Level {

	private Scene scene;
	private ArrayList<Entity> groundPieces = new ArrayList<>();
	private ArrayList<Entity> spikes = new ArrayList<>();
	private Flag flag;
	private int movementDirection = 0;
	private SpookyMage player;
	private boolean fell = false;

	public Level2(Scene scene) {
		super(scene);
		this.scene = scene;
	}

	public boolean levelOver() {
		return player.hasReachedFlag();
	}

	@Override
	public boolean gameOver() {
		return fell || player.isDead();
	}

	@Override
	public void onUpdate() {
		if (player.getY() > 9 * 32) {
			fell = true;
		}
		player.move(movementDirection, groundPieces, spikes, flag, false);
	}

	public void onLoad() {
		scene.setBackgroundColor(new Color(50, 50, 50));
		fell = false;
		groundPieces.clear();
		spikes.clear();
		try {
			player = new SpookyMage(32, 64);
			spikes.add(new Spike(32 * 4, 7 * 32));
			flag = new Flag(14 * 32, 4 * 32);
			createBlockGround();
		} catch (IOException ex) {
			System.exit(-1);
		}
		scene.addEntity(spikes.get(0));
		scene.addEntity(flag);
		scene.addEntity(player);
		movementDirection = 0;
		scene.addEntity(new Entity(new TextDrawable("Controls", 12, true), 5, 5, 0, 0));
		scene.addEntity(new Entity(new TextDrawable("left/right arrows = move", 12, true), 5, 17, 0, 0));
		scene.addEntity(new Entity(new TextDrawable("up arrow = jump", 12, true), 5, 29, 5, 0));
		scene.addEntity(new Entity(new TextDrawable("~ = show bounding boxes", 12, true), 5, 41, 0, 0));
	}

	@Override
	public void onMouseInput(GameMouseEvent event) {

	}

	@Override
	public void onKeyInput(GameKeyEvent event) {
		if (event.getKeyCode() == VK_RIGHT) {
			if (event.isDown()) {
				movementDirection = 1;
			} else {
				movementDirection = 0;
			}
		} else if (event.getKeyCode() == VK_LEFT) {
			if (event.isDown()) {
				movementDirection = -1;
			} else {
				movementDirection = 0;
			}
		} else if (event.getKeyCode() == VK_UP) {
			player.jump();
		} else if (event.getKeyCode() == VK_BACK_QUOTE && event.isDown()) {
			scene.toggleShowingBoundingBoxes();
		}
	}

	private void createBlockGround() throws IOException {
		for (int i = 64; i < 15 * 32; i += 32) {
			Block b;
			if (i == 64) {
				b = new Block(i, 7 * 32);
			} else if (i == 96) {
				b = new Block(i, 6 * 32);
			} else if (i == 13 * 32) {
				b = new Block(i, 7 * 32);
			} else if (i == 14 * 32) {
				b = new Block(i, 6 * 32);
			} else {
				b = new Block(i, 8 * 32);
			}
			scene.addEntity(b);
			groundPieces.add(b);
		}
	}

}
