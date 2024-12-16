package snow.pierce.Level;

public enum TileType {

    TILE("tile");

    private final String value;

    private TileType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
