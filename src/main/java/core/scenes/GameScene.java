package core.scenes;

import core.Consts;
import core.animations.Animation;
import core.listeners.MouseListener;
import core.objects.EntityCreator;
import core.objects.GameObject;
import core.objects.entities.*;
import core.objects.models.ModelTexture;
import core.objects.models.RawModel;
import core.objects.models.TexturedModel;
import core.objects.models.objloader.OBJFileLoader;
import core.renderers.MasterRenderer;
import core.toolbox.Loader;
import core.toolbox.Maths;
import core.toolbox.Vector2;
import core.toolbox.Vector3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import static core.Consts.radConst;

public class GameScene extends Scene{

    private Camera camera;
    private Light sun;
    private List<Light> lights = new ArrayList<>();

    private Entity gun;
    private Player player;

    private MasterRenderer renderer;
    private Loader loader;

    TexturedModel bulletModel;
    TexturedModel targetModel;

    @Override
    public void init() {
        loader = new Loader();
        camera = new Camera();
        camera.setPosition(new Vector3(0,15,15));
        renderer = new MasterRenderer(camera);



        EntityCreator.createEntity(loader, "res/models/axis.obj", "res/textures/axis.png"
                ,new Vector3(0,0,0),new Vector3(0,0,0),new Vector3(10,10,10), "axis");

        EntityCreator.createEntity(loader, "res/models/floor.obj", "res/textures/wood.jpg"
                ,new Vector3(0,1,0), new Vector3(0,0,0), new Vector3(100,5,100), "ground");

        gun = EntityCreator.createEntity(loader, "res/models/gun.obj", "res/textures/gunTexture.png",
                new Vector3(0,0,0), new Vector3(0,0,0), new Vector3(3,3,3), "gun");

        player = new Player(new TexturedModel(
                OBJFileLoader.loadOBJ("res/models/player.obj", loader),
                new ModelTexture(loader.loadTexture("res/textures/yellow.png"))),
                new Vector3(0,1,0),
                new Vector3(0,0,0),
                new Vector3(10,10,10));
        player.getModel().getTexture().setShineDamper(10);
        player.getModel().getTexture().setShineDamper(1);


        RawModel rawModelBullet = OBJFileLoader.loadOBJ("res/models/bullet.obj", loader);
        ModelTexture bulletTexture = new ModelTexture(loader.loadTexture("res/textures/yellow.png"));
        bulletModel = new TexturedModel(rawModelBullet, bulletTexture);
        bulletModel.getTexture().setShineDamper(10);
        bulletModel.getTexture().setReflectivity(2);
        RawModel rawModelTarget = OBJFileLoader.loadOBJ("res/models/target.obj", loader);
        ModelTexture targetTexture = new ModelTexture(loader.loadTexture("res/textures/target.png"));
        targetModel = new TexturedModel(rawModelTarget, targetTexture);



        sun = new Light(new Vector3(100,100,100), new Color(255, 255, 255), "sun");
        lights.add(sun);
        //lights.add(new Light(new Vector3(-50,50,-50), new Color(222, 6, 47), new Vector3(1,0.01f, 0.002f), "decoLight"));



        MouseListener.addPressEvent(0, () -> {
            Vector3 cameraRot = new Vector3(camera.getPitch(), camera.getYaw(), camera.getRoll());

            Bullet bulletEntity = new Bullet(bulletModel, new Vector3(camera.getPosition().x, camera.getPosition().y, camera.getPosition().z), new Vector3(0,0,0), new Vector3(1,1,1), "bullet");
            bulletEntity.setRotation(new Vector3(camera.getPitch(), 180 - camera.getYaw(), camera.getRoll()));
            bulletEntity.velocity = Maths.forwardVector(cameraRot).mul(5);
            bulletEntity.useGravity = true;
        });

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (Target.getTargets().size() < Consts.MAX_TARGETS){
                    Vector3 position = new Vector3(Maths.randomNumberBetween(-100,100), Maths.randomNumberBetween(20,50), Maths.randomNumberBetween(-100,100));
                    Vector2 toCenterVector = new Vector2(0,0).sub(new Vector2(position.x, position.z * -1)).normalize();
                    float angle = (float) Math.toDegrees(Math.atan(toCenterVector.y/toCenterVector.x)) + 90;

                    Target target = new Target(targetModel,
                            position,
                            new Vector3(0,angle,0),
                            new Vector3(10,10,10), "target");
                }
            }
        }, 0, 2 * 1000);
    }

    @Override
    public void update(double dt) {
        //displayEntity.addRotation(new Vector3(0,1,0));
        //displayEntity.addPosition(new Vector3(0, (float) Math.cos(Math.toRadians(System.currentTimeMillis()) / 5) / 10,0));

        player.move();
        camera.move(player);

        for (Bullet bullet : Bullet.getBullets()) {
            synchronized (Entity.entities){
                for (Entity collider : Entity.entities) {
                    if (collider instanceof Bullet) continue;
                    if (collider instanceof Player) continue;
                    if (collider.getName().equalsIgnoreCase("axis")) continue;
                    if (collider == gun) continue;

                    if (Maths.isAabbIntersect(collider.getAabb(), bullet.getAabb())){
                        if (collider instanceof Target){
                            collider.destroy();
                        }

                        bullet.destroy();
                        break;
                    }
                }
            }
        }

        Vector2 gunRotationAddition = rotatePoint(player.getRotation().y);
        gunRotationAddition.x *= Consts.positionOffset.x;
        gunRotationAddition.y *= Consts.positionOffset.z;

        gun.setPosition(player.getPosition().add(new Vector3(gunRotationAddition.x,Consts.positionOffset.y,gunRotationAddition.y)));
        gun.setRotation(new Vector3(camera.getPitch(), player.getRotation().y, 0));

        for (Entity entity : Entity.entities) {
            renderer.processEntity(entity);
        }

        renderer.render(lights, camera, true);
    }

    public Vector2 rotatePoint(float angle){
        float x = (float) Math.cos(Math.toRadians(angle));
        float y = (float) Math.sin(Math.toRadians(angle));

        Vector2 point = new Vector2(x,y);

        return point;
    }

    @Override
    public void cleanup() {
        loader.cleanup();
        renderer.cleanup();

        Entity.entities.clear();
        GameObject.gameObjects.clear();
    }
}
