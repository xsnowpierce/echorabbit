package snow.pierce.Components;

import org.joml.Vector2f;
import snow.pierce.Renderer.Window;

public class FollowCamera extends Component {

    Vector2f cameraOffset;

    public FollowCamera(Vector2f cameraOffset) {
        this.cameraOffset = cameraOffset;
    }

    @Override
    public void Update() {
        Vector2f camPosition = Window.getScene().camera().position;
        gameObject.transform.position = new Vector2f(camPosition.x + cameraOffset.x, camPosition.y + cameraOffset.y);
    }
}
