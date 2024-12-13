package snow.pierce.Components;

import snow.pierce.Renderer.Window;

public class CameraFollow extends Component {

    @Override
    public void Update() {
        Window.getScene().camera().position = gameObject.transform.position;
    }
}
