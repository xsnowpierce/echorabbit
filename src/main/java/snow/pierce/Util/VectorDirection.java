package snow.pierce.Util;

import org.joml.Vector2f;

public enum VectorDirection {
    UP, DOWN, LEFT, RIGHT;

    public static VectorDirection GetDirectionFromVector2f(Vector2f value) {
        if (value.x == 0 && value.y > 0)
            return UP;
        else if (value.x == 0 && value.y < 0)
            return DOWN;
        else if (value.x < 0 && value.y == 0)
            return LEFT;
        else if (value.x > 0 && value.y == 0)
            return RIGHT;

        return DOWN;
    }
}
