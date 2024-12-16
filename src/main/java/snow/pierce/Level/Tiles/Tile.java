package snow.pierce.Level.Tiles;

import com.github.cliftonlabs.json_simple.JsonArray;
import snow.pierce.Level.TileType;

public class Tile {

    private TileType tileType;

    public Tile(TileType tileType, JsonArray properties) {
        this.tileType = tileType;
    }

    public TileType getTileType() {
        return tileType;
    }
}
