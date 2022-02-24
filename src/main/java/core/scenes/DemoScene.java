package core.scenes;

import core.objects.EntityCreator;
import core.objects.GameObject;
import core.objects.entities.Camera;
import core.objects.entities.Entity;
import core.objects.entities.Light;
import core.renderers.MasterRenderer;
import core.toolbox.Loader;
import core.toolbox.Maths;
import core.toolbox.Vector3;
import org.joml.Vector3f;

import java.awt.*;
import java.util.Arrays;

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
        loader = new Loader();


        displayEntity = EntityCreator.createEntity(loader, "res/models/teapot.obj", "res/textures/yellow.png"
                ,new Vector3(0,10,0),new Vector3(0,0,0),new Vector3(10,10,10), "display");
        displayEntity.getModel().getTexture().setShineDamper(10);
        displayEntity.getModel().getTexture().setReflectivity(1);

        groundEntity = EntityCreator.createEntity(loader, "res/models/floor.obj", "res/textures/grass.jpg"
                ,new Vector3(0,1,0), new Vector3(0,0,0), new Vector3(5,5,5), "ground");

        lightEntity = EntityCreator.createEntity(loader, "res/models/lightBulb.obj", "res/textures/yellow.png"
                ,new Vector3(100,100,100), new Vector3(0,0,0), new Vector3(100,100,100), "light");


        camera = new Camera();
        camera.setPosition(new Vector3(0,15,15));
        light = new Light(lightEntity.getPosition(), new Color(255, 255, 255), new Vector3(1, 0.01f, 0.002f), "light");

        renderer = new MasterRenderer(camera);
    }

    @Override
    public void update(double dt) {
        displayEntity.addRotation(new Vector3(1,1,0));
        displayEntity.addPosition(new Vector3(0,-0.01f, 0));

        System.out.println(Maths.isAabbIntersect(displayEntity.getAabb(), groundEntity.getAabb()));

        light.setPosition(lightEntity.getPosition());

        //camera.rotate();

        renderer.processEntity(groundEntity);
        renderer.processEntity(displayEntity);
        renderer.processEntity(lightEntity);

        renderer.render(Arrays.asList(light), camera, true);
    }

    @Override
    public void cleanup() {
        loader.cleanup();
        renderer.cleanup();

        Entity.entities.clear();
        GameObject.gameObjects.clear();
    }
}