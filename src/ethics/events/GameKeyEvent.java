package ethics.events;

public class GameKeyEvent {
    private int keyCode;
    private boolean down;

    public GameKeyEvent(int keyCode, boolean down) {
        this.keyCode = keyCode;
        this.down = down;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public boolean isDown() {
        return down;
    }
}
