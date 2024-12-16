package snow.pierce.Level;

import org.joml.Vector2i;

import java.util.Map;

public class Layer {
    int id;
    String name;
    String type;
    boolean visible;
    int width;
    int height;
    int x;
    int y;
    Map<Vector2i, Chunk> chunkMap;
    Chunk[] chunkArray;
    int layerWidth;

    public Layer(int id, String name, String type, boolean visible, int width, int height, int x, int y, Map<Vector2i, Chunk> chunkMap, Chunk[] chunkArray, int layerWidth) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.visible = visible;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.chunkMap = chunkMap;
        this.chunkArray = chunkArray;
        this.layerWidth = layerWidth;
    }

    public Map<Vector2i, Chunk> getChunkMap() {
        return chunkMap;
    }

    public Chunk[] getChunkArray() {
        return chunkArray;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isVisible() {
        return visible;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getLayerWidth() {
        return layerWidth;
    }
}
