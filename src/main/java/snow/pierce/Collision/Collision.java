package snow.pierce.Collision;

import org.joml.Vector2f;

public class Collision {

    public Vector2f penetrationDepth;
    public Vector2f distance;
    public boolean isIntersecting;

    public Collision(Vector2f distance, boolean isIntersecting, Vector2f penetrationDepth) {
        this.distance = distance;
        this.isIntersecting = isIntersecting;
        this.penetrationDepth = penetrationDepth;
    }
}
