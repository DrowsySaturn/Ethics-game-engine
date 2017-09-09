package demogame;

import ethics.Display;
import ethics.Scene;
import ethics.events.GameKeyEvent;
import ethics.events.GameListener;
import ethics.events.GameMouseEvent;
import java.awt.Color;
import java.io.IOException;

/**
 * The main class for the demo game.
 *
 * @author Jonathan Cooper
 */
public class InterestingPlatformer implements GameListener {

	private Display display;
	private Scene scene;
	private Level currentLevel;
	private int levelIndex = 0;
	private Level[] levels = new Level[3];

	@Override
	public void onUpdate() {
		currentLevel.onUpdate();
		if (currentLevel.gameOver()) {
			nextLevel(0);
		} else if (currentLevel.levelOver()) {
			nextLevel();
		}
	}

	@Override
	public void onLoad() {
		nextLevel();
	}

	@Override
	public void onMouseInput(GameMouseEvent event) {
		currentLevel.onMouseInput(event);
	}

	@Override
	public void onKeyInput(GameKeyEvent event) {
		currentLevel.onKeyInput(event);
	}

	public void start() {
		scene = new Scene();
		scene.setBackgroundColor(Color.LIGHT_GRAY);
		display = Display.createWindow("Interesting platformer", 16 * 32, 9 * 32, false);
		display.setGameListener(this);
		display.setScene(scene);
		loadLevels();
		display.start();
	}

	public static void main(String[] args) throws IOException {
		new InterestingPlatformer().start();
	}

	private void nextLevel(int index) {
		scene.setBackgroundColor(Color.LIGHT_GRAY);
		if (index < levels.length && levels[index] != null) {
			scene.clearScene();
			currentLevel = levels[index];
			currentLevel.onLoad();
			levelIndex = index + 1;
		} else {
			throw new IllegalArgumentException("Invalid level");
		}
	}

	private void nextLevel() {
		nextLevel(levelIndex);
	}

	private void loadLevels() {
		levels[0] = new Level1(scene);
		levels[1] = new Level2(scene);
		levels[2] = new LevelGameWon(scene);
	}
}
