package demogame;

import ethics.Display;
import ethics.Scene;
import ethics.events.GameListener;

/**
 * The layout for a level in the demo game. 
 * @author Jonathan Cooper
 */
public abstract class Level implements GameListener {
	public Level(Scene scene) {}
	public Level(Display display, Scene scene) {this(scene);}
    public abstract boolean levelOver();
    public abstract boolean gameOver();
}
