package snow.pierce.Renderer;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import snow.pierce.Components.GameObject;
import snow.pierce.EventSystem.EventSystem;
import snow.pierce.EventSystem.Events.Event;
import snow.pierce.EventSystem.Observer;
import snow.pierce.Listener.KeyListener;
import snow.pierce.Listener.MouseListener;
import snow.pierce.Physics2D.Physics2D;
import snow.pierce.Scene.LevelScene;
import snow.pierce.Scene.Scene;
import snow.pierce.Util.Time;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window implements Observer {

    private int width, height;
    private String title;
    private long glfwWindow;

    public float r, g, b, a;
    private boolean fadeToBlack = false;

    private static Window window = null;

    private static Scene currentScene;

    private Window() {
        this.width = 432;
        this.height = 480;
        this.title = "Game";
        EventSystem.addObserver(this);
    }

    public static void changeScene(int newScene) {
        if (newScene == 0) {
            currentScene = new LevelScene();
            currentScene.init();
            currentScene.Start();
        } else {
            assert false : "Unknown scene '" + newScene + "'";
        }
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public static Scene getScene() {
        return get().currentScene;
    }

    public void run() {

        System.out.println("Initializing window");
        init();

        loop();

        // Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and the free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        System.out.println("GLFW initialized");

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        /* Center window on screen */
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(glfwWindow,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2
        );

        System.out.println("GLFW window created");

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

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        FontReader.CreateFont();

        Window.changeScene(0);
    }

    int targetFPS = 60;
    double frameTime = 1.0 / targetFPS; // Time per frame in seconds
    long lastTime = System.nanoTime();

    public void loop() {

        System.out.println("Beginning loop");

        float beginTime = Time.getTime();
        float endTime;

        while (!glfwWindowShouldClose(glfwWindow)) {

            long currentTime = System.nanoTime();

            double elapsedTime = (currentTime - lastTime) / 1_000_000_000.0;

            while (elapsedTime < frameTime) {
                currentTime = System.nanoTime();
                elapsedTime = (currentTime - lastTime) / 1_000_000_000.0;
            }

            lastTime = System.nanoTime();

            // Poll events
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            if (Time.deltaTime >= 0) {
                currentScene.Update();
            }

            glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime();
            Time.deltaTime = endTime - beginTime;
            beginTime = endTime;
        }
    }

    public static int getWidth(){
        return get().width;
    }

    public static int getHeight(){
        return get().height;
    }

    @Override
    public void onNotify(GameObject object, Event event) {
        // todo this
    }

    public static Physics2D getPhysics2D() {
        return currentScene.getPhysics();
    }
}
