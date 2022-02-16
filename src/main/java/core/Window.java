package core;

import core.listeners.KeyListener;
import core.listeners.MouseListener;
import core.objects.GameObject;
import core.objects.entities.Entity;
import core.objects.models.ModelTexture;
import core.objects.models.RawModel;
import core.objects.models.TexturedModel;
import core.objects.models.objloader.OBJFileLoader;
import core.renderers.debug.DebugSphere;
import core.scenes.DemoScene;
import core.scenes.GameScene;
import core.scenes.Scene;
import core.toolbox.Loader;
import core.toolbox.Time;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.windows.User32.*;

public class Window {
    private final int width;
    private final int height;
    private final String title;
    private long glfwWindow;

    private static Window window = null;

    private static Scene currentScene = null;
    private static int currentSceneIndex = 0;

    private static boolean mouseLocked = false;

    private Window(){
        this.width = 1000;
        this.height = 1000;
        this.title = "Ethereal";
    }

    public static void changeScene(int newScene){
        GameObject.gameObjects.clear();

        currentSceneIndex = newScene;

        if (currentScene != null)
            currentScene.cleanup();

        switch (newScene){
            case 0:
                currentScene = new DemoScene();
                currentScene.init();
                break;
            case 1:
                currentScene = new GameScene();
                currentScene.init();
            default:
                assert false: "Unknown scene: " + newScene;
                break;
        }
    }

    public static Window get(){
        if (window == null){
            Window.window = new Window();
        }
        return window;
    }

    public void run() {
        System.out.println("LWJGL Version: " + Version.getVersion());

        init();
        loop();

        //Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //Terminate glfw and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        //glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        int max_width  = GetSystemMetrics(SM_CXSCREEN);
        int max_height = GetSystemMetrics(SM_CYSCREEN);

        glfwSetWindowMonitor(glfwWindow, NULL, (max_width/2)-(width/2), (max_height/2) - (height/2), width, height, GLFW_DONT_CARE);

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        //OpenGL Initialization
        GL.createCapabilities();
    }

    int fps;
    long lastTime = System.currentTimeMillis();

    private void loop() {
        float beginTime = Time.getTime();
        float endTime = Time.getTime();
        float dt = -1.0f;

        Loader loader = new Loader();

        RawModel rawModel = OBJFileLoader.loadOBJ("res/models/debugSphere.obj", loader);

        ModelTexture redTexture = new ModelTexture(loader.loadTexture("res/textures/red.png"));
        redTexture.setUseFakeLighting(true);
        ModelTexture yellowTexture = new ModelTexture(loader.loadTexture("res/textures/yellow.png"));
        yellowTexture.setUseFakeLighting(true);
        ModelTexture blueTexture = new ModelTexture(loader.loadTexture("res/textures/blue.png"));
        blueTexture.setUseFakeLighting(true);

        DebugSphere.redTexture = new TexturedModel(rawModel, redTexture);
        DebugSphere.yellowTexture = new TexturedModel(rawModel, yellowTexture);
        DebugSphere.blueTexture = new TexturedModel(rawModel, blueTexture);

        changeScene(1);

        while (!glfwWindowShouldClose(glfwWindow)) {
            //Poll events
            glfwPollEvents();

            if (MouseListener.mouseButtonDown(0)){
                glfwSetInputMode(glfwWindow, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
                mouseLocked = true;
            }else if (KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)){
                glfwSetInputMode(glfwWindow, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
                mouseLocked = false;
            }

            if (KeyListener.isKeyPressed(GLFW_KEY_PAGE_UP)){
                changeScene(1);
            }else if (KeyListener.isKeyPressed(GLFW_KEY_PAGE_DOWN)){
                changeScene(0);
            }

            if (dt >= 0){
                for (Entity entity : Entity.entities) {
                    entity.updateAABB();
                    if (entity.useGravity)
                        entity.velocity.y += Entity.GRAVITY * dt;
                }

                for (GameObject gameObject : GameObject.gameObjects) {
                    gameObject.addPosition(gameObject.velocity);
                }

                currentScene.update(dt);
            }

            MouseListener.endFrame();

            glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;

            //--------< Display FPS >--------
            fps++;
            if (System.currentTimeMillis() - lastTime > 1000){
                glfwSetWindowTitle(glfwWindow, title + " | fps: " + fps);
                lastTime += 1000;
                fps = 0;
            }
        }

        currentScene.cleanup();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static boolean isMouseLocked() {
        return mouseLocked;
    }
}
