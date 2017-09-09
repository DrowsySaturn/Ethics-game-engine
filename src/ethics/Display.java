package ethics;

import ethics.events.GameKeyEvent;
import ethics.events.GameListener;
import ethics.events.GameMouseEvent;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Contains all windowing. This class controls how the game should be
 * shown to the user and handles user input. It routes regular swing input
 * through a game listener. This class has built-in double buffering and
 * relies on a Scene object to know what to draw.
 * 
 * @author Jonathan Cooper
 */
public class Display extends Container implements ActionListener, MouseListener, MouseMotionListener, KeyListener {
	private Color DEFAULT_BACKGROUND = new Color(0, 0, 0);
	
    private JFrame jframe;
    private int resolutionX;
    private int resolutionY;
    private Scene scene = null;
	
	/**
	 * Used to manage game repainting at a fairly consistent rate.
	 */
    private Timer refreshTimer;
	
	/**
	 * 
	 */
    private GameListener gameListener = null;
    
	/**
	 * Creates a new display.
	 * 
	 * @param jframe Parent for the frame.
	 * @param width Width of the game view.
	 * @param height Height of the game view.
	 */
    private Display(JFrame jframe, int width, int height) {
        this.jframe = jframe;
        resolutionX = width;
        resolutionY = height;
        setPreferredSize(new Dimension(width, height));
        refreshTimer = new Timer(40, this);
    }
    
	/**
	 * Returns the parent container of this display.
	 * 
	 * @return Parent container.
	 */
    public JFrame getWindow() {
        return jframe;
    }

	/**
	 * Gets the associated scene object for this display.
	 * 
	 * @return Associated scene.
	 */
    public Scene getScene() {
        return scene;
    }

	/**
	 * Sets a new scene for this display.
	 * 
	 * @param scene New scene to associate with this display.
	 */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

	/**
	 * Sets a new game listener for this display.
	 * 
	 * @param gameListener New listener
	 */
    public void setGameListener(GameListener gameListener) {
        this.gameListener = gameListener;
    }
    
	/**
	 * Paints the display using the associated scene.
	 * 
	 * @param g Graphics object to paint to.
	 */
    @Override
    public void paint(Graphics g) {
        BufferedImage buffer = new BufferedImage(resolutionX, resolutionY, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = buffer.getGraphics();
        Color oldColor = graphics.getColor();
		if (scene != null)
			graphics.setColor(scene.getBackgroundColor());
		else
			graphics.setColor(DEFAULT_BACKGROUND);
        graphics.fillRect(0, 0, resolutionX, resolutionY);
        graphics.setColor(oldColor);
        if (scene != null)
            scene.draw(graphics);
        g.drawImage(buffer, 0, 0, getWidth(), getHeight(), null);
    }
    
	/**
	 * Creates a new display. 
	 * 
	 * @param title The window caption for the new window.
	 * @param width The width of the game view.
	 * @param height The height of the game view.
	 * @param fullscreen If the window should be a full screen window.
	 * @return New display
	 */
    public static Display createWindow(String title, int width, int height, boolean fullscreen) {
        Display display;
        JFrame frame = new JFrame();
        frame.setTitle(title);
        display = new Display(frame, width, height);
        frame.setContentPane(display);
        if (fullscreen) {
            frame.setUndecorated(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            frame.pack();
        }
        display.setFocusable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return display;
    }
    
    public void start() {
        if (gameListener != null)
            gameListener.onLoad();
        jframe.setVisible(true);
        requestFocus();
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        refreshTimer.start();
    }
    
	/**
	 * Adjusts the internal timer to the desired FPS.
	 * 
	 * @param fps New FPS
	 */
    public void setFPS(int fps) {
        refreshTimer.setDelay(1000/fps);
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (gameListener != null)
            gameListener.onUpdate();
        jframe.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (gameListener != null)
            gameListener.onMouseInput(GameMouseEvent.createMouseEvent(e.getButton(), true, e.getX(), e.getY()));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (gameListener != null)
            gameListener.onMouseInput(GameMouseEvent.createMouseEvent(e.getButton(), false, e.getX(), e.getY()));
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        if (gameListener != null)
            gameListener.onMouseInput(GameMouseEvent.createMouseMotionEvent(e.getX(), e.getY()));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (gameListener != null)
            gameListener.onMouseInput(GameMouseEvent.createMouseMotionEvent(e.getX(), e.getY()));
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameListener != null)
            gameListener.onKeyInput(new GameKeyEvent(e.getKeyCode(), true));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameListener != null)
            gameListener.onKeyInput(new GameKeyEvent(e.getKeyCode(), false));
    }
}
