package systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import components.ControllableComponent;
import components.PhysicsBodyComponent;
import components.TextureComponent;
import components.TransformComponent;
import inputHandling.MyControllerListener;
import inputHandling.MyControllerState;
import structs.MessageNode;

/**
 * the system is about the control to the game entities, both from the player and the ai
 */
public class ControlSystem extends IteratingSystem {
    Controller controller;
    MyControllerState controllerState;

    private Array<Entity> controllerQueue;

    boolean B_INPUTTING = false;

    private ComponentMapper<ControllableComponent> controllableMapper;
    private ComponentMapper<PhysicsBodyComponent> physicsBodyMapper;


    public ControlSystem() {
        super(Family.all(ControllableComponent.class).get());

        controllerQueue = new Array<Entity>();

        controllableMapper = ComponentMapper.getFor(ControllableComponent.class);

        physicsBodyMapper = ComponentMapper.getFor(PhysicsBodyComponent.class);

        controller = Controllers.getControllers().get(0);
        this.controllerState = new MyControllerState(controller);
        controller.addListener(new MyControllerListener(this.controllerState));


    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        MessageNode player_msg = new MessageNode();
        if (controllerState.RIGHT_TRIGGER >= 4) {
            player_msg.stringMessage = PhysicsSystem.MESSAGE_APPLY_FORCE;

            player_msg.put(new MessageNode(PhysicsSystem.MESSAGE_DIRECTION_X,
                    controllerState.AXIS_LEFT_X,
                    new MessageNode(PhysicsSystem.MESSAGE_DIRECTION_Y,
                            -controllerState.AXIS_LEFT_Y,
                            new MessageNode(PhysicsSystem.MESSAGE_STRENGTH, controllerState.RIGHT_TRIGGER,
                                    new MessageNode(PhysicsSystem.MESSAGE_END)))));

        }

        for (Entity e : controllerQueue
        ) {
            ControllableComponent ctrl_able = controllableMapper.get(e);
            PhysicsBodyComponent physics_body = physicsBodyMapper.get(e);
            if (physics_body != null && ctrl_able != null) {
                physics_body.physics_message = player_msg;
            }
        }

        controllerQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        controllerQueue.add(entity);
    }
}
