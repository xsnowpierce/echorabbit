package snow.pierce.Renderer;

import org.joml.Vector2f;
import snow.pierce.Util.SpriteNormal;

import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {

    private Texture texture;
    private List<Sprite> sprites;

    public SpriteSheet(Texture texture, int spriteWidth, int spriteHeight, int numSprites, int spacing){
        this.sprites = new ArrayList<>();

        this.texture = texture;

        int currentX = 0;
        int currentY = ((int) texture.GetImageSize().y) - spriteHeight;

        for(int i = 0; i < numSprites; i++){
            float topY = (currentY + spriteHeight) / texture.GetImageSize().y;
            float rightX = (currentX + spriteWidth) / texture.GetImageSize().x;
            float leftX = currentX / texture.GetImageSize().x;
            float bottomY = currentY / texture.GetImageSize().y;

            Vector2f[] texCoords = {
                    new Vector2f(rightX, topY),
                    new Vector2f(rightX, bottomY),
                    new Vector2f(leftX, bottomY),
                    new Vector2f(leftX, topY)
            };

            Sprite sprite = new Sprite(this.texture, texCoords);
            sprites.add(sprite);

            currentX += spriteWidth + spacing;
            if(currentX >= texture.GetImageSize().x){
                currentX = 0;
                currentY -= spriteHeight + spacing;
            }
        }
    }

    public Sprite GetSprite(int index){
        //if(index < 0 || index >= sprites.size()){
        //    System.err.println("Tried to access sprite at " + index + ", when array size is only " + sprites.size());
        //    return GetSprite(0);
        //}
        return sprites.get(index);
    }

    public Sprite[] GetSprites(){
        return sprites.toArray(new Sprite[0]);
    }
}
