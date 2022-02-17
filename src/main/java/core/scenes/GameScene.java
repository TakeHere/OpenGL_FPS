package core.scenes;

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
import core.toolbox.Loader;
import core.toolbox.Maths;
import org.joml.Vector3f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameScene extends Scene{

    private Camera camera;
    private Light sun;
    private List<Light> lights = new ArrayList<>();

    private Entity displayEntity;
    private Player player;

    private MasterRenderer renderer;
    private Loader loader;

    TexturedModel bulletModel;

    @Override
    public void init() {
        loader = new Loader();


        EntityCreator.createEntity(loader, "res/models/axis.obj", "res/textures/axis.png"
                ,new Vector3f(0,40,0),new Vector3f(0,0,0),new Vector3f(10,10,10), "axis");

        displayEntity = EntityCreator.createEntity(loader, "res/models/debugSphere.obj", "res/textures/red.png"
                ,new Vector3f(0,10,0),new Vector3f(0,0,0),new Vector3f(10,10,10), "display");
        displayEntity.getModel().getTexture().setShineDamper(10);
        displayEntity.getModel().getTexture().setReflectivity(1);

         EntityCreator.createEntity(loader, "res/models/floor.obj", "res/textures/grass.jpg"
                ,new Vector3f(0,1,0), new Vector3f(0,0,0), new Vector3f(100,5,100), "ground");

        player = new Player(new TexturedModel(
                OBJFileLoader.loadOBJ("res/models/teapot.obj", loader),
                new ModelTexture(loader.loadTexture("res/textures/yellow.png"))),
                new Vector3f(0,1,0),
                new Vector3f(0,0,0),
                new Vector3f(10,10,10));

        RawModel rawModelBullet = OBJFileLoader.loadOBJ("res/models/debugSphere.obj", loader);
        ModelTexture bulletTexture = new ModelTexture(loader.loadTexture("res/textures/blue.png"));
        bulletModel = new TexturedModel(rawModelBullet, bulletTexture);

        camera = new Camera();
        camera.setPosition(new Vector3f(0,15,15));

        //sun = new Light(new Vector3f(1000000,1000000,-1000000), new Color(255, 255, 255), "sun");
        sun = new Light(new Vector3f(100,100,100), new Color(255, 255, 255), "sun");
        lights.add(sun);
        lights.add(new Light(new Vector3f(-50,50,-50), new Color(222, 6, 47), new Vector3f(1,0.01f, 0.002f), "decoLight"));


        MouseListener.addPressEvent(0, () -> {
            Entity bulletEntity = new Entity(bulletModel, new Vector3f(camera.getPosition().x, camera.getPosition().y, camera.getPosition().z), new Vector3f(0,0,0), new Vector3f(10,10,10), "bullet");
            bulletEntity.velocity = Maths.forwardVector(new Vector3f(camera.getPitch(), camera.getYaw(), camera.getRoll())).mul(5);
            bulletEntity.useGravity = true;
        });

        renderer = new MasterRenderer(camera);
    }

    @Override
    public void update(double dt) {
        displayEntity.addRotation(new Vector3f(0,1,0));
        displayEntity.addPosition(new Vector3f(0, (float) Math.cos(Math.toRadians(System.currentTimeMillis()) / 5) / 10,0));

        player.move();
        camera.move(player);

        renderer.renderShadowMap(Entity.entities, sun);

        for (Entity entity : Entity.entities) {
            renderer.processEntity(entity);
        }

        renderer.render(lights, camera);
    }

    @Override
    public void cleanup() {
        loader.cleanup();
        renderer.cleanup();

        Entity.entities.clear();
        GameObject.gameObjects.clear();
    }
}
