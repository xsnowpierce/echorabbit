package snow.pierce.Level;

import com.github.cliftonlabs.json_simple.*;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class Level {

    int compressionLevel;
    boolean infiniteGrid;
    Layer[] layers; // Array of Layer data
    String renderOrder;
    TileSet[] tileSets; // Array of TileSet data
    String type;
    Vector2f gridSize;
    Vector2f tileSize;
    static int chunkSize;

    public Level(String filePath) {

        try (FileReader reader = new FileReader(filePath)) {
            JsonObject json = (JsonObject) Jsoner.deserialize(reader);

            // Deserialize the JSON into a Level object
            this.compressionLevel = ((BigDecimal) json.get("compressionlevel")).intValue();
            this.infiniteGrid = (boolean) json.get("infinite");
            this.renderOrder = (String) json.get("renderorder");
            this.type = (String) json.get("type");
            this.gridSize = new Vector2f(((BigDecimal) json.get("width")).intValue(), ((BigDecimal) json.get("height")).intValue());
            this.tileSize = new Vector2f(((BigDecimal) json.get("tileheight")).intValue(), ((BigDecimal) json.get("tilewidth")).intValue());

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

        Map<Vector2i, Chunk> chunkMap = new HashMap<>();
        List<Chunk> chunkList = new ArrayList<>();
        int maxX = 0;
        // Parse chunks
        JsonArray chunksArray = (JsonArray) layerObj.get("chunks");
        for (int i = 0; i < chunksArray.size(); i++) {
            Object chunkObj = chunksArray.get(i);
            JsonObject chunkJson = (JsonObject) chunkObj;
            Chunk chunk = new Chunk(i, (JsonArray) chunkJson.get("data"), ((BigDecimal) chunkJson.get("height")).intValue(),
                    ((BigDecimal) chunkJson.get("width")).intValue(), ((BigDecimal) chunkJson.get("x")).intValue(),
                    ((BigDecimal) chunkJson.get("y")).intValue());

            chunkMap.put(new Vector2i(chunk.getX(), chunk.getY()), chunk);

            if(chunk.getX() + chunk.getWidth() > maxX) {
                maxX = chunk.getX() + chunk.getWidth();
            }

            chunkSize = chunk.getWidth();
            chunkList.add(chunk);
        }

        System.out.println("chunks added " + chunkMap.size());

        Layer layer = new Layer(((BigDecimal) layerObj.get("id")).intValue(),
                (String) layerObj.get("name"), (String) layerObj.get("type")
                , (boolean) layerObj.get("visible"), ((BigDecimal) layerObj.get("width")).intValue(),
                ((BigDecimal) layerObj.get("height")).intValue(), ((BigDecimal) layerObj.get("x")).intValue(),
                ((BigDecimal) layerObj.get("y")).intValue(), chunkMap, chunkList.toArray(chunkList.toArray(new Chunk[chunkList.size()])), maxX);

        System.out.println("layer chunk size " + layer.chunkMap.size());

        return layer;
    }

    public Chunk getChunkFromPosition(int layerID, Vector2f position){
        // Calculate grid position
        int gridX = (int) ((position.x + 0.5f) / tileSize.x) / chunkSize;
        int gridY = (int) ((-position.y + 0.5f) / tileSize.y + 7.5f) / chunkSize;

        //System.out.println(gridX + " " + gridY);

        // Direct lookup in the map
        return layers[layerID].chunkMap.getOrDefault(new Vector2i(gridX * chunkSize, gridY * chunkSize), null);
    }


    public int getCompressionLevel() {
        return compressionLevel;
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

    public TileSet[] getTileSets() {
        return tileSets;
    }

    public String getType() {
        return type;
    }

    public Vector2f getGridSize() {
        return gridSize;
    }

    public Vector2f getTileSize() {
        return tileSize;
    }
}

