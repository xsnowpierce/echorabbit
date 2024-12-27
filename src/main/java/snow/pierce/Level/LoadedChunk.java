package snow.pierce.Level;

import snow.pierce.Collision.AABB;
import snow.pierce.Level.Tiles.LoadedTile;

import java.util.List;

public class LoadedChunk {

    private final Chunk chunk;
    private final List<LoadedTile> tiles;
    private final List<AABB> currentAABBs;

    public LoadedChunk(Chunk chunk, List<LoadedTile> tiles, List<AABB> currentAABBs) {
        this.chunk = chunk;
        this.tiles = tiles;
        this.currentAABBs = currentAABBs;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public List<LoadedTile> getTiles() {
        return tiles;
    }

    public List<AABB> getCurrentAABBs() {
        return currentAABBs;
    }
}
