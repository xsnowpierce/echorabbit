package snow.pierce.Components;

import org.joml.Vector2f;
import snow.pierce.Renderer.FontReader;
import snow.pierce.Renderer.Window;

import java.util.ArrayList;
import java.util.List;

public class TextObject extends GameObject {

    private final int FONT_SPRITE_SIZE = 8;
    private final int FONT_Z_INDEX = 1;

    private String text;

    List<GameObject> letterList = new ArrayList<>();

    public TextObject(String name) {
        super(name);
        text = "New text";
        CreateText();
    }

    public TextObject(String text, Vector2f position) {
        super("text");
        transform.position = position;
        this.text = text;
        CreateText();
    }

    private void CreateText(){
        Vector2f currentPosition = transform.position;
        int positionXAdd = 0;
        for(int i = 0; i < text.length(); i++) {

            GameObject letter = new GameObject("Word", new Transform(new Vector2f(currentPosition.x + positionXAdd, currentPosition.y),
                    new Vector2f(FONT_SPRITE_SIZE, FONT_SPRITE_SIZE)), FONT_Z_INDEX);

            letter.addComponent(new SpriteRenderer(FontReader.GetCharacter(text.charAt(i))));

            Window.getScene().addGameObjectToScene(letter);

            positionXAdd += FONT_SPRITE_SIZE + 1;

            letterList.add(letter);
        }
    }
}
