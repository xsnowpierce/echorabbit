package snow.pierce.Components;

import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

public abstract class Component {



    public transient GameObject gameObject = null;

    public void Start() {}

    public void Update(){}

    public void Destroy(){}

    public void beginCollision(GameObject other, Contact contact, Vector2f hitNormal){}

    public void endCollision(GameObject other, Contact contact, Vector2f hitNormal){}

    public void preSolve(GameObject other, Contact contact, Vector2f hitNormal){}

    public void postSolve(GameObject other, Contact contact, Vector2f hitNormal){}
}
