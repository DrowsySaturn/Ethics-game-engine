package ethics.events;

public interface GameListener {
    public void onUpdate();
    public void onLoad();
    public void onMouseInput(GameMouseEvent event);
    public void onKeyInput(GameKeyEvent event);
}
