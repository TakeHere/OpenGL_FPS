package core.objects.entities;

import core.objects.GameObject;
import org.joml.Vector3f;

import java.awt.*;

public class Light extends GameObject {

    private Vector3f position;
    private Vector3f color;
    private Vector3f attenuation = new Vector3f(1,0,0);

    public Light(Vector3f position, Color color, Vector3f attenuation, String name) {
        super(position, new Vector3f(0,0,0), new Vector3f(0,0,0), name);
        this.position = position;
        this.attenuation = attenuation;
        this.color = new Vector3f(color.getRed(), color.getGreen(), color.getBlue());
    }

    public Light(Vector3f position, Color color, String name) {
        super(position, new Vector3f(0,0,0), new Vector3f(0,0,0), name);
        this.position = position;
        this.color = new Vector3f(color.getRed(), color.getGreen(), color.getBlue());
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

    public Vector3f getAttenuation() {
        return attenuation;
    }

    public void setAttenuation(Vector3f attenuation) {
        this.attenuation = attenuation;
    }
}
