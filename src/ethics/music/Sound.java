package ethics.music;

public abstract class Sound {
    public abstract void play();
    public abstract void stop();
    public abstract void restart();
    public abstract void loop();
    public abstract void loop(int numberOfTimes);
    public abstract boolean finished();
}
