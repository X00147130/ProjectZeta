package com.mygdx.projectZeta.Sprites.Enemies;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.projectZeta.Scenes.Screens.PlayScreen;

public abstract class Enemy extends Sprite {

    //world and screen generation
    protected World world;
    protected PlayScreen screen;

    //body
    public Body krakenBody;
    public Body truhanBody;

    //movement
    public Vector2 velocity;
    public Vector2 attackStop;

    public Enemy(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        if(Gdx.app.getType() == Application.ApplicationType.Android)
            velocity = new Vector2(-0.19f, 0f);
        else{
            velocity = new Vector2(-0.1f,0f);
        }
        attackStop = new Vector2(0, 0);
    }

    protected abstract void defineEnemy();

    public abstract void update(float dt);

    public abstract void shot();

    public void reverseVelocity(boolean x, boolean y) {
        if (x)
            velocity.x = -velocity.x;
        if (y)
            velocity.y = -velocity.y;
    }
}
