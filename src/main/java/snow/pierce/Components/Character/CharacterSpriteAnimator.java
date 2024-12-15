package snow.pierce.Components.Character;

import org.joml.Vector2f;
import snow.pierce.Components.SpriteAnimator;
import snow.pierce.Components.SpriteRenderer;

import java.util.Objects;

public class CharacterSpriteAnimator extends SpriteAnimator {

    private final PlayerMovement playerMovement;
    private CharacterSpriteMap characterSpriteMap;
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

        switch(GetMovementFromVector2f(playerMovement.getLastMovement())) {
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

        //System.out.println("character anim update: sprite " + (super.currentSpriteIndex + 1) + "/" + super.sprites.length + ", " +
        //        "time until next: " + super.timeUntilNextSprite + "s, isMovementPressed: " + playerMovement.IsMovementPressed());
    }

    public enum Movement{
        LEFT, RIGHT, UP, DOWN
    }

    public Movement GetMovementFromVector2f(Vector2f value){
        if(Objects.equals(value, new Vector2f(0, 1))){
            return Movement.UP;
        }
        else if(Objects.equals(value, new Vector2f(0, -1))){
            return Movement.DOWN;
        }
        else if (Objects.equals(value, new Vector2f(1, 0))){
            return Movement.RIGHT;
        }
        else if(Objects.equals(value, new Vector2f(-1, 0))){
            return Movement.LEFT;
        }
        return Movement.DOWN;
    }
}
