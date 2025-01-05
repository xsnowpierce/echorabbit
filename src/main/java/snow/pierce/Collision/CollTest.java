package snow.pierce.Collision;

import org.joml.Vector2f;
import snow.pierce.Components.Transform;

public class CollTest extends AABB {


    public CollTest(Vector2f center, Vector2f half_extent) {
        super(center, half_extent);
    }

    public CollTest(Transform transform) {
        super(transform);
    }

    public CollTest(Vector2f center, Vector2f half_extent, boolean isTrigger) {
        super(center, half_extent, isTrigger);
    }

    public CollTest(Transform transform, boolean isTrigger) {
        super(transform, isTrigger);
    }


    @Override
    protected void PlayerEnterTrigger() {
        System.out.println("PlayerEnterTrigger");
    }

    @Override
    protected void PlayerExitTrigger() {
        System.out.println("PlayerExitTrigger");
    }

    @Override
    protected void PlayerStayTrigger() {
        //System.out.println("PlayerStayTrigger");
    }
}
