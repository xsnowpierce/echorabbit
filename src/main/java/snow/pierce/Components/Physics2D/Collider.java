package snow.pierce.Components.Physics2D;

import org.joml.Vector2f;
import snow.pierce.Components.Component;

public abstract class Collider extends Component {

    protected Vector2f offset = new Vector2f();

    public Vector2f getOffset() {
        return offset;
    }


}
