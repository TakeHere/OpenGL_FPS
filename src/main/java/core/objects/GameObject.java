package core.objects;

import core.toolbox.RunNextFrame;
import core.toolbox.Vector3;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameObject {

    private Vector3 position;
    private Vector3 rotation;
    private Vector3 scale;
    private String name;

    public Vector3 velocity = new Vector3(0,0,0);

    public static List<GameObject> gameObjects = Collections.synchronizedList(new ArrayList<>());

    public GameObject(Vector3 position, Vector3 rotation, Vector3 scale, String name) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.name = name;

        gameObjects.add(this);
    }

    public void addPosition(Vector3 addition){
        setPosition(getPosition().add(addition));
    }

    public void addRotation(Vector3 addition){
        setRotation(getRotation().add(addition));
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Vector3 getRotation() {
        return rotation;
    }

    public void setRotation(Vector3 rotation) {
        this.rotation = rotation;
    }

    public Vector3 getScale() {
        return scale;
    }

    public void setScale(Vector3 scale) {
        this.scale = scale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void destroy(){
        RunNextFrame.runNextFrame(() -> gameObjects.remove(this));
    }
}
