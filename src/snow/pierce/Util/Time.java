package snow.pierce.Util;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Time {
    public static float timeStarted = (float) glfwGetTime();

    public static float getTime() { return (float)glfwGetTime(); }

    public static float deltaTime;
}
