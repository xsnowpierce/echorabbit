package snow.pierce.Level;

import com.github.cliftonlabs.json_simple.*;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Level {

    int compressionLevel;
    int gridHeight;
    boolean infiniteGrid;
    Layer[] layers; // Array of Layer data
    String renderOrder;
    int tileHeight;
    TileSet[] tileSets; // Array of TileSet data
    int tileWidth;
    String type;
    int gridWidth;

    public Level(String filePath) {

        try (FileReader reader = new FileReader(filePath)) {
            JsonObject json = (JsonObject) Jsoner.deserialize(reader);

            // Deserialize the JSON into a Level object
            this.compressionLevel = ((BigDecimal) json.get("compressionlevel")).intValue();
            this.gridHeight = ((BigDecimal) json.get("height")).intValue();
            this.infiniteGrid = (boolean) json.get("infinite");
            this.renderOrder = (String) json.get("renderorder");
            this.tileHeight = ((BigDecimal) json.get("tileheight")).intValue();
            this.tileWidth = ((BigDecimal) json.get("tilewidth")).intValue();
            this.type = (String) json.get("type");
            this.gridWidth = ((BigDecimal) json.get("width")).intValue();

            // Parse tilesets
            JsonArray tileSetsJson = (JsonArray) json.get("tilesets");

            tileSets = new TileSet[tileSetsJson.size()];
            for (int i = 0; i < tileSetsJson.size(); i++) {
                TileSet set = new TileSet((JsonObject) tileSetsJson.get(i));
                tileSets[i] = set;
            }

            // Parse layers
            JsonArray layersArray = (JsonArray) json.get("layers");

            layers = new Layer[layersArray.size()];
            for (int i = 0; i < layersArray.size(); i++) {
                layers[i] = getLayer((JsonObject) layersArray.get(i));
            }

            System.out.println("layer size " + this.layers.length);

        } catch (IOException | JsonException e) {
            e.printStackTrace();
            assert false : "Error loading file.";
        }
    }

    private static Layer getLayer(JsonObject layerObj) {

        List<Chunk> chunks = new ArrayList<>();

        // Parse chunks
        JsonArray chunksArray = (JsonArray) layerObj.get("chunks");
        for (Object chunkObj : chunksArray) {
            JsonObject chunkJson = (JsonObject) chunkObj;
            Chunk chunk = new Chunk((JsonArray) chunkJson.get("data"), ((BigDecimal) chunkJson.get("height")).intValue(),
                    ((BigDecimal) chunkJson.get("width")).intValue(), ((BigDecimal) chunkJson.get("x")).intValue(),
                    ((BigDecimal) chunkJson.get("y")).intValue());

            chunks.add(chunk);
        }

        System.out.println("chunks added " + chunks.size());

        Layer layer = new Layer(((BigDecimal) layerObj.get("id")).intValue(),
                (String) layerObj.get("name"), (String) layerObj.get("type")
                , (boolean) layerObj.get("visible"), ((BigDecimal) layerObj.get("width")).intValue(),
                ((BigDecimal) layerObj.get("height")).intValue(), ((BigDecimal) layerObj.get("x")).intValue(),
                ((BigDecimal) layerObj.get("y")).intValue(), chunks.toArray(new Chunk[chunks.size()]));

        System.out.println("layer chunk size " + layer.chunks.length);

        return layer;
    }


    public int getCompressionLevel() {
        return compressionLevel;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public boolean isInfiniteGrid() {
        return infiniteGrid;
    }

    public Layer[] getLayers() {
        return layers;
    }

    public String getRenderOrder() {
        return renderOrder;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public TileSet[] getTileSets() {
        return tileSets;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public String getType() {
        return type;
    }

    public int getGridWidth() {
        return gridWidth;
    }
}

