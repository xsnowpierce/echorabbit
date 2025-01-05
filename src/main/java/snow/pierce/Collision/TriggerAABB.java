package snow.pierce.Collision;

import org.joml.Vector2f;

public class TriggerAABB extends AABB {

    private boolean PLAYER_INSIDE;
    private AABB player;

    public TriggerAABB(Vector2f center, Vector2f half_extent) {
        super(center, half_extent);
    }


    public void playerTouchingTrigger(AABB player) {
        if (!PLAYER_INSIDE) {
            PlayerEnterTrigger();
        }
        PLAYER_INSIDE = true;
        this.player = player;
    }

    protected void PlayerStayTrigger() {

    }

    protected void PlayerEnterTrigger() {

    }

    protected void PlayerExitTrigger() {

    }

    @Override
    public void Update() {
        if (PLAYER_INSIDE) {
            if (!getCollision(player).isIntersecting) {
                PlayerExitTrigger();
                PLAYER_INSIDE = false;
            } else PlayerStayTrigger();
        }
    }
}
