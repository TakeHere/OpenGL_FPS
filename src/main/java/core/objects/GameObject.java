package core.objects;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class GameObject {

    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;
    private String name;

    public Vector3f velocity = new Vector3f();

    public static List<GameObject> gameObjects = new ArrayList<>();

    public GameObject(Vector3f position, Vector3f rotation, Vector3f scale, String name) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.name = name;

        gameObjects.add(this);
    }

    public void addPosition(Vector3f addition){
        setPosition(getPosition().add(addition));
    }

    public void addRotation(Vector3f addition){
        setRotation(getRotation().add(addition));
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
