package snow.pierce.Scene;

import org.joml.Vector2f;
import snow.pierce.Components.*;
import snow.pierce.Level.LevelLoader;
import snow.pierce.Renderer.Camera;
import snow.pierce.Renderer.SpriteSheet;
import snow.pierce.Util.AssetPool;

public class LevelScene extends Scene {

    public LevelScene() {

    }
    @Override
    public void init() {

        LoadResources();

        this.camera = new Camera(new Vector2f(0, 0));

        SpriteSheet sprites = AssetPool.getSpriteSheet(AssetPool.imagesPath + "character.png");
        SpriteSheet tiles = AssetPool.getSpriteSheet(AssetPool.imagesPath + "tiles.png");

        LevelLoader.LoadLevel(AssetPool.levelPath + "level.json", tiles);


        TextObject text = new TextObject("Hello world 123", new Vector2f());
        text.addComponent(new FollowCamera(new Vector2f(0, 0)));
        addGameObjectToScene(text);

        GameObject player = new GameObject("Player", new Transform(new Vector2f(0, 0), new Vector2f(16, 16)), 0);
        player.addComponent(new PlayerMovement());
        player.addComponent(new SpriteRenderer(sprites.GetSprite(0)));
        player.addComponent(new CameraFollow());
        addGameObjectToScene(player);
    }

    private void LoadResources() {
        AssetPool.getShader(AssetPool.shaderPath + "default.glsl");

        AssetPool.addSpriteSheet(AssetPool.imagesPath + "character.png",
                new SpriteSheet(AssetPool.getTexture(AssetPool.imagesPath + "character.png"),
                        16, 16, 4, 0));

        AssetPool.addSpriteSheet(AssetPool.imagesPath + "tiles.png",
                new SpriteSheet(AssetPool.getTexture(AssetPool.imagesPath + "tiles.png"),
                        16, 16, 2, 0));
    }

    @Override
    public void Update() {
        for (GameObject go : this.gameObjects) {
            go.Update();
        }

        this.renderer.render();
    }
}
