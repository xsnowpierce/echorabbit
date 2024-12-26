package snow.pierce.Level;

public enum TileType {

    WALKABLE_TILE("walkable"), IMPASSABLE_TILE("impassable");

    private final String value;

    TileType(String value) {
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

    public static TileType getTileTypeFromTileID(int tileID) {
        return switch (tileID) {
            case 1 -> WALKABLE_TILE; // light grass
            case 2 -> IMPASSABLE_TILE; // dark grass
            default -> WALKABLE_TILE;
        };
    }
}
