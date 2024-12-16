package snow.pierce.Level;

import snow.pierce.Components.GameObject;

import java.util.List;

public class LoadedChunk {

    private Chunk chunk;
    private List<GameObject> tiles;

    public LoadedChunk(Chunk chunk, List<GameObject> tiles) {
        this.chunk = chunk;
        this.tiles = tiles;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public List<GameObject> getTiles() {
        return tiles;
    }
}
