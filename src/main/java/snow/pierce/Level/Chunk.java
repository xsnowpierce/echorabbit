package snow.pierce.Level;

import com.github.cliftonlabs.json_simple.JsonArray;

public class Chunk {

    JsonArray data;
    int height;
    int width;
    int x;
    int y;

    public Chunk(JsonArray data, int height, int width, int x, int y) {
        this.data = data;
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
    }

    public JsonArray getData() {
        return data;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
