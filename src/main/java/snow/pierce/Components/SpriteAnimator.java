package snow.pierce.Components;

import snow.pierce.Renderer.Sprite;

import java.util.Arrays;

public class SpriteAnimator extends Component {

    private float animationSpeed;
    protected Sprite[] sprites;
    private SpriteRenderer spriteRenderer;
    protected int currentSpriteIndex;
    protected float timeUntilNextSprite;

    public SpriteAnimator(Sprite[] sprites, float animationSpeed, SpriteRenderer spriteRenderer) {
        this.animationSpeed = animationSpeed;
        this.sprites = sprites;
        this.spriteRenderer = spriteRenderer;
    }

    public void SetSprites(Sprite[] sprites) {

        if(Arrays.equals(this.sprites, sprites)) return;

        this.sprites = sprites;
        currentSpriteIndex = 0;
        IncrementSprite();
    }

    public void SetAnimationSpeed(float animationSpeed) {
        this.animationSpeed = animationSpeed;
    }

    @Override
    public void Update() {
        if(timeUntilNextSprite >= 60)
            IncrementSprite();
        else {
            timeUntilNextSprite += animationSpeed;
        }
    }

    private void IncrementSprite(){
        currentSpriteIndex ++;
        timeUntilNextSprite = 0;

        if(currentSpriteIndex >= sprites.length){
            currentSpriteIndex = 0;
        }

        spriteRenderer.setSprite(sprites[currentSpriteIndex]);
    }
}
