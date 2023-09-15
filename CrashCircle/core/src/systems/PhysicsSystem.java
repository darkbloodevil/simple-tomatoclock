package systems;

import components.*;
import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import singleton.PhysicsTool;
import singleton.Settings;
import structs.MessageNode;

import java.util.Arrays;

public class PhysicsSystem extends EntitySystem {

    String message_name = "PhysicsSystem";


    public static World PHYSICS_WORLD;

    //float kind message needs to add an F
    public static final String MESSAGE_APPLY_FORCE = "apply_force",
            MESSAGE_END = "end",
            MESSAGE_DIRECTION_X = "direction_x", MESSAGE_DIRECTION_Y = "direction_y",
            MESSAGE_POSITION_X = "position_x", MESSAGE_POSITION_Y = "position_y",
            MESSAGE_STRENGTH = "strength";


    private static float TIME_STEP = .005f;
    private static int VELOCITY_ITERATIONS = 8;
    private static int POSITION_ITERATIONS = 10;

    private ComponentMapper<PhysicsBodyComponent> physicsBodyMapper;
    private ComponentMapper<TransformComponent> transformMapper;

    public static int GRAVITY = 0;

    private Engine engine;

    private float accumulator = 0;

    public PhysicsSystem(final Engine engine) {
        this.engine = engine;

        PHYSICS_WORLD = new World(new Vector2(0, GRAVITY), true);
        physicsBodyMapper = ComponentMapper.getFor(PhysicsBodyComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);

        PHYSICS_WORLD.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }


    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= TIME_STEP) {
            PHYSICS_WORLD.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            //PHYSICS_WORLD.step(deltaTime, 2, 2);
            accumulator -= TIME_STEP;
        }


        update_body_data();


    }

    private void update_body_data() {
        // Create an array to be filled with the bodies
        // (better don't create a new one every time though)
        Array<Body> bodies = new Array<Body>();
        // Now fill the array with all bodies
        PHYSICS_WORLD.getBodies(bodies);


        for (Body b : bodies) {
            // Get the body's user data - in this example, our user
            // data is an instance of the Entity class
            Entity e = (Entity) b.getUserData();

            circle_world_transform(b,500,500);

            if (e != null) {
                TransformComponent tc = transformMapper.get(e);
                if (tc != null) {
                    // Update the entities/sprites position and angle
                    tc.pos.x = b.getPosition().x;
                    tc.pos.y = b.getPosition().y;
                    // We need to convert our angle from radians to degrees
                    tc.rotation = b.getAngle();
                    //Gdx.app.log("my","position "+b.getPosition().x+" "+ b.getPosition().y);
                }

                PhysicsBodyComponent pbc = physicsBodyMapper.get(e);
                if (pbc != null) {
                    if (pbc.physics_message != null) {
                        message_decoder(b, pbc.physics_message);
                    }
                }

            }
        }
    }

    /**
     * to make the world a circle
     * @param b
     * @param world_width
     * @param world_height
     */
    private void circle_world_transform(Body b,int world_width, int world_height){
        b.setTransform(b.getPosition().x % world_width, b.getPosition().y % world_height, 0);
    }

    /**
     * decoder, to choose which instruct function
     *
     * @param body
     * @param message
     */
    private void message_decoder(Body body, MessageNode message) {
        if (message.stringMessage != null) {
            switch (message.stringMessage) {
                case MESSAGE_APPLY_FORCE: {
                    instruct_apply_force(body, message);
                    break;
                }
            }
        }
    }

    private void instruct_apply_force(Body body, MessageNode message) {
        Vector2 dir = new Vector2(0, 0), pos = body.getPosition();
        boolean is_wake = true;
        int strength = -1;

        MessageNode temp = message;
        //skip the head node (which is processed in the upper structure )
        while (temp.has_next) {
            temp = temp.next;
            boolean end_while = false;
            if (temp.stringMessage != null) {
                switch (temp.stringMessage) {
                    case MESSAGE_DIRECTION_X: {
                        dir.x = temp.intMessage;
                        break;
                    }
                    case MESSAGE_DIRECTION_Y: {
                        dir.y = temp.intMessage;
                        break;
                    }
                    case MESSAGE_STRENGTH: {
                        strength = temp.intMessage;
                        break;
                    }
                    case MESSAGE_END: {
                        end_while = true;
                        // if this part of message ends while there are still another message left,
                        // go ahead and process them in the decoder
                        if (temp.has_next) {
                            message_decoder(body, temp.next);
                        }
                        break;
                    }
                }
            }
            if (end_while) {
                break;
            }
        }
        if (strength != -1) {
            dir = PhysicsTool.standard_strength(dir, strength);
        }

        body.applyForce(dir,
                pos, is_wake);
    }

}
