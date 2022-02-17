package core.objects.entities;

import core.Window;
import core.listeners.KeyListener;
import core.listeners.MouseListener;
import imgui.internal.ImGui;
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

    public static boolean freecam = false;
    private final float freecamSpeed = 1;

    public void move(Player player){
        if (Window.isMouseLocked()){
            float angleChange = ImGui.getIO().getMouseDeltaX() * 0.3f;
            yAngleOffset -= angleChange;
            float pitchChange = ImGui.getIO().getMouseDeltaY() * 0.3f;
            pitch += pitchChange;
        }

        pitch = Math.clamp(-90,90, pitch);

        player.getRotation().y = yAngleOffset;

        this.yaw = 180-(yAngleOffset);

        if(freecam){
            freecam();
        }else{
            setPosition(new Vector3f(player.getPosition().x + 0, player.getPosition().y + 10, player.getPosition().z + 0));
        }
    }

    private void freecam(){
        float angle = 180 - yaw;

        Vector2f forwardVector = new Vector2f((float) java.lang.Math.sin(java.lang.Math.toRadians(angle)), (float) java.lang.Math.cos(java.lang.Math.toRadians(angle))).normalize();
        Vector2f rightVector = new Vector2f((float) java.lang.Math.sin(java.lang.Math.toRadians(angle - 90)), (float) java.lang.Math.cos(java.lang.Math.toRadians(angle - 90))).normalize();

        int vertical = 0;
        int horizontal = 0;

        if(KeyListener.isKeyPressed(GLFW.GLFW_KEY_W)){
            horizontal = 1;
        }else if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_S)){
            horizontal = -1;
        }

        if(KeyListener.isKeyPressed(GLFW.GLFW_KEY_D)){
            vertical = 1;
        }else if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_A)){
            vertical = -1;
        }

        Vector2f move = forwardVector.mul(horizontal).add(rightVector.mul(vertical));
        move.mul(freecamSpeed);
        getPosition().x += move.x;
        getPosition().z += move.y;

        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_E)){
            position.y+=freecamSpeed;
        }else if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_Q)){
            position.y-=freecamSpeed;
        }
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
