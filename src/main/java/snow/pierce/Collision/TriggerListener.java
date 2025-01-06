package snow.pierce.Collision;

import snow.pierce.Components.Component;

public abstract class TriggerListener extends Component {

    public abstract void PlayerEnterTrigger();

    public abstract void PlayerStayTrigger();

    public abstract void PlayerExitTrigger();

    @Override
    public void Start() {
        AABB aabb = gameObject.getComponent(AABB.class);
        if (aabb == null) {
            System.out.println("Warning: TriggerListener placed on a gameobject without an AABB component.");
            return;
        }
        aabb.AddTriggerListener(this);
    }

    @Override
    public void Destroy() {
        AABB aabb = gameObject.getComponent(AABB.class);
        if (aabb == null) {
            return;
        }
        aabb.RemoveTriggerListener(this);
    }
}
