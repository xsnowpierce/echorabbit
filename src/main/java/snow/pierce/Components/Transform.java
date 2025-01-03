package snow.pierce.Components;

import org.joml.Vector2f;

public class Transform {

    public Vector2f position;
    public Vector2f scale;
    public float rotation = 0.0f;

    public Transform() {
        init(new Vector2f(), new Vector2f());
    }

    public Transform(Vector2f position) {
        init(position, new Vector2f());
    }

    public Transform(Vector2f position, Vector2f scale) {
        init(position, scale);
    }

    public void init(Vector2f position, Vector2f scale) {
        this.position = position;
        this.scale = scale;
    }

    public Transform copy(){
        return new Transform(new Vector2f(this.position), new Vector2f(this.scale));
    }

    public void copy(Transform copyTo){
        copyTo.position.set(this.position);
        copyTo.scale.set(this.position);
    }

    @Override
    public boolean equals(Object o){
        if(o == null) return false;

        if(!(o instanceof Transform newTransform)) return false;

        return newTransform.position.equals(this.position) && newTransform.scale.equals(this.scale);
    }
}
