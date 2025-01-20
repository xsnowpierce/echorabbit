package snow.pierce.Components.Character;

import snow.pierce.Components.SpriteAnimator;
import snow.pierce.Components.SpriteRenderer;

public class CharacterSpriteAnimator extends SpriteAnimator {

    private final PlayerMovement playerMovement;
    private final CharacterSpriteMap characterSpriteMap;
    private CharacterSpriteMap.CharacterSpriteSet useSet;


    public CharacterSpriteAnimator(CharacterSpriteMap spriteMap, float animationSpeed, SpriteRenderer spriteRenderer, PlayerMovement playerMovement) {
        super(spriteMap.idleSet.downSprites, animationSpeed, spriteRenderer);
        this.characterSpriteMap = spriteMap;
        this.playerMovement = playerMovement;
        useSet = characterSpriteMap.idleSet;
    }


    @Override
    public void Update() {
        super.Update();

        if(playerMovement.IsMovementPressed())
            useSet = characterSpriteMap.walkingSet;
        else useSet = characterSpriteMap.idleSet;

        switch (playerMovement.getLastMovement()) {
            case UP -> {
                super.SetSprites(useSet.upSprites);
            }
            case DOWN -> {
                super.SetSprites(useSet.downSprites);
            }
            case LEFT -> {
                super.SetSprites(useSet.leftSprites);
            }
            case RIGHT -> {
                super.SetSprites(useSet.rightSprites);
            }
            case null, default -> {
                System.err.println("Animator was given value that doesnt fall within movement ranges.");
            }
        }
    }
}
