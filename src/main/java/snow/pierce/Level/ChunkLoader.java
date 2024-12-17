package snow.pierce.Level;

import org.joml.Vector2f;
import org.joml.Vector2i;
import snow.pierce.Components.GameObject;
import snow.pierce.Components.SpriteRenderer;
import snow.pierce.Components.Transform;
import snow.pierce.EventSystem.EventSystem;
import snow.pierce.EventSystem.Events.Event;
import snow.pierce.EventSystem.Events.PlayerEnterChunkEvent;
import snow.pierce.EventSystem.Observer;
import snow.pierce.Renderer.SpriteSheet;
import snow.pierce.Renderer.Window;
import snow.pierce.Util.SpriteLayer;

import java.util.*;

public class ChunkLoader implements Observer {

    private final Map<Vector2i, LoadedChunk> loadedChunks;
    private final Level currentLevel;
    private final SpriteSheet spriteSheet;
    private final int chunkRingDepth;

    public ChunkLoader(SpriteSheet spriteSheet, Level level, int chunkRingDepth) {
        this.spriteSheet = spriteSheet;
        this.currentLevel = level;
        loadedChunks = new HashMap<>();
        this.chunkRingDepth = chunkRingDepth;
        EventSystem.addObserver(this);
    }

    public void LoadChunk(int layer, Vector2i chunkToLoad) {

        if (loadedChunks.get(chunkToLoad) != null) return; // chunk is already loaded
        if (currentLevel.getLayers()[layer].chunkMap.get(chunkToLoad) == null) return; // chunk is not existent

        //System.out.println("loading chunk at " + chunkToLoad.x + ", " + chunkToLoad.y);

        Chunk loadingChunk = currentLevel.getLayers()[layer].chunkMap.get(chunkToLoad);
        List<GameObject> tiles = new ArrayList<>();

        Vector2f startPosition = new Vector2f(loadingChunk.getX(), -loadingChunk.getY()); // negative Y for LWJGL shenanigans
        Vector2i tileSize = currentLevel.getTileSize();

        int currentRow = 0;
        int currentCol = 0;
        for (int k = 0; k < loadingChunk.tileArray.length; k++) {

            int tileValue = loadingChunk.tileArray[k];
            int flippedRow = loadingChunk.getHeight() - 1 - currentRow;

            if (tileValue - 1 >= 0) {
                GameObject tile = new GameObject(
                        "tile",
                        new Transform(
                                new Vector2f(
                                        currentCol * tileSize.x + (startPosition.x * tileSize.x),
                                        flippedRow * tileSize.y + (startPosition.y * tileSize.y)
                                ),
                                new Vector2f(tileSize.x, tileSize.y)
                        ),
                        SpriteLayer.BACKGROUND_LAYER
                );

                tile.addComponent(new SpriteRenderer(spriteSheet.GetSprite(tileValue - 1)));
                Window.getScene().addGameObjectToScene(tile);
                tiles.add(tile);
            }

            currentCol++;

            if (currentCol > loadingChunk.getWidth() - 1) {
                currentCol = 0;
                currentRow++;
            }
        }

        loadedChunks.put(chunkToLoad, new LoadedChunk(loadingChunk, tiles));
    }

    public void LoadChunksAround(int layer, Vector2i centreChunk) {
        List<Vector2i> chunkPositions = GetChunkPositions(centreChunk);

        for (Vector2i position : chunkPositions) {
            LoadChunk(layer, position);
        }
    }

    public void UnloadChunk(Vector2i chunkToUnload) {

        if (loadedChunks.get(chunkToUnload) == null)
            return;

        for (int i = 0; i < loadedChunks.get(chunkToUnload).getTiles().size(); i++) {
            GameObject tile = loadedChunks.get(chunkToUnload).getTiles().get(i);

            Window.getScene().getRenderer().destroyGameObject(tile);
            tile.removeComponent(SpriteRenderer.class);
            Window.getScene().removeGameObjectFromScene(tile);
            tile.Destroy();
        }
    }

    // Function to get a list of chunk positions to render
    private List<Vector2i> GetChunkPositions(Vector2i centreChunk) {
        List<Vector2i> chunkPositions = new ArrayList<>();

        // If chunkRingDepth is 0, add only the center chunk
        if (chunkRingDepth == 0) {
            chunkPositions.add(centreChunk);
            return chunkPositions;
        }

        // Calculate positions for all chunks in the specified range
        for (int a = -chunkRingDepth; a <= chunkRingDepth; a++) {
            for (int b = -chunkRingDepth; b <= chunkRingDepth; b++) {
                chunkPositions.add(new Vector2i(
                        centreChunk.x + a * Level.chunkSize,
                        centreChunk.y + b * Level.chunkSize
                ));
            }
        }

        return chunkPositions;
    }

    @Override
    public void onNotify(Event event) {
        if (event.getClass() != PlayerEnterChunkEvent.class) return;

        PlayerEnterChunkEvent playerEnterChunkEvent = (PlayerEnterChunkEvent) event;

        // Get the new chunks that need to be loaded
        List<Vector2i> newChunks = GetChunkPositions(playerEnterChunkEvent.getChunkEntered());

        // Create a set for faster lookup of new chunk positions
        Set<Vector2i> newChunkSet = new HashSet<>(newChunks);

        for (Vector2i chunkPos : new ArrayList<>(loadedChunks.keySet())) {
            if (!newChunkSet.contains(chunkPos)) {
                // If the chunk is not in the new set, unload it
                UnloadChunk(chunkPos);
                loadedChunks.remove(chunkPos);
                //System.out.println("unloaded chunk at " + chunkPos.x + ", " + chunkPos.y);
            }
        }
    }
}
