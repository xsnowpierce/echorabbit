package snow.pierce.Util;

import snow.pierce.Components.Character.CharacterSpriteMap;
import snow.pierce.Renderer.Sprite;
import snow.pierce.Renderer.SpriteSheet;

public class PlayerSpriteSet {

    public static CharacterSpriteMap GetPlayerSpriteMap() {

        SpriteSheet spriteSheet = AssetPool.getSpriteSheet(AssetPool.getImagesPath() + "character.png");

        Sprite[] idleUp = new Sprite[]{
                spriteSheet.GetSprite(12)
        };
        Sprite[] idleDown = new Sprite[]{
                spriteSheet.GetSprite(0)
        };
        Sprite[] idleLeft = new Sprite[]{
                spriteSheet.GetSprite(8)
        };
        Sprite[] idleRight = new Sprite[]{
                spriteSheet.GetSprite(4)
        };

        Sprite[] walkUp = new Sprite[]{
                spriteSheet.GetSprite(12),
                spriteSheet.GetSprite(13),
                spriteSheet.GetSprite(14),
                spriteSheet.GetSprite(15)
        };
        Sprite[] walkDown = new Sprite[]{
                spriteSheet.GetSprite(0),
                spriteSheet.GetSprite(1),
                spriteSheet.GetSprite(2),
                spriteSheet.GetSprite(3)
        };
        Sprite[] walkLeft = new Sprite[]{
                spriteSheet.GetSprite(8),
                spriteSheet.GetSprite(9),
                spriteSheet.GetSprite(10),
                spriteSheet.GetSprite(11)
        };
        Sprite[] walkRight = new Sprite[]{
                spriteSheet.GetSprite(4),
                spriteSheet.GetSprite(5),
                spriteSheet.GetSprite(6),
                spriteSheet.GetSprite(7)
        };

        CharacterSpriteMap.CharacterSpriteSet idleSet = new CharacterSpriteMap.CharacterSpriteSet(
                idleUp, idleDown, idleLeft, idleRight

        );
        CharacterSpriteMap.CharacterSpriteSet walkingSet = new CharacterSpriteMap.CharacterSpriteSet(
                walkUp, walkDown, walkLeft, walkRight
        );

        return new CharacterSpriteMap(idleSet, walkingSet);
    }

}
