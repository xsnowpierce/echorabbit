package snow.pierce.UI;

import org.joml.Vector2f;
import snow.pierce.Components.FollowCamera;
import snow.pierce.Components.GameObject;
import snow.pierce.Components.Transform;
import snow.pierce.Util.SpriteLayer;

public class UIObject extends GameObject {

    public UIObject(String name) {
        super(name);
    }

    public UIObject(String name, Transform transform) {
        super(name, transform, SpriteLayer.UI_LAYER);
        this.addComponent(new FollowCamera(new Vector2f(transform.position.x, transform.position.y)));
    }

    public UIObject(String name, Transform transform, SpriteLayer layer) {
        super(name, transform, layer);
        this.addComponent(new FollowCamera(new Vector2f(transform.position.x, transform.position.y)));
    }

    public UIObject(String name, Transform transform, int layer) {
        super(name, transform, layer);
        this.addComponent(new FollowCamera(new Vector2f(transform.position.x, transform.position.y)));
    }
}
