package core.objects.entities;

import core.Window;
import core.listeners.KeyListener;
import core.toolbox.Maths;
import core.toolbox.Vector2;
import core.toolbox.Vector3;
import imgui.internal.ImGui;
import org.joml.Math;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

public class Camera {

    private Vector3 position = new Vector3(0,0,0);
    private float pitch;
    private float yaw;
    private float roll;

    private float yAngleOffset = 0;

    public static boolean freecam = false;

    private final float freecamSpeed = 1;
    private final Vector3 offset = new Vector3(0,30,0);

    private Matrix4f viewMatrix;

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
            setPosition(new Vector3(player.getPosition().x + offset.x, player.getPosition().y + offset.y, player.getPosition().z + offset.z));
        }
    }

    private void freecam(){
        float angle = 180 - yaw;

        Vector2 forwardVector = new Vector2(Math.sin(Math.toRadians(angle)), Math.cos(Math.toRadians(angle))).normalize();
        Vector2 rightVector = new Vector2(Math.sin(Math.toRadians(angle - 90)), Math.cos(Math.toRadians(angle - 90))).normalize();

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

        Vector2 move = forwardVector.mul(horizontal).add(rightVector.mul(vertical));
        move.mul(freecamSpeed);
        getPosition().x += move.x;
        getPosition().z += move.y;

        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_E)){
            position.y+=freecamSpeed;
        }else if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_Q)){
            position.y-=freecamSpeed;
        }
    }

    public Vector3 getPosition() {
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

    public void setPosition(Vector3 position) {
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

    public Vector3 getOffset() {
        return offset;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public void updateViewMatrix(){
        viewMatrix = Maths.getViewMatrix(this);
    }
}
