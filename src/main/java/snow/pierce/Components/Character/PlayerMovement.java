package snow.pierce.Components.Character;

import org.joml.Vector2f;
import org.joml.Vector2i;
import snow.pierce.Components.Component;
import snow.pierce.EventSystem.EventSystem;
import snow.pierce.EventSystem.Events.PlayerEnterChunkEvent;
import snow.pierce.Listener.KeyListener;
import snow.pierce.Renderer.Window;
import snow.pierce.Scene.LevelScene;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerMovement extends Component {

    private final float playerMoveSpeed = 1;
    private Vector2f lastMovement = new Vector2f(0, -1);
    private boolean isMovementPressed = false;
    private Vector2i currentChunkPosition;


    @Override
    public void Start() {
        currentChunkPosition = ((LevelScene) Window.getScene()).getCurrentLevel().getGridPosition(gameObject.transform.position);
    }

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

        Vector2i newChunkPosition = ((LevelScene) Window.getScene()).getCurrentLevel().getGridPosition(gameObject.transform.position);

        if (!currentChunkPosition.equals(newChunkPosition)) {
            // player entered new chunk
            PlayerEnterChunkEvent playerEnterChunkEvent = new PlayerEnterChunkEvent(newChunkPosition);
            EventSystem.Notify(playerEnterChunkEvent);
        }
    }

    public Vector2f getLastMovement() {
        return lastMovement;
    }

    public boolean IsMovementPressed() {
        return isMovementPressed;
    }
}
