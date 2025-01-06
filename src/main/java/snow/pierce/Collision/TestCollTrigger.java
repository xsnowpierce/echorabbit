package snow.pierce.Collision;

public class TestCollTrigger extends TriggerListener {

    @Override
    public void PlayerEnterTrigger() {
        System.out.println("hi");
    }

    @Override
    public void PlayerStayTrigger() {
        System.out.println("sup");
    }

    @Override
    public void PlayerExitTrigger() {
        System.out.println("bye");
    }
}
