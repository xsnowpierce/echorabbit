package snow.pierce.Collision;

import org.joml.Vector2f;
import snow.pierce.Components.Component;
import snow.pierce.Components.Transform;

public class AABB extends Component {

    private Vector2f center;
    private final Vector2f half_extent;
    private boolean PLAYER_TRIGGER_INSIDE;
    private AABB player;
    private boolean isTrigger = false;

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

    public Collision getCollision(AABB box2) {
        Vector2f distance = box2.center.sub(center, new Vector2f());
        distance.x = Math.abs(distance.x);
        distance.y = Math.abs(distance.y);

        // Calculate the total allowed distance between the centers before collision occurs
        Vector2f totalExtent = half_extent.add(box2.half_extent, new Vector2f());

        // Check for overlap
        boolean isColliding = distance.x < totalExtent.x && distance.y < totalExtent.y;

        // Return the collision result
        return new Collision(distance, isColliding);
    }

    public void UpdatePosition(Vector2f center) {
        this.center = center;
    }

    public void playerTouchingTrigger(AABB player) {
        if (!isTrigger) return;

        if (!PLAYER_TRIGGER_INSIDE) {
            PlayerEnterTrigger();
        }
        PLAYER_TRIGGER_INSIDE = true;
        this.player = player;
    }

    protected void PlayerStayTrigger() {
    }

    protected void PlayerEnterTrigger() {
    }

    protected void PlayerExitTrigger() {
    }

    @Override
    public void Update() {
        if (PLAYER_TRIGGER_INSIDE) {
            if (!getCollision(player).isIntersecting) {
                PlayerExitTrigger();
                PLAYER_TRIGGER_INSIDE = false;
            } else PlayerStayTrigger();
        }
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

}
