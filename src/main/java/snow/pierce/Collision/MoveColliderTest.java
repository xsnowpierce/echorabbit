package snow.pierce.Collision;

import org.joml.Vector2f;
import snow.pierce.Components.Component;
import snow.pierce.Util.Time;

public class MoveColliderTest extends Component {

    private final float moveSpeed = -20f;

    @Override
    public void Update() {
        gameObject.transform.position.add(new Vector2f(moveSpeed * Time.deltaTime, 0));
    }
}
