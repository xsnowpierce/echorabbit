package snow.pierce.Scene;

import snow.pierce.Components.GameObject;
import snow.pierce.Renderer.Camera;
import snow.pierce.Renderer.Renderer;
import snow.pierce.UI.UIObject;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected Renderer renderer = new Renderer();
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();
    protected List<UIObject> uiObjects = new ArrayList<>();

    public void init() {

    }

    public void Start() {
        for (GameObject go : gameObjects) {
            go.Start();
            this.renderer.add(go);
        }
        for (UIObject ui : uiObjects) {
            ui.Start();
            this.renderer.add(ui);
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject go) {
        if (!isRunning) {
            gameObjects.add(go);
        } else {
            gameObjects.add(go);
            go.Start();
            this.renderer.add(go);
        }
    }

    public void addUIObjectToScene(UIObject ui) {
        if (!isRunning) {
            uiObjects.add(ui);
        } else {
            uiObjects.add(ui);
            ui.Start();
            this.renderer.add(ui);
        }
    }

    public void removeGameObjectFromScene(GameObject go) {
        gameObjects.remove(go);
    }

    public void removeUIObjectFromScene(UIObject ui) {
        uiObjects.remove(ui);
    }

    public void Update(){}

    public Camera camera() {
        return this.camera;
    }

    public Renderer getRenderer() {
        return renderer;
    }
}
