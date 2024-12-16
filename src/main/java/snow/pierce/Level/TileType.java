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

    public static TileType fromString(String value) {
        for (TileType type : TileType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant for value " + value);
    }
}
