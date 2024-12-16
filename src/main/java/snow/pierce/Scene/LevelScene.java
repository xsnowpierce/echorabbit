package snow.pierce.Scene;

import org.joml.Vector2f;
import snow.pierce.Collision.AABB;
import snow.pierce.Components.*;
import snow.pierce.Components.Character.CharacterSpriteAnimator;
import snow.pierce.Components.Character.PlayerMovement;
import snow.pierce.Level.LevelLoader;
import snow.pierce.Renderer.Camera;
import snow.pierce.Renderer.SpriteSheet;
import snow.pierce.Util.AssetPool;
import snow.pierce.Util.PlayerSpriteSet;
import snow.pierce.Util.SpriteLayer;

public class LevelScene extends Scene {

    public LevelScene() {

    }

    @Override
    public void init() {

        LoadResources();

        this.camera = new Camera(new Vector2f(0, 0));

        SpriteSheet sprites = AssetPool.getSpriteSheet(AssetPool.imagesPath + "character.png");
        SpriteSheet tiles = AssetPool.getSpriteSheet(AssetPool.imagesPath + "tiles.png");

        LevelLoader.LoadLevel(AssetPool.levelPath + "newlevel.json", tiles);

        GameObject player = new GameObject("Player", new Transform(new Vector2f(0, 0), new Vector2f(16, 16)), SpriteLayer.ENTITY_LAYER);
        player.addComponent(new PlayerMovement());
        player.addComponent(new SpriteRenderer(sprites.GetSprite(0)));
        player.addComponent(new CameraFollow(new Vector2f(-camera.getSize().x / 2f, -camera.getSize().y / 2f)));
        player.addComponent(new CharacterSpriteAnimator(PlayerSpriteSet.GetPlayerSpriteMap(), 8, player.getComponent(SpriteRenderer.class), player.getComponent(PlayerMovement.class)));
        addGameObjectToScene(player);

        AABB box1 = new AABB(new Vector2f(0, 0), new Vector2f(16, 16));
        AABB box2 = new AABB(new Vector2f(2, 0), new Vector2f(16, 16));
    }

    private void LoadResources() {
        AssetPool.getShader(AssetPool.shaderPath + "default.glsl");

        AssetPool.addSpriteSheet(AssetPool.imagesPath + "character.png",
                new SpriteSheet(AssetPool.getTexture(AssetPool.imagesPath + "character.png"),
                        16, 16, 16, 0));

        AssetPool.addSpriteSheet(AssetPool.imagesPath + "tiles.png",
                new SpriteSheet(AssetPool.getTexture(AssetPool.imagesPath + "tiles.png"),
                        16, 16, 3, 0));
    }

    @Override
    public void Update() {
        for (GameObject go : this.gameObjects) {
            go.Update();
        }

        this.renderer.render();
    }
}
