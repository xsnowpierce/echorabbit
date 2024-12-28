package snow.pierce.Renderer;

import snow.pierce.Util.AssetPool;

public class FontReader {

    private static SpriteSheet fontSheet;

    public static void CreateFont(){
        String url = AssetPool.getImagesPath() + "font.png";
        fontSheet = new SpriteSheet(new Texture(url), 6, 8, 68, 0);
        AssetPool.addSpriteSheet(url, fontSheet);
    }

    public static Sprite GetCharacter(char Character) {
        if((int) Character == 32)
            return fontSheet.GetSprite(67);
        if((int) Character >= 65 && (int) Character <= 90){
            return fontSheet.GetSprite((int) Character - 65);
        }
        else if((int) Character >= 97 && (int) Character <= 122){
            return fontSheet.GetSprite((int) Character - 97 + 26);
        }
        else if((int) Character >= 48 && (int) Character <= 57){
            return fontSheet.GetSprite((int) Character - 48 + 26 + 26);
        }
        else {
            assert false : "Font was given a character out of bounds.";
        }
        return null;
    }
}
