package components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import structs.MessageNode;

public class PhysicsBodyComponent implements Component {
    public Body body = null;
    public MessageNode physics_message=null;
//    public BodyDef bodyDef = null;
//    public FixtureDef fixtureDef = null;
//
//    public Fixture fixture=null;
}
