package snow.pierce.Collision;

import org.joml.Vector2f;
import snow.pierce.Components.Component;
import snow.pierce.Components.Transform;

import java.util.ArrayList;
import java.util.List;

public class AABB extends Component {

    private final List<TriggerListener> triggerListenerList = new ArrayList<>();
    private Vector2f center;
    private final Vector2f half_extent;
    private ColliderType colliderType = ColliderType.STATIC;
    private Vector2f velocity = new Vector2f(0, 0);
    private Vector2f lastFramePosition;

    private boolean PLAYER_TRIGGER_INSIDE;
    private AABB player;
    private boolean isTrigger = false;
    private boolean hasUpdatedPosition = false;

    public AABB(Vector2f center, Vector2f half_extent) {
        this.center = center;
        this.half_extent = half_extent;
    }

    public AABB(Transform transform) {
        this.center = transform.position;
        this.half_extent = new Vector2f(transform.scale.x / 2, transform.scale.y / 2);
    }

    public AABB(Vector2f center, Vector2f half_extent, boolean isTrigger) {
        this.center = center;
        this.half_extent = half_extent;
        this.isTrigger = isTrigger;
    }

    public AABB(Transform transform, boolean isTrigger) {
        this.center = transform.position;
        this.half_extent = new Vector2f(transform.scale.x / 2, transform.scale.y / 2);
        this.isTrigger = isTrigger;
    }

    public AABB(Transform transform, ColliderType colliderType, boolean isTrigger) {
        this.center = transform.position;
        this.half_extent = new Vector2f(transform.scale.x / 2, transform.scale.y / 2);
        this.isTrigger = isTrigger;
        this.colliderType = colliderType;
    }

    @Override
    public void Update() {
        if (colliderType != ColliderType.DYNAMIC) return;

        if (!hasUpdatedPosition) {
            lastFramePosition = gameObject.transform.position;
            hasUpdatedPosition = true;
            return;
        }

        velocity = new Vector2f(gameObject.transform.position.x - lastFramePosition.x, gameObject.transform.position.y - lastFramePosition.y);

        lastFramePosition = gameObject.transform.position;

        center = gameObject.transform.position;
    }

    // Collision detection method
    public Collision getCollision(AABB box2) {
        Vector2f distance = box2.center.sub(center, new Vector2f());
        distance.x = Math.abs(distance.x);
        distance.y = Math.abs(distance.y);

        // Calculate the total allowed distance between the centers before collision occurs
        Vector2f totalExtent = half_extent.add(box2.half_extent, new Vector2f());

        // Check for overlap
        boolean isColliding = distance.x < totalExtent.x && distance.y < totalExtent.y;

        // If colliding, calculate penetration depth
        Vector2f penetrationDepth = new Vector2f(0, 0);
        if (isColliding) {
            penetrationDepth.x = totalExtent.x - distance.x;
            penetrationDepth.y = totalExtent.y - distance.y;
        }

        return new Collision(distance, isColliding, penetrationDepth);
    }

    // Update position of the collider
    public void UpdatePosition(Vector2f center) {
        this.center = center;
    }

    // Trigger logic
    public void playerTouchingTrigger(AABB player) {
        if (!isTrigger) return;

        if (!PLAYER_TRIGGER_INSIDE) {
            PlayerEnterTrigger();
        }
        PLAYER_TRIGGER_INSIDE = true;
        this.player = player;
    }

    public void PlayerEnterTrigger() {
        for (TriggerListener listener : triggerListenerList) {
            listener.PlayerEnterTrigger();
        }
    }

    public void PlayerStayTrigger() {
        for (TriggerListener listener : triggerListenerList) {
            listener.PlayerStayTrigger();
        }
    }

    public void PlayerExitTrigger() {
        for (TriggerListener listener : triggerListenerList) {
            listener.PlayerExitTrigger();
        }
    }

    public void AddTriggerListener(TriggerListener listener) {
        triggerListenerList.add(listener);
    }

    public void RemoveTriggerListener(TriggerListener listener) {
        triggerListenerList.remove(listener);
    }

    // Getter and setter for velocity
    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity.set(velocity);
    }

    public Vector2f getCenter() {
        return center;
    }

    public Vector2f getHalfExtent() {
        return half_extent;
    }

    public boolean IsTrigger() {
        return isTrigger;
    }

    public void setTrigger(boolean isTrigger) {
        this.isTrigger = isTrigger;
    }

    public void SetColliderType(ColliderType colliderType) {
        this.colliderType = colliderType;
    }

    public ColliderType GetColliderType() {
        return this.colliderType;
    }
}

