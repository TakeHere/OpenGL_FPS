package core.objects.entities;

import core.objects.GameObject;
import core.toolbox.Vector3;
import org.joml.Vector3f;

import java.awt.*;

public class Light extends GameObject {

    private Vector3 position;
    private Vector3 color;
    private Vector3 attenuation = new Vector3(1,0,0);

    public Light(Vector3 position, Color color, Vector3 attenuation, String name) {
        super(position, new Vector3(0,0,0), new Vector3(0,0,0), name);
        this.position = position;
        this.attenuation = attenuation;
        this.color = new Vector3(color.getRed(), color.getGreen(), color.getBlue());
    }

    public Light(Vector3 position, Color color, String name) {
        super(position, new Vector3(0,0,0), new Vector3(0,0,0), name);
        this.position = position;
        this.color = new Vector3(color.getRed(), color.getGreen(), color.getBlue());
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Vector3 getColor() {
        return new Vector3((float) color.x/255,(float) color.y/255,(float) color.z/255);
    }

    public void setColor(Vector3 color) {
        this.color = color;
    }

    public Vector3 getAttenuation() {
        return attenuation;
    }

    public void setAttenuation(Vector3 attenuation) {
        this.attenuation = attenuation;
    }
}
