package snow.pierce.Util;

public enum SpriteLayer {
    BACKGROUND_LAYER(-5), ENTITY_LAYER(0), UI_LAYER(5);

    private final int value;
    private SpriteLayer(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
