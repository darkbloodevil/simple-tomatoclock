package systems;


import components.BackgroundComponent;
import components.TransformComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class BackgroundSystem extends IteratingSystem {
    private OrthographicCamera camera;
    private ComponentMapper<TransformComponent> tm;

    public BackgroundSystem() {
        super(Family.all(BackgroundComponent.class).get());
        tm = ComponentMapper.getFor(TransformComponent.class);
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent t = tm.get(entity);
        t.pos.set(camera.position.x, camera.position.y, 10.0f);
    }
}
