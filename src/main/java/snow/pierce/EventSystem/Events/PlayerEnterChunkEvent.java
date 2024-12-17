package snow.pierce.EventSystem.Events;

import org.joml.Vector2i;

public class PlayerEnterChunkEvent extends Event {

    private final Vector2i chunkEntered;

    public PlayerEnterChunkEvent(Vector2i chunkEntered) {
        this.chunkEntered = chunkEntered;
    }

    public Vector2i getChunkEntered() {
        return chunkEntered;
    }
}
