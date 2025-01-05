package snow.pierce.Collision;

import org.joml.Vector2f;

public class TriggerBox extends TriggerAABB {

    public TriggerBox(Vector2f center, Vector2f half_extent) {
        super(center, half_extent);
    }
}