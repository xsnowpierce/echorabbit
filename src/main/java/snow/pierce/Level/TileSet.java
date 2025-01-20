package snow.pierce.Level;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import org.joml.Vector2i;
import snow.pierce.Level.Tiles.Tile;

import java.math.BigDecimal;

public class TileSet {

    private final Vector2i tileSize;

    public TileSet(JsonObject tileSet) {

        tileCount = ((BigDecimal) tileSet.get("tilecount")).intValue();

        int tileSizeX = ((BigDecimal) tileSet.get("tilewidth")).intValue();
        int tileSizeY = ((BigDecimal) tileSet.get("tileheight")).intValue();
        tileSize = new Vector2i(tileSizeX, tileSizeY);


        JsonArray tiles = (JsonArray) tileSet.get("tiles");

        this.tiles = new Tile[tiles.size()];
        for (int i = 0; i < tiles.size(); i++) {
            JsonObject tile = (JsonObject) tiles.get(i);

            TileType tileType = TileType.fromString((String) tile.get("type"));

            Tile newTile = new Tile(tileType, (JsonArray) tile.get("properties"));
            this.tiles[i] = newTile;
        }
    }

    private final int tileCount;

    public int getTileCount() {
        return tileCount;
    }
    private final Tile[] tiles;

    public Vector2i getTileSize() {
        return tileSize;
    }


}
