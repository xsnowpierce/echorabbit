package snow.pierce.Level;

public class TileSet {
    int firstgid;
    String source;

    public TileSet(int firstgid, String source) {
        this.firstgid = firstgid;
        this.source = source;
    }

    public int getFirstgid() {
        return firstgid;
    }

    public String getSource() {
        return source;
    }
}
