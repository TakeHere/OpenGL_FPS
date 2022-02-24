package core.scenes;

import java.util.Timer;

public abstract class Scene {

    public static Timer timer = new Timer();

    public abstract void init();

    public abstract void update(double dt);

    public abstract void cleanup();
}
