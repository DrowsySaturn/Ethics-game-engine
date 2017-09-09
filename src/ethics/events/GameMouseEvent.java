package ethics.events;

public class GameMouseEvent {
    public static final int EVENT_MOTION = 10;
    public static final int EVENT_MOUSE_DOWN = 20;
    public static final int EVENT_MOUSE_UP = 30;
    
    public static final int BUTTON_NONE = 0;
    public static final int BUTTON_1 = 10;
    public static final int BUTTON_2 = 20;
    public static final int BUTTON_3 = 30;
    
    private int type;
    private int x;
    private int y;
    private int button;
    
    public static GameMouseEvent createMouseMotionEvent(int x, int y) {
        return new GameMouseEvent(EVENT_MOTION, x, y, BUTTON_NONE);
    }
    
    public static GameMouseEvent createMouseEvent(int button, boolean down, int x, int y) {
        int type = down ? EVENT_MOUSE_DOWN : EVENT_MOUSE_UP;
        if (button >= 1 && button <= 3) button *= 10; else button = BUTTON_NONE;
        return new GameMouseEvent(type, x, y, button);
    }

    private GameMouseEvent(int type, int x, int y, int button) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.button = button;
    }

    public int getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getButton() {
        return button;
    }

}
