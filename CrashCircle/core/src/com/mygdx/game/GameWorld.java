package com.mygdx.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import components.*;
import singleton.Assets;
import singleton.Settings;
import systems.PhysicsSystem;
import systems.RenderingSystem;

import java.util.Random;

public class GameWorld {
    Random random=new Random(10);
    private PooledEngine engine;
    public GameWorld(PooledEngine engine){
        this.engine=engine;
    }
    public void create(){
        create_player(Settings.SCREEN_W/2,Settings.SCREEN_H/2);
        create_player2(Settings.SCREEN_W/4,Settings.SCREEN_H/2);
        create_player2(Settings.SCREEN_W/4,Settings.SCREEN_H/4);
        create_player2(Settings.SCREEN_W*3/4,Settings.SCREEN_H/4);
        create_player2(Settings.SCREEN_W*3/4,Settings.SCREEN_H*3/4);
        for (int i = 0; i < 1000; i++) {
            create_player2((int)(Settings.SCREEN_W*10*random.nextFloat()),
                    (int)(Settings.SCREEN_H*10*random.nextFloat()));
        }
        create_background();
    }
    public void create_player(int x,int y){
        Entity playerEntity = engine.createEntity();

        PhysicsBodyComponent physicsBody = engine.createComponent(PhysicsBodyComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        ControllableComponent playerCharacter = engine.createComponent(ControllableComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        CameraComponent cam=engine.createComponent(CameraComponent.class);

        texture.region = new TextureRegion(Assets.get_character());

        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(x* Settings.PIXELS_TO_METRES, y*Settings.PIXELS_TO_METRES);

        // Create our body in the world using our body definition
        physicsBody.body = PhysicsSystem.PHYSICS_WORLD.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(50*Settings.PIXELS_TO_METRES/2f);
        //shape.setAsBox((int)(Settings.CHARACTER_W*Settings.PIXELS_TO_METRES/2),(int)(Settings.CHARACTER_H*Settings.PIXELS_TO_METRES/2));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = .00f;

        Fixture fixture = physicsBody.body.createFixture(fixtureDef);
        fixture.setFriction(0.05f);
        //physicsBody.body.setFixedRotation(true);
        physicsBody.body.setUserData(playerEntity);

        position.pos.set(x*Settings.PIXELS_TO_METRES, y*Settings.PIXELS_TO_METRES, 0.0f);

        cam.camera = engine.getSystem(RenderingSystem.class).getCamera();
        cam.target = playerEntity;

        playerEntity.add(physicsBody);
        playerEntity.add(texture);
        playerEntity.add(playerCharacter);
        playerEntity.add(position);
        playerEntity.add(cam);

        engine.addEntity(playerEntity);
    }
    public void create_player2(int x,int y){
        Entity playerEntity = engine.createEntity();

        PhysicsBodyComponent physicsBody = engine.createComponent(PhysicsBodyComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);

        texture.region = new TextureRegion(Assets.get_character());

        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(x* Settings.PIXELS_TO_METRES, y*Settings.PIXELS_TO_METRES);

        // Create our body in the world using our body definition
        physicsBody.body = PhysicsSystem.PHYSICS_WORLD.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(50*Settings.PIXELS_TO_METRES/2f);
        //shape.setAsBox((int)(Settings.CHARACTER_W*Settings.PIXELS_TO_METRES/2),(int)(Settings.CHARACTER_H*Settings.PIXELS_TO_METRES/2));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = .00f;

        Fixture fixture = physicsBody.body.createFixture(fixtureDef);
        fixture.setFriction(0.05f);
        //physicsBody.body.setFixedRotation(true);
        physicsBody.body.setUserData(playerEntity);

        position.pos.set(x*Settings.PIXELS_TO_METRES, y*Settings.PIXELS_TO_METRES, 0.0f);


        playerEntity.add(physicsBody);
        playerEntity.add(texture);
        playerEntity.add(position);

        engine.addEntity(playerEntity);
    }

    private void create_background() {
        Entity entity = engine.createEntity();

        BackgroundComponent background = engine.createComponent(BackgroundComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);

        texture.region = new TextureRegion(Assets.get_background());

        entity.add(background);
        entity.add(position);
        entity.add(texture);

        engine.addEntity(entity);
    }
}
