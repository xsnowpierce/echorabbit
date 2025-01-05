package snow.pierce.Collision;

import org.joml.Vector2f;
import snow.pierce.Components.Component;

public class AABB extends Component {

    private Vector2f center;
    private final Vector2f half_extent;

    public AABB(Vector2f center, Vector2f half_extent) {
        this.center = center;
        this.half_extent = half_extent;
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

    public Vector2f getCenter() {
        return center;
    }

    public Vector2f getHalfExtent() {
        return half_extent;
    }

}
