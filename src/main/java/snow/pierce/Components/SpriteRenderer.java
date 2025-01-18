package snow.pierce.Components;

import org.joml.Vector2f;
import org.joml.Vector4f;
import snow.pierce.Renderer.Sprite;
import snow.pierce.Renderer.Texture;

public class SpriteRenderer extends Component {

    private final Vector4f colour;
    private Sprite sprite;

    private Transform lastTransform;

    private boolean isDirty = true;

    @Override
    public void Start() {
        this.lastTransform = gameObject.transform.copy();
    }

    @Override
    public void Update(){
        if(!this.lastTransform.equals(this.gameObject.transform)){
            this.gameObject.transform.copy(this.lastTransform);

            isDirty = true;
        }
    }

    public SpriteRenderer(Sprite sprite) {
        this.colour = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.sprite = sprite;
    }

    public SpriteRenderer(Vector4f color) {
        this.colour = color;
        this.sprite = new Sprite(null);
    }

    public SpriteRenderer(java.awt.Color color) {
        Vector4f chosenColour = new Vector4f();
        chosenColour.x = color.getRed() / 255.0f;
        chosenColour.y = color.getGreen() / 255.0f;
        chosenColour.z = color.getBlue() / 255.0f;
        chosenColour.w = color.getAlpha() / 255.0f;
        this.colour = chosenColour;
        this.sprite = new Sprite(null);
    }

    public SpriteRenderer(Sprite sprite, Vector4f color) {
        this.colour = color;
        this.sprite = sprite;
    }

    public Vector4f getColour() {
        return this.colour;
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public Vector2f[] getTexCoords() {
        return sprite.getTexCoords();
    }

    public void setSprite(Sprite sprite){
        this.sprite = sprite;
        isDirty = true;
    }

    public void setColour(Vector4f colour){

        if(this.colour.equals(colour)) return;

        this.colour.set(colour);
        isDirty = true;
    }

    public boolean isDirty(){
        return isDirty;
    }

    public void setClean(){
        isDirty = false;
    }

    public void setDirty(){isDirty = true;}

    public void setTexture(Texture texture) {
        this.sprite.setTexture(texture);
    }
}
