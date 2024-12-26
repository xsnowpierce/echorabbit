package snow.pierce.Components.Character;

import org.joml.Vector2f;
import org.joml.Vector2i;
import snow.pierce.Collision.AABB;
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
    private AABB boundingBox;

    @Override
    public void Start() {
        currentChunkPosition = ((LevelScene) Window.getScene()).getCurrentLevel().getGridPosition(gameObject.transform.position);
        boundingBox = new AABB(gameObject.transform.position, new Vector2f(gameObject.transform.scale.x / 2, gameObject.transform.scale.y / 2));
    }

    @Override
    public void Update() {

        boundingBox.UpdatePosition(gameObject.transform.position);

        isMovementPressed = false;
        Vector2f attemptedMovement = new Vector2f();
        if(KeyListener.isKeyPressed(GLFW_KEY_W)){
            attemptedMovement.add(0, playerMoveSpeed);
            lastMovement = new Vector2f(0, 1);
            isMovementPressed = true;
        }
        if(KeyListener.isKeyPressed(GLFW_KEY_S)){
            attemptedMovement.add(0, -playerMoveSpeed);
            lastMovement = new Vector2f(0, -1);
            isMovementPressed = true;
        }
        if(KeyListener.isKeyPressed(GLFW_KEY_A)){
            attemptedMovement.add(-playerMoveSpeed, 0);
            lastMovement = new Vector2f(-1, 0);
            isMovementPressed = true;
        }
        if(KeyListener.isKeyPressed(GLFW_KEY_D)){
            attemptedMovement.add(playerMoveSpeed, 0);
            lastMovement = new Vector2f(1, 0);
            isMovementPressed = true;
        }

        LevelScene levelScene = (LevelScene) Window.getScene();

        // check for collision
        boolean colliding = false;
        for (AABB aabb : levelScene.getChunkLoader().getAABBs()) {
            if (aabb == null) continue;
            if (boundingBox.getCollision(aabb) != null) {
                colliding = true;
                System.out.println("Colliding!");
                break;
            }
        }

        if (!colliding) {
            // update position
            gameObject.transform.position.add(attemptedMovement);
            Vector2i newChunkPosition = levelScene.getCurrentLevel().getGridPosition(gameObject.transform.position);
            if (!currentChunkPosition.equals(newChunkPosition)) {
                // player entered new chunk
                PlayerEnterChunkEvent playerEnterChunkEvent = new PlayerEnterChunkEvent(newChunkPosition);
                EventSystem.Notify(playerEnterChunkEvent);
            }
        }
    }

    public Vector2f getLastMovement() {
        return lastMovement;
    }

    public boolean IsMovementPressed() {
        return isMovementPressed;
    }
}
