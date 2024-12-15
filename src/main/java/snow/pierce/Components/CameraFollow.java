package snow.pierce.Components;

import org.joml.Vector2f;
import snow.pierce.Renderer.Window;

public class CameraFollow extends Component {

    private Vector2f offset = new Vector2f();

    public CameraFollow(Vector2f offset) {
        this.offset = offset;
    }

    @Override
    public void Update() {
        Vector2f position = new Vector2f(gameObject.transform.position);
        position.x += offset.x;
        position.y += offset.y;
        Window.getScene().camera().position = position;
    }
}
