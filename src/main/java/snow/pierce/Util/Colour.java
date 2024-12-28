package snow.pierce.Util;

import org.joml.Vector4f;

public enum Colour {

    RED(new Vector4f(1, 0, 0, 1)),
    GREEN(new Vector4f(0, 1, 0, 1)),
    BLUE(new Vector4f(0, 0, 1, 1)),
    BLACK(new Vector4f(0, 0, 0, 1)),
    WHITE(new Vector4f(1, 1, 1, 1));

    private final Vector4f colour;

    Colour(Vector4f colour) {
        this.colour = colour;
    }

    public Vector4f getVector4f() {
        return colour;
    }

}
