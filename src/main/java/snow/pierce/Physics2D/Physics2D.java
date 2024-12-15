package snow.pierce.Physics2D;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.joml.Vector2f;
import snow.pierce.Components.GameObject;
import snow.pierce.Components.Physics2D.Box2DCollider;
import snow.pierce.Components.Physics2D.Rigidbody2D;
import snow.pierce.Components.Transform;
import snow.pierce.Util.Time;

public class Physics2D {

    private Vec2 gravity = new Vec2(0, 0);
    private World world = new World(gravity);

    private float physicsTime = 0.0f;
    private float physicsTimeStep = 1.0f / 60f;

    private int velocityIterations = 8;
    private int positionIterations = 3;

    public void Update(){
        physicsTime += Time.deltaTime;
        if(physicsTime >= 0.0f){
            physicsTime -= physicsTimeStep;
            world.step(physicsTimeStep, velocityIterations, positionIterations);
        }
    }

    public void add(GameObject object){
        Rigidbody2D rbody = object.getComponent(Rigidbody2D.class);
        if (rbody == null || rbody.getRawBody() == null) {
            System.err.println("Tried to add object with null rigidbody to physics engine.");
            return;
        }

        Transform transform = object.transform;

        BodyDef bodyDef = new BodyDef();
        bodyDef.angle = 0;
        bodyDef.position.set(transform.position.x, transform.position.y);
        bodyDef.angularDamping = rbody.getAngularDamping();
        bodyDef.linearDamping = rbody.getLinearDamping();
        bodyDef.fixedRotation = rbody.isFixedRotation();
        bodyDef.bullet = rbody.isContinuousCollision();

        switch(rbody.getBodyType()){
            case KINEMATIC -> bodyDef.type = BodyType.KINEMATIC;
            case STATIC -> bodyDef.type = BodyType.STATIC;
            case DYNAMIC -> bodyDef.type = BodyType.DYNAMIC;
        }

        PolygonShape shape = new PolygonShape();
        Box2DCollider box2DCollider = object.getComponent(Box2DCollider.class);

        if(box2DCollider == null){
            System.err.println("Tried to add box2D collider with null collider object.");
            return;
        }

        Vector2f halfSize = new Vector2f(box2DCollider.getHalfSize()).mul(0.5f);
        Vector2f offset = box2DCollider.getOffset();
        Vector2f origin = new Vector2f(box2DCollider.getOrigin());
        shape.setAsBox(halfSize.x, halfSize.y, new Vec2(origin.x, origin.y), 0);

        Vec2 position = bodyDef.position;
        float xPosition = position.x + offset.x;
        float yPosition = position.y + offset.y;
        bodyDef.position.set(xPosition, yPosition);

        Body body = this.world.createBody(bodyDef);
        rbody.setRawBody(body);
        body.createFixture(shape, rbody.getMass());
    }
}
