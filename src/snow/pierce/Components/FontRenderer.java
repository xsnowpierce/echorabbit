package snow.pierce.Components;

public class FontRenderer extends Component {

    @Override
    public void Start() {
        if (gameObject.getComponent(SpriteRenderer.class) != null) {
            System.out.println("Found Font Renderer!");
        }
    }
}
