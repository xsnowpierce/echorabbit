package snow.pierce.Scene;

import snow.pierce.Components.*;
import org.joml.Vector2f;
import snow.pierce.Renderer.Camera;
import snow.pierce.Renderer.FontReader;
import snow.pierce.Renderer.Sprite;
import snow.pierce.Renderer.SpriteSheet;
import snow.pierce.Util.AssetPool;
import snow.pierce.Util.Time;

public class LevelEditorScene extends Scene {


    public LevelEditorScene() {

    }

    @Override
    public void init() {

        LoadResources();

        this.camera = new Camera(new Vector2f(0, 0));

        SpriteSheet sprites = AssetPool.getSpriteSheet(AssetPool.imagesPath + "character.png");

        TextObject text = new TextObject("Hello world", new Vector2f());
    }

    private void LoadResources() {
        AssetPool.getShader(AssetPool.shaderPath + "default.glsl");

        AssetPool.addSpriteSheet(AssetPool.imagesPath + "character.png",
                new SpriteSheet(AssetPool.getTexture(AssetPool.imagesPath + "character.png"),
                        16, 16, 4, 0));
    }

    @Override
    public void Update() {
        for (GameObject go : this.gameObjects) {
            go.Update();
        }

        this.renderer.render();


    }
}
