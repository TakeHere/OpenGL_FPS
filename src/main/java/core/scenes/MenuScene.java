package core.scenes;

import core.Consts;
import core.Window;
import core.listeners.KeyListener;
import core.objects.EntityCreator;
import core.objects.GameObject;
import core.objects.entities.Camera;
import core.objects.entities.Entity;
import core.objects.entities.Light;
import core.renderers.MasterRenderer;
import core.sound.AudioMaster;
import core.sound.AudioSource;
import core.toolbox.Loader;
import core.toolbox.Vector3;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.Arrays;

public class MenuScene extends Scene{

    private Camera camera;
    private Light sun;
    private MasterRenderer renderer;
    private Loader loader;

    @Override
    public void init() {
        loader = new Loader();
        camera = new Camera();
        camera.setPosition(new Vector3(0,25,25));
        renderer = new MasterRenderer(camera);

        AudioSource ambientSource = new AudioSource();
        ambientSource.setLooping(true);
        ambientSource.setVolume(0.4f);
        ambientSource.play(Consts.MENU_MUSIC);


        EntityCreator.createEntity(loader, "res/models/title.obj", "res/textures/yellow.png"
                ,new Vector3(-2,26.5f,20),new Vector3(0,-75,0),new Vector3(1,1,1), "title");

        EntityCreator.createEntity(loader, "res/models/gun.obj", "res/textures/gunTexture.png"
                ,new Vector3(4.5f,25,20),new Vector3(-10,-75,1),new Vector3(1,1,1), "gun");

        EntityCreator.createEntity(loader, "res/models/floor.obj", "res/textures/wood.jpg"
                ,new Vector3(0,1,0), new Vector3(0,0,0), new Vector3(80,5,30), "ground");

        sun = new Light(new Vector3(100,100,100), new Color(255, 255, 255), "sun");
    }

    @Override
    public void update(double dt) {
        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_SPACE)){
            Window.changeScene(1);
        }

        for (Entity entity : Entity.entities) {
            renderer.processEntity(entity);
        }

        renderer.render(Arrays.asList(sun), camera, true);
    }

    @Override
    public void cleanup() {
        loader.cleanup();
        renderer.cleanup();
        AudioMaster.deleteAllSources();

        Entity.entities.clear();
        GameObject.gameObjects.clear();
    }
}
