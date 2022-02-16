package core.listeners;

import core.Window;
import imgui.internal.ImGui;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener {
    private static KeyListener instance;

    private KeyListener() {}

    public static KeyListener get() {
        if (KeyListener.instance == null) {
            KeyListener.instance = new KeyListener();
        }

        return KeyListener.instance;
    }

    public static boolean isKeyPressed(int keyCode) {
        if (Window.isMouseLocked()){
            return ImGui.getIO().getKeysDown(keyCode);
        }
        return false;
    }
}
