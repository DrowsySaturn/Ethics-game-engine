package demogame;

import ethics.Display;
import ethics.Entity;
import ethics.Scene;
import ethics.drawables.StaticImage;
import ethics.drawables.TextDrawable;
import ethics.events.GameKeyEvent;
import ethics.events.GameMouseEvent;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * The final win screen for the demo game.
 *
 * @author Jonathan Cooper
 */
public class LevelGameWon extends Level {

	private Scene scene;
	private Display display;
	private boolean over = false;

	public LevelGameWon(Scene scene) {
		super(scene);
		this.scene = scene;
	}

	@Override
	public boolean levelOver() {
		return false;
	}

	@Override
	public boolean gameOver() {
		return over;
	}

	@Override
	public void onUpdate() {

	}

	@Override
	public void onLoad() {
		scene.setBackgroundColor(Color.BLACK);
		try {
			over = false;
			TextDrawable clickAnywhere = new TextDrawable("Click anywhere to restart");
			clickAnywhere.setFont(new Font("Arial", Font.BOLD, 24));
			clickAnywhere.setColor(new Color(255, 100, 100));
			clickAnywhere.setShadowColor(new Color(150, 50, 50));
			clickAnywhere.setShadow(true);
			scene.addEntity(new Entity(new StaticImage("images/screens/won.bmp"), 0, 50, 0, 0));
			scene.addEntity(new Entity(clickAnywhere, 100, 200, 0, 0));
		} catch (IOException ex) {
			Logger.getLogger(LevelGameWon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
			System.exit(-1);
		}
	}

	@Override
	public void onMouseInput(GameMouseEvent event) {
		if (event.getType() == GameMouseEvent.EVENT_MOUSE_DOWN) {
			over = true;
		}
	}

	@Override
	public void onKeyInput(GameKeyEvent event) {

	}
}
