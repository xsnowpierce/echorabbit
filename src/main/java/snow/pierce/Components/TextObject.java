package snow.pierce.Components;

import org.joml.Vector2f;
import org.joml.Vector4f;
import snow.pierce.Renderer.FontReader;
import snow.pierce.Renderer.Window;
import snow.pierce.UI.UIObject;
import snow.pierce.Util.Colour;
import snow.pierce.Util.SpriteLayer;

public class TextObject extends UIObject {

    private final int FONT_SPRITE_SIZE = 8;
    private Vector4f colour = new Vector4f();

    private final String text;

    public TextObject(String name) {
        super(name);
        text = "New text";
        colour = new Vector4f(1f, 1f, 1f, 1f);
        CreateText();
    }

    public TextObject(String text, Vector2f position) {
        super("text");
        transform.position = position;
        this.text = text;

        CreateText();
    }

    public TextObject(String text, Vector2f position, Vector4f colour) {
        super("text");
        transform.position = position;
        this.text = text;
        this.colour = colour;
        CreateText();
    }

    public TextObject(String text, Vector2f position, Colour colour) {
        super("text");
        transform.position = position;
        this.text = text;
        this.colour = colour.getVector4f();
        CreateText();
    }

    private void CreateText(){
        Vector2f currentPosition = transform.position;
        int positionXAdd = 0;
        for(int i = 0; i < text.length(); i++) {

            GameObject letter = new GameObject("Word", new Transform(new Vector2f(currentPosition.x + positionXAdd, currentPosition.y),
                    new Vector2f(FONT_SPRITE_SIZE, FONT_SPRITE_SIZE)), SpriteLayer.UI_LAYER);

            letter.addComponent(new SpriteRenderer(FontReader.GetCharacter(text.charAt(i)), colour));
            letter.addComponent(new FollowCamera(new Vector2f(currentPosition.x + positionXAdd, currentPosition.y)));

            Window.getScene().addGameObjectToScene(letter);

            positionXAdd += FONT_SPRITE_SIZE + 1;
        }
    }
}
