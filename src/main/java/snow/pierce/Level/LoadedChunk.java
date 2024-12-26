package snow.pierce.Level;

import snow.pierce.Level.Tiles.LoadedTile;

import java.util.List;

public class LoadedChunk {

    private final Chunk chunk;
    private final List<LoadedTile> tiles;

    public LoadedChunk(Chunk chunk, List<LoadedTile> tiles) {
        this.chunk = chunk;
        this.tiles = tiles;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public List<LoadedTile> getTiles() {
        return tiles;
    }
}
