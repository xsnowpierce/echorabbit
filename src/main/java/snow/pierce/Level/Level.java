package snow.pierce.Level;

import com.github.cliftonlabs.json_simple.*;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Level {

    int compressionlevel;
    int height;
    boolean infinite;
    Layer[] layers; // Array of Layer data
    int nextlayerid;
    int nextobjectid;
    String orientation;
    String renderorder;
    String tiledversion;
    int tileheight;
    JsonArray tilesets; // Array of TileSet data
    int tilewidth;
    String type;
    String version;
    int width;

    public Level(String filePath) {

        try (FileReader reader = new FileReader(filePath)) {
            JsonObject json = (JsonObject) Jsoner.deserialize(reader);

            // Deserialize the JSON into a Map object
            this.compressionlevel = ((BigDecimal) json.get("compressionlevel")).intValue();
            this.height = ((BigDecimal) json.get("height")).intValue();
            this.infinite = (boolean) json.get("infinite");
            this.nextlayerid = ((BigDecimal) json.get("nextlayerid")).intValue();
            this.nextobjectid = ((BigDecimal) json.get("nextobjectid")).intValue();
            this.orientation = (String) json.get("orientation");
            this.renderorder = (String) json.get("renderorder");
            this.tiledversion = (String) json.get("tiledversion");
            this.tileheight = ((BigDecimal) json.get("tileheight")).intValue();
            this.tilewidth = ((BigDecimal) json.get("tilewidth")).intValue();
            this.type = (String) json.get("type");
            this.version = (String) json.get("version");
            this.width = ((BigDecimal) json.get("width")).intValue();

            // Parse tilesets
            JsonArray tilesetsArray = (JsonArray) json.get("tilesets");
            this.tilesets = new JsonArray();
            for (Object tsObj : tilesetsArray) {
                JsonObject ts = (JsonObject) tsObj;
                TileSet tileSet = new TileSet(((BigDecimal) ts.get("firstgid")).intValue(),
                        (String) ts.get("source"));
                this.tilesets.add(tileSet);
            }
            System.out.println("tilset size " + this.tilesets.size());

            // Parse layers
            JsonArray layersArray = (JsonArray) json.get("layers");
            List<Layer> layerList = new ArrayList<>();
            for (Object layerObj : layersArray) {
                Layer layer = getLayer((JsonObject) layerObj);

                layerList.add(layer);
            }
            this.layers = layerList.toArray(new Layer[layerList.size()]);
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
        return compressionlevel;
    }

    public int getHeight() {
        return height;
    }

    public boolean isInfinite() {
        return infinite;
    }

    public Layer[] getLayers() {
        return layers;
    }

    public int getNextlayerid() {
        return nextlayerid;
    }

    public int getNextobjectid() {
        return nextobjectid;
    }

    public String getOrientation() {
        return orientation;
    }

    public String getRenderorder() {
        return renderorder;
    }

    public String getTiledversion() {
        return tiledversion;
    }

    public int getTileheight() {
        return tileheight;
    }

    public JsonArray getTilesets() {
        return tilesets;
    }

    public int getTilewidth() {
        return tilewidth;
    }

    public String getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    public int getWidth() {
        return width;
    }

    /*
    // Method to display map details
    public static void printMapDetails(Map map) {
        if (map == null) {
            System.out.println("Error reading map.");
            return;
        }

        System.out.println("Map Details:");
        System.out.println("Compression Level: " + map.compressionlevel);
        System.out.println("Height: " + map.height);
        System.out.println("Infinite: " + map.infinite);
        System.out.println("Next Layer ID: " + map.nextlayerid);
        System.out.println("Orientation: " + map.orientation);
        System.out.println("Tile Height: " + map.tileheight);
        System.out.println("Tile Width: " + map.tilewidth);

        System.out.println("\nTilesets:");
        for (Object tsObj : map.tilesets) {
            TileSet ts = (TileSet) tsObj;
            System.out.println("  - First GID: " + ts.firstgid + ", Source: " + ts.source);
        }

        System.out.println("\nLayers:");
        for (Object layerObj : map.layers) {
            Layer layer = (Layer) layerObj;
            System.out.println("  - Layer Name: " + layer.name);
            System.out.println("    Type: " + layer.type);
            System.out.println("    Visible: " + layer.visible);
            System.out.println("    Dimensions: " + layer.width + "x" + layer.height);
            System.out.println("    Chunks:");
            for (Object chunkObj : layer.chunks) {
                Chunk chunk = (Chunk) chunkObj;
                System.out.println("      - Chunk X: " + chunk.x + ", Y: " + chunk.y + ", Width: " + chunk.width + ", Height: " + chunk.height);
                System.out.println("        Data: " + chunk.data);
            }
        }
    }
    */
}

