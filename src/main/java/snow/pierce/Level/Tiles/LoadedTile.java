package snow.pierce.Level.Tiles;

import org.joml.Vector2f;
import snow.pierce.Collision.AABB;
import snow.pierce.Components.GameObject;
import snow.pierce.Components.SpriteRenderer;
import snow.pierce.Components.Transform;
import snow.pierce.Level.TileType;
import snow.pierce.Renderer.Sprite;
import snow.pierce.Util.SpriteLayer;

public class LoadedTile extends GameObject {

    TileType tileType;

    public LoadedTile(String name, Transform transform, TileType tileType, Sprite sprite) {

        super(name, transform, SpriteLayer.BACKGROUND_LAYER);

        this.tileType = tileType;
        this.addComponent(new SpriteRenderer(sprite));

        if (tileType == TileType.IMPASSABLE_TILE) {
            this.addComponent(new AABB(transform.position, new Vector2f(transform.scale.x / 2, transform.scale.y / 2)));
        }

    }

    public AABB getBoundingBox() {
        if (tileType == TileType.IMPASSABLE_TILE) {
            return getComponent(AABB.class);
        } else return null;
    }
}
