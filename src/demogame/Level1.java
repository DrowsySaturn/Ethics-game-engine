package demogame;

import ethics.Entity;
import ethics.Scene;
import ethics.drawables.TextDrawable;
import ethics.events.GameKeyEvent;
import ethics.events.GameMouseEvent;
import demogame.actors.Flag;
import demogame.actors.Spike;
import demogame.actors.SpookyMage;
import java.io.IOException;
import java.util.ArrayList;
import static java.awt.event.KeyEvent.*;

/**
 * The 1st level in the demo game.
 *
 * @author Jonathan Cooper
 */
public class Level1 extends Level {

	private Scene scene;
	private ArrayList<Entity> groundPieces = new ArrayList<>();
	private ArrayList<Entity> spikes = new ArrayList<>();
	private Flag flag;
	private int movementDirection = 0;
	private SpookyMage player;

	public Level1(Scene scene) {
		super(scene);
		this.scene = scene;
	}

	@Override
	public boolean levelOver() {
		return player.hasReachedFlag();
	}

	@Override
	public boolean gameOver() {
		return player.isDead() || player.getY() > 9 * 32;
	}

	@Override
	public void onUpdate() {
		try {
			player.generateGround(groundPieces, scene, 8 * 32);
		} catch (IOException ex) {
			System.exit(-1);
		}
		player.move(movementDirection, groundPieces, spikes, flag, true);
	}

	@Override
	public void onLoad() {
		try {
			groundPieces.clear();
			spikes.clear();
			player = new SpookyMage(128, 64);
			spikes.add(new Spike(32 * 20, 7 * 32));
			flag = new Flag(32 * 35, 6 * 32);
		} catch (IOException ex) {
			System.exit(-1);
		}
		scene.addEntity(spikes.get(0));
		scene.addEntity(flag);
		movementDirection = 0;
		scene.addEntity(new Entity(new TextDrawable("Controls", 12, true), 5, 5, 0, 0));
		scene.addEntity(new Entity(new TextDrawable("left/right arrows = move", 12, true), 5, 17, 0, 0));
		scene.addEntity(new Entity(new TextDrawable("up arrow = jump", 12, true), 5, 29, 5, 0));
		scene.addEntity(new Entity(new TextDrawable("~ = show bounding boxes", 12, true), 5, 41, 0, 0));
		Entity demoNotice = new Entity(new TextDrawable("This game is a demo for the Ethics Engine", true), 20, 90, 0, 0);
		scene.addEntity(demoNotice);
		scene.addEntity(player);
		groundPieces.add(demoNotice);
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

}
