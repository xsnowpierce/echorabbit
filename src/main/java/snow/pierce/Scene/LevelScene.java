package snow.pierce.Scene;

import org.joml.Vector2f;
import snow.pierce.Collision.AABB;
import snow.pierce.Collision.CollTest;
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

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LevelScene extends Scene {

    private Level currentLevel;
    private ChunkLoader chunkLoader;
    private GameObject player;
    private final List<AABB> aabbList = new ArrayList<>();

    public LevelScene() {

    }

    @Override
    public void init() {

        LoadResources();
        //DebugDraw.start();

        this.camera = new Camera(new Vector2f(0, 0));

        SpriteSheet sprites = AssetPool.getSpriteSheet(AssetPool.getImagesPath() + "character.png");
        SpriteSheet tiles = AssetPool.getSpriteSheet(AssetPool.getImagesPath() + "tiles.png");

        currentLevel = new Level(AssetPool.getLevelPath() + "newlevel.json");

        chunkLoader = new ChunkLoader(tiles, currentLevel, 1);

        player = new GameObject("Player", new Transform(new Vector2f(0, 0), new Vector2f(16, 16)), SpriteLayer.ENTITY_LAYER);
        player.addComponent(new PlayerMovement());
        player.addComponent(new SpriteRenderer(sprites.GetSprite(0)));
        player.addComponent(new CameraFollow(new Vector2f(-camera.getSize().x / 2f, -camera.getSize().y / 2f)));
        player.addComponent(new CharacterSpriteAnimator(PlayerSpriteSet.GetPlayerSpriteMap(), 8, player.getComponent(SpriteRenderer.class), player.getComponent(PlayerMovement.class)));
        addGameObjectToScene(player);


        TextObject text = new TextObject("Text", new Vector2f(5, 5), Colour.BLACK);

        //UIObject image = new UIObject("text background", new Transform(new Vector2f(0, 0), new Vector2f(Window.getWidth(), 20)), SpriteLayer.UI_LAYER.getValue() - 1);
        //image.addComponent(new SpriteRenderer(Colour.WHITE));
        //addGameObjectToScene(image);

        GameObject collTest = new GameObject("colltest", new Transform(new Vector2f(16, 16), new Vector2f(16, 16)), SpriteLayer.ITEM_LAYER);
        collTest.addComponent(new SpriteRenderer(Color.MAGENTA));
        collTest.addComponent(new CollTest(collTest.transform, true));
        addGameObjectToScene(collTest);
    }

    public List<AABB> getAabbList() {
        return aabbList;
    }

    @Override
    public void addGameObjectToScene(GameObject gameObject) {
        super.addGameObjectToScene(gameObject);
        if (gameObject.getComponent(AABB.class) != null) {
            aabbList.add(gameObject.getComponent(AABB.class));
        }
    }

    @Override
    public void removeGameObjectFromScene(GameObject gameObject) {
        super.removeGameObjectFromScene(gameObject);
        if (gameObject.getComponent(AABB.class) != null) {
            aabbList.remove(gameObject.getComponent(AABB.class));
        }
    }

    private void LoadResources() {
        AssetPool.getShader(AssetPool.getShaderPath() + "default.glsl");

        AssetPool.addSpriteSheet(AssetPool.getImagesPath() + "character.png",
                new SpriteSheet(AssetPool.getTexture(AssetPool.getImagesPath() + "character.png"),
                        16, 16, 16, 0));

        AssetPool.addSpriteSheet(AssetPool.getImagesPath() + "tiles.png",
                new SpriteSheet(AssetPool.getTexture(AssetPool.getImagesPath() + "tiles.png"),
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
