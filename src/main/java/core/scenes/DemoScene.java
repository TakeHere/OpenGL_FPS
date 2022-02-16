package core.scenes;

import core.objects.EntityCreator;
import core.objects.GameObject;
import core.objects.entities.Camera;
import core.objects.entities.Entity;
import core.objects.entities.Light;
import core.renderers.MasterRenderer;
import core.toolbox.Loader;
import core.toolbox.Maths;
import org.joml.Vector3f;

import java.awt.*;

public class DemoScene extends Scene{

    private Entity displayEntity;
    private Entity groundEntity;
    private Entity lightEntity;

    private Camera camera;
    private Light light;

    private MasterRenderer renderer;
    private Loader loader;

    @Override
    public void init() {

        renderer = new MasterRenderer();
        loader = new Loader();


        displayEntity = EntityCreator.createEntity(loader, "res/models/teapot.obj", "res/textures/yellow.png"
                ,new Vector3f(0,10,0),new Vector3f(0,0,0),new Vector3f(10,10,10), "display");
        displayEntity.getModel().getTexture().setShineDamper(10);
        displayEntity.getModel().getTexture().setReflectivity(1);

        groundEntity = EntityCreator.createEntity(loader, "res/models/floor.obj", "res/textures/grass.jpg"
                ,new Vector3f(0,1,0), new Vector3f(0,0,0), new Vector3f(5,5,5), "ground");

        lightEntity = EntityCreator.createEntity(loader, "res/models/lightBulb.obj", "res/textures/yellow.png"
                ,new Vector3f(100,100,100), new Vector3f(0,0,0), new Vector3f(100,100,100), "light");


        camera = new Camera();
        camera.setPosition(new Vector3f(0,15,15));
        light = new Light(lightEntity.getPosition(), new Color(255, 255, 255));
    }

    @Override
    public void update(double dt) {
        displayEntity.addRotation(new Vector3f(1,1,0));
        displayEntity.addPosition(new Vector3f(0,-0.01f, 0));

        System.out.println(Maths.isAabbIntersect(displayEntity.getAabb(), groundEntity.getAabb()));

        lightEntity.getPosition().add(0,0,0);
        light.setPosition(lightEntity.getPosition());

        //camera.rotate();

        renderer.processEntity(groundEntity);
        renderer.processEntity(displayEntity);
        renderer.processEntity(lightEntity);

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