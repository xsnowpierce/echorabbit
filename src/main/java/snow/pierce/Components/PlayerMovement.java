package snow.pierce.Components;

import org.joml.Vector2f;
import snow.pierce.Listener.KeyListener;
import snow.pierce.Renderer.Window;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerMovement extends Component {

    private float playerMoveSpeed = 1;
    private Vector2f lastMovement = new Vector2f(0, -1);
    private boolean isMovementPressed = false;

    @Override
    public void Update() {
        isMovementPressed = false;
        if(KeyListener.isKeyPressed(GLFW_KEY_W)){
            gameObject.transform.position.y += playerMoveSpeed;
            lastMovement = new Vector2f(0, 1);
            isMovementPressed = true;
        }
        if(KeyListener.isKeyPressed(GLFW_KEY_S)){
            gameObject.transform.position.y -= playerMoveSpeed;
            lastMovement = new Vector2f(0, -1);
            isMovementPressed = true;
        }
        if(KeyListener.isKeyPressed(GLFW_KEY_A)){
            gameObject.transform.position.x -= playerMoveSpeed;
            lastMovement = new Vector2f(-1, 0);
            isMovementPressed = true;
        }
        if(KeyListener.isKeyPressed(GLFW_KEY_D)){
            gameObject.transform.position.x += playerMoveSpeed;
            lastMovement = new Vector2f(1, 0);
            isMovementPressed = true;
        }
    }

    public Vector2f getLastMovement() {
        return lastMovement;
    }

    public boolean IsMovementPressed() {
        return isMovementPressed;
    }
}
