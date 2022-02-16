package core.objects.entities;

import core.Window;
import core.listeners.KeyListener;
import core.listeners.MouseListener;
import org.joml.Math;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {

    private Vector3f position = new Vector3f(0,0,0);
    private float pitch;
    private float yaw;
    private float roll;

    private float yAngleOffset = 0;

    public void rotate(Player player){
        if (Window.isMouseLocked()){
            float angleChange = MouseListener.getDx() * 0.3f;
            yAngleOffset += angleChange;
            float pitchChange = MouseListener.getDy() * 0.3f;
            pitch -= pitchChange;
        }

        pitch = Math.clamp(-90,90, pitch);

        player.getRotation().y = yAngleOffset;

        this.yaw = 180-(yAngleOffset);
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }
}
