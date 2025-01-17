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

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerMovement extends Component {

    private final float playerMoveSpeed = 1;
    private final Vector2f lastMovement = new Vector2f(0, -1);
    private boolean isMovementPressed = false;
    private Vector2i currentChunkPosition;
    private AABB boundingBox;
    private List<AABB> touchingAABBTriggers;

    @Override
    public void Start() {
        touchingAABBTriggers = new ArrayList<>();
        currentChunkPosition = ((LevelScene) Window.getScene()).getCurrentLevel().getGridPosition(gameObject.transform.position);
        boundingBox = new AABB(gameObject.transform.position, new Vector2f(gameObject.transform.scale.x / 2, gameObject.transform.scale.y / 2));
    }

    @Override
    public void Update() {
        boundingBox.UpdatePosition(gameObject.transform.position);
        CheckCurrentTriggers();

        isMovementPressed = false;
        Vector2f attemptedMovement = calculateAttemptedMovement();

        if (attemptedMovement.length() != 0) {

            Vector2f resolvedMovement = resolveMovement(attemptedMovement);

            // Update position and handle chunk transition
            gameObject.transform.position.set(resolvedMovement);
            checkChunkTransition(resolvedMovement);
        }
    }

    private Vector2f calculateAttemptedMovement() {
        Vector2f attemptedMovement = new Vector2f();
        Vector2f[] directions = {
                new Vector2f(0, playerMoveSpeed),  // W
                new Vector2f(0, -playerMoveSpeed), // S
                new Vector2f(-playerMoveSpeed, 0), // A
                new Vector2f(playerMoveSpeed, 0)   // D
        };
        int[] keys = {GLFW_KEY_W, GLFW_KEY_S, GLFW_KEY_A, GLFW_KEY_D};

        for (int i = 0; i < keys.length; i++) {
            if (KeyListener.isKeyPressed(keys[i])) {
                attemptedMovement.add(new Vector2f(directions[i]));
                lastMovement.set(directions[i]).normalize();
                isMovementPressed = true;
            }
        }
        return attemptedMovement;
    }

    private Vector2f resolveMovement(Vector2f attemptedMovement) {
        LevelScene levelScene = (LevelScene) Window.getScene();
        Vector2f resolvedPosition = new Vector2f(gameObject.transform.position);

        // Combine X and Y collision checks into a single loop
        Vector2f[] axes = {
                new Vector2f(attemptedMovement.x, 0),
                new Vector2f(0, attemptedMovement.y)
        };

        for (Vector2f axis : axes) {
            resolvedPosition.add(axis);
            boundingBox.UpdatePosition(resolvedPosition);

            if (isCollidingWithAnyAABB(levelScene, boundingBox)) {
                resolvedPosition.sub(axis); // Undo movement if colliding
            }
        }

        return resolvedPosition;
    }

    private AABB getFirstCollidingAABB(LevelScene levelScene, AABB boundingBox) {
        List<AABB> aabbList = new ArrayList<>(levelScene.getChunkLoader().getAABBs());
        aabbList.addAll(levelScene.getAabbList());

        for (AABB aabb : aabbList) {
            if (aabb != null && boundingBox.getCollision(aabb).isIntersecting) {
                return aabb;
            }
        }
        return null;
    }

    private boolean isCollidingWithAnyAABB(LevelScene levelScene, AABB boundingBox) {

        // todo make this only check nearby ones instead of all the ones loaded

        List<AABB> aabbList = new ArrayList<>(levelScene.getChunkLoader().getAABBs());
        aabbList.addAll(levelScene.getAabbList());

        for (AABB aabb : aabbList) {
            if (aabb != null && boundingBox.getCollision(aabb).isIntersecting) {
                if (aabb.IsTrigger()) {
                    CheckCollisionWithTrigger(aabb);
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    private void CheckCollisionWithTrigger(AABB trigger) {
        if (!touchingAABBTriggers.contains(trigger)) {
            touchingAABBTriggers.add(trigger);
            trigger.PlayerEnterTrigger();
        }
    }

    private void CheckCurrentTriggers() {
        List<AABB> triggerList = new ArrayList<>(touchingAABBTriggers);

        for (AABB trigger : triggerList) {
            if (trigger != null && trigger.IsTrigger()) {
                if (!trigger.getCollision(boundingBox).isIntersecting) {
                    trigger.PlayerExitTrigger();
                    touchingAABBTriggers.remove(trigger);
                } else trigger.PlayerStayTrigger();
            }
        }
    }

    private void checkChunkTransition(Vector2f position) {
        LevelScene levelScene = (LevelScene) Window.getScene();
        Vector2i newChunkPosition = levelScene.getCurrentLevel().getGridPosition(position);

        if (!currentChunkPosition.equals(newChunkPosition)) {
            EventSystem.Notify(new PlayerEnterChunkEvent(newChunkPosition));
        }
    }

    public Vector2f getLastMovement() {
        return lastMovement;
    }

    public boolean IsMovementPressed() {
        return isMovementPressed;
    }

    public AABB getBoundingBox() {
        return boundingBox;
    }
}
