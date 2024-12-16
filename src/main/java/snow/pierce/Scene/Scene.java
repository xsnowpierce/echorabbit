package snow.pierce.Scene;

import snow.pierce.Components.GameObject;
import snow.pierce.Physics2D.Physics2D;
import snow.pierce.Renderer.Camera;
import snow.pierce.Renderer.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected Renderer renderer = new Renderer();
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();
    private Physics2D physics2D;

    public Scene() {
        this.physics2D = new Physics2D();
    }

    public void init() {

    }

    public void Start() {
        for (GameObject go : gameObjects) {
            go.Start();
            this.renderer.add(go);
            this.physics2D.add(go);
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

    public void removeGameObjectFromScene(GameObject go) {
        gameObjects.remove(go);
    }

    public void Update(){

        this.physics2D.Update();
        for(GameObject go : gameObjects){
            if(go.IsDead()){
                this.physics2D.DestroyGameObject(go);
            }
        }
    }

    public Camera camera() {
        return this.camera;
    }

    public Physics2D getPhysics() {
        return physics2D;
    }
}
