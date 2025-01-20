package snow.pierce.Renderer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {

    private final Matrix4f projectionMatrix;
    private final Matrix4f viewMatrix;
    public Vector2f position;
    private final int targetWidth = 144, targetHeight = 160;
    private final int width;
    private final int height;

    public Camera(Vector2f position) {
        this.position = position;

        float targetAspectRatio = (float) targetWidth / targetHeight;
        float windowAspectRatio = (float) Window.getWidth() / Window.getHeight();

        if (windowAspectRatio > targetAspectRatio) {
            height = targetHeight;
            width = Math.round(targetHeight * windowAspectRatio);
        } else {
            height = Math.round(targetWidth / windowAspectRatio);
            width = targetWidth;
        }

        System.out.println("Camera created with size " + width + "," + height);

        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        adjustProjection();
    }

    public void adjustProjection() {
        projectionMatrix.identity();

        projectionMatrix.ortho(0.0f, width, 0.0f, height, 0.0f, 100.0f);
    }

    public Matrix4f getViewMatrix() {
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        this.viewMatrix.identity();
        viewMatrix.lookAt(new Vector3f(position.x, position.y, 20.0f),
                                        cameraFront.add(position.x, position.y, 0.0f),
                                        cameraUp);

        return this.viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }
}
