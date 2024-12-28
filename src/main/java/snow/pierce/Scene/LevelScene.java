package snow.pierce.Scene;

import org.joml.Vector2f;
import snow.pierce.Components.*;
import snow.pierce.Components.Character.CharacterSpriteAnimator;
import snow.pierce.Components.Character.PlayerMovement;
import snow.pierce.Level.ChunkLoader;
import snow.pierce.Level.Level;
import snow.pierce.Renderer.Camera;
import snow.pierce.Renderer.SpriteSheet;
import snow.pierce.UI.UIObject;
import snow.pierce.Util.AssetPool;
import snow.pierce.Util.Colour;
import snow.pierce.Util.PlayerSpriteSet;
import snow.pierce.Util.SpriteLayer;

import java.util.ArrayList;

public class LevelScene extends Scene {

    private Level currentLevel;
    private ChunkLoader chunkLoader;
    private GameObject player;

    public LevelScene() {

    }

    @Override
    public void init() {

        LoadResources();
        //DebugDraw.start();

        this.camera = new Camera(new Vector2f(0, 0));

        SpriteSheet sprites = AssetPool.getSpriteSheet(AssetPool.imagesPath + "character.png");
        SpriteSheet tiles = AssetPool.getSpriteSheet(AssetPool.imagesPath + "tiles.png");

        currentLevel = new Level(AssetPool.levelPath + "newlevel.json");

        chunkLoader = new ChunkLoader(tiles, currentLevel, 1);

        player = new GameObject("Player", new Transform(new Vector2f(0, 0), new Vector2f(16, 16)), SpriteLayer.ENTITY_LAYER);
        player.addComponent(new PlayerMovement());
        player.addComponent(new SpriteRenderer(sprites.GetSprite(0)));
        player.addComponent(new CameraFollow(new Vector2f(-camera.getSize().x / 2f, -camera.getSize().y / 2f)));
        player.addComponent(new CharacterSpriteAnimator(PlayerSpriteSet.GetPlayerSpriteMap(), 8, player.getComponent(SpriteRenderer.class), player.getComponent(PlayerMovement.class)));
        addGameObjectToScene(player);

        //UIObject image = new UIObject("text background", new Transform(new Vector2f(0, 0), new Vector2f(Window.getWidth(), 20)));
        //image.addComponent(new SpriteRenderer(Colour.WHITE));
        //addUIObjectToScene(image);

        TextObject text = new TextObject("Text", new Vector2f(5, 5), Colour.BLACK);
        addUIObjectToScene(text);
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

        for (GameObject go : new ArrayList<>(this.gameObjects)) {
            go.Update();
        }
        this.renderer.render();

        // Load chunks safely
        chunkLoader.LoadChunksAround(0, currentLevel.getGridPosition(player.transform.position));
        //DebugDraw.draw();

        for (UIObject ui : this.uiObjects) {
            ui.Update();
        }
    }

    public ChunkLoader getChunkLoader() {
        return chunkLoader;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }
}
