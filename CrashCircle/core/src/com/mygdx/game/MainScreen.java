package com.mygdx.game;

import singleton.Settings;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import systems.*;

public class MainScreen extends ScreenAdapter {
    final MyGdxGame game;
    OrthographicCamera camera;

    PooledEngine engine;
    GameWorld gameWorld;
    public MainScreen(final MyGdxGame game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Settings.SCREEN_W, Settings.SCREEN_H);
        this.engine=new PooledEngine();

        initialize_engine();

        engine.getSystem(BackgroundSystem.class).setCamera(engine.getSystem(RenderingSystem.class).getCamera());

        this.gameWorld=new GameWorld(engine);
        this.gameWorld.create();
    }
    private void initialize_engine(){
        engine.addSystem(new PhysicsSystem(engine));
        engine.addSystem(new ControlSystem());
        engine.addSystem(new CameraSystem());
        engine.addSystem(new BackgroundSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new RenderingSystem(game.batch));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
