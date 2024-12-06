package snow.pierce.Scene;

import snow.pierce.Components.GameObject;
import snow.pierce.Components.SpriteRenderer;
import org.joml.Vector2f;
import snow.pierce.Components.Transform;
import snow.pierce.Renderer.Camera;
import snow.pierce.Renderer.Sprite;
import snow.pierce.Renderer.SpriteSheet;
import snow.pierce.Util.AssetPool;
import snow.pierce.Util.Time;

public class LevelEditorScene extends Scene {

    private GameObject obj1;

    public LevelEditorScene() {

    }

    @Override
    public void init() {

        LoadResources();

        this.camera = new Camera(new Vector2f(0, 0));

        SpriteSheet sprites = AssetPool.getSpriteSheet("resources/images/character.png");

        obj1 = new GameObject("Object1", new Transform(new Vector2f(0, 0), new Vector2f(16, 16)), 0);
        obj1.addComponent(new SpriteRenderer(sprites.GetSprite(0)));
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object2", new Transform(new Vector2f(18, 0), new Vector2f(16, 16)), 0);
        obj2.addComponent(new SpriteRenderer(sprites.GetSprite(1)));
        this.addGameObjectToScene(obj2);
    }

    private void LoadResources() {
        AssetPool.getShader("resources/shaders/default.glsl");

        AssetPool.addSpriteSheet("resources/images/character.png",
                new SpriteSheet(AssetPool.getTexture("resources/images/character.png"),
                        16, 16, 4, 0));
    }

    @Override
    public void Update() {

        obj1.transform.position.x += 10 * Time.deltaTime;

        for (GameObject go : this.gameObjects) {
            go.Update();
        }

        this.renderer.render();
    }
}
