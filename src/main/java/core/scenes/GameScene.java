package core.scenes;

import com.sun.tools.javac.Main;
import core.listeners.MouseListener;
import core.objects.EntityCreator;
import core.objects.GameObject;
import core.objects.entities.Camera;
import core.objects.entities.Entity;
import core.objects.entities.Light;
import core.objects.entities.Player;
import core.objects.models.ModelTexture;
import core.objects.models.RawModel;
import core.objects.models.TexturedModel;
import core.objects.models.objloader.OBJFileLoader;
import core.renderers.MasterRenderer;
import core.renderers.debug.DebugSphere;
import core.toolbox.BufferUtil;
import core.toolbox.Loader;
import core.toolbox.Maths;
import org.joml.Vector3f;
import org.lwjgl.util.par.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameScene extends Scene{

    private Camera camera;
    private Light light;

    private Entity displayEntity;
    private Entity groundEntity;
    private Entity axis;

    private Player player;

    private MasterRenderer renderer;
    private Loader loader;

    TexturedModel bulletModel;

    private List<Entity> bullets = new ArrayList<>();

    @Override
    public void init() {
        renderer = new MasterRenderer();
        loader = new Loader();


        axis = EntityCreator.createEntity(loader, "res/models/axis.obj", "res/textures/axis.png"
                ,new Vector3f(0,40,0),new Vector3f(0,0,0),new Vector3f(10,10,10), "axis");

        displayEntity = EntityCreator.createEntity(loader, "res/models/teapot.obj", "res/textures/yellow.png"
                ,new Vector3f(0,10,0),new Vector3f(0,0,0),new Vector3f(10,10,10), "display");
        displayEntity.getModel().getTexture().setShineDamper(10);
        displayEntity.getModel().getTexture().setReflectivity(1);

        groundEntity = EntityCreator.createEntity(loader, "res/models/floor.obj", "res/textures/grass.jpg"
                ,new Vector3f(0,1,0), new Vector3f(0,0,0), new Vector3f(100,5,100), "ground");

        player = new Player(new TexturedModel(
                OBJFileLoader.loadOBJ("res/models/teapot.obj", loader),
                new ModelTexture(loader.loadTexture("res/textures/yellow.png"))),
                new Vector3f(0,1,0),
                new Vector3f(0,0,0),
                new Vector3f(10,10,10));

        RawModel rawModelBullet = OBJFileLoader.loadOBJ("res/models/debugSphere.obj", loader);
        ModelTexture bulletTexture = new ModelTexture(loader.loadTexture("res/textures/blue.png"));
        bulletTexture.setUseFakeLighting(true);
        bulletTexture.setHasTransparency(true);
        bulletModel = new TexturedModel(rawModelBullet, bulletTexture);

        camera = new Camera();
        camera.setPosition(new Vector3f(0,15,15));
        light = new Light(new Vector3f(100,100,100), new Color(255, 255, 255));


        MouseListener.addPressEvent(0, () -> {
            Entity bulletEntity = new Entity(bulletModel, camera.getPosition(), new Vector3f(0,0,0), new Vector3f(10,10,10), "bullet");
            bulletEntity.velocity = Maths.forwardVector(new Vector3f(camera.getPitch(), camera.getYaw(), camera.getRoll())).mul(1);

            bullets.add(bulletEntity);
        });
    }

    @Override
    public void update(double dt) {
        //MasterRenderer.addDebugSphere(new DebugSphere(Maths.forwardVector(player.getRotation()).add(player.getPosition()), 1, 0));

        displayEntity.addRotation(new Vector3f(0,1,0));
        displayEntity.addPosition(new Vector3f(0, (float) Math.cos(Math.toRadians(System.currentTimeMillis()) / 5) / 10,0));

        player.move();
        camera.rotate(player);

        camera.setPosition(new Vector3f(player.getPosition().x + 0, player.getPosition().y + 10, player.getPosition().z + 0));

        renderer.processEntity(player);
        renderer.processEntity(axis);
        renderer.processEntity(groundEntity);
        renderer.processEntity(displayEntity);

        for (Entity bullet : bullets) {
            renderer.processEntity(bullet);
        }

        renderer.render(light, camera);
    }

    @Override
    public void cleanup() {
        loader.cleanup();
        renderer.cleanup();

        Entity.entities.clear();
        GameObject.gameObjects.clear();
    }
}
