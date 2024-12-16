package snow.pierce.Level;

import com.github.cliftonlabs.json_simple.JsonArray;

import java.math.BigDecimal;

public class Chunk {

    int chunkId;
    int[] tileArray;
    int height;
    int width;
    int x;
    int y;

    public Chunk(int chunkId, JsonArray data, int height, int width, int x, int y) {
        this.chunkId = chunkId;
        this.tileArray = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            tileArray[i] = ((BigDecimal) data.get(i)).intValue();
        }
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
    }

    public int[]  getTileArray() {
        return tileArray;
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
