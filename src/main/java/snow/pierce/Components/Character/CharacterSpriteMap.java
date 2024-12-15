package snow.pierce.Components.Character;

import snow.pierce.Renderer.Sprite;

public class CharacterSpriteMap {

    public CharacterSpriteSet idleSet;
    public CharacterSpriteSet walkingSet;

    public static class CharacterSpriteSet {

        public Sprite[] upSprites;
        public Sprite[] downSprites;
        public Sprite[] leftSprites;
        public Sprite[] rightSprites;


        public CharacterSpriteSet(Sprite[] upSprites, Sprite[] downSprites, Sprite[] leftSprites, Sprite[] rightSprites) {
            this.upSprites = upSprites;
            this.downSprites = downSprites;
            this.leftSprites = leftSprites;
            this.rightSprites = rightSprites;
        }
    }

    public CharacterSpriteMap(CharacterSpriteSet idleSet, CharacterSpriteSet walkingSet) {
        this.idleSet = idleSet;
        this.walkingSet = walkingSet;
    }
}
