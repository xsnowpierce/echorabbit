package snow.pierce.Level;

import com.github.cliftonlabs.json_simple.JsonArray;

import java.util.List;

public class Layer {
    int id;
    String name;
    String type;
    boolean visible;
    int width;
    int height;
    int x;
    int y;
    Chunk[] chunks;

    public Layer(int id, String name, String type, boolean visible, int width, int height, int x, int y, Chunk[] chunks) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.visible = visible;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.chunks = chunks;
    }

    public Chunk[] getChunks() {
        return chunks;
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
}
