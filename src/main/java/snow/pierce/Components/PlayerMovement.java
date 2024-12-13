package snow.pierce.Components;

import snow.pierce.Listener.KeyListener;
import snow.pierce.Renderer.Window;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerMovement extends Component {

    private float playerMoveSpeed = 1;

    @Override
    public void Update() {

        if(KeyListener.isKeyPressed(GLFW_KEY_W)){
            gameObject.transform.position.y += playerMoveSpeed;
        }
        if(KeyListener.isKeyPressed(GLFW_KEY_S)){
            gameObject.transform.position.y -= playerMoveSpeed;
        }
        if(KeyListener.isKeyPressed(GLFW_KEY_A)){
            gameObject.transform.position.x -= playerMoveSpeed;
        }
        if(KeyListener.isKeyPressed(GLFW_KEY_D)){
            gameObject.transform.position.x += playerMoveSpeed;
        }
    }
}
