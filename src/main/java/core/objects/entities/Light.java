package core.objects.entities;

import org.joml.Vector3f;

import java.awt.*;

public class Light {

    private Vector3f position;
    private Vector3f color;

    public Light(Vector3f position, Color color) {
        this.position = position;
        this.color = new Vector3f(color.getRed(), color.getGreen(), color.getBlue());
    }

    public Light(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getColor() {
        return new Vector3f((float) color.x/255,(float) color.y/255,(float) color.z/255);
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}
