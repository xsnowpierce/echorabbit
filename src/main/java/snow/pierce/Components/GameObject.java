package snow.pierce.Components;

import snow.pierce.Util.SpriteLayer;

import java.util.ArrayList;
import java.util.List;

public class GameObject {

    private final String name;
    private final List<Component> components;
    public Transform transform;
    private final int zIndex;
    private boolean isDead = false;

    public GameObject(String name) {
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = new Transform();
        this.zIndex = 0;
    }

    public GameObject(String name, Transform transform, SpriteLayer layer) {
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = transform;
        this.zIndex = layer.getValue();
    }

    public GameObject(String name, Transform transform, int layer) {
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = transform;
        this.zIndex = layer;
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "Error: Casting component.";
                }
            }
        }

        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (int i=0; i < components.size(); i++) {
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public void addComponent(Component c) {
        this.components.add(c);
        c.gameObject = this;
    }

    public void Update() {
        for (int i=0; i < components.size(); i++) {
            components.get(i).Update();
        }
    }

    public void Start() {
        for (int i=0; i < components.size(); i++) {
            components.get(i).Start();
        }
    }

    public void Destroy(){
        for (int i=0; i < components.size(); i++) {
            components.get(i).Destroy();
        }
        isDead = true;
    }

    public int zIndex(){
        return zIndex;
    }

    public boolean IsDead() {
        return isDead;
    }

    public List<Component> getAllComponents() {
        return components;
    }
}
