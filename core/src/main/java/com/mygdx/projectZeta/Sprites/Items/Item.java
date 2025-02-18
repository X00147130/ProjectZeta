package com.mygdx.projectZeta.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.projectZeta.Scenes.Screens.PlayScreen;
import com.mygdx.projectZeta.Sprites.Player;
import com.mygdx.projectZeta.projectZeta;

public abstract class Item extends Sprite {
    protected PlayScreen screen;
    protected World world;
    protected boolean todestroy;
    protected boolean destroyed;
    protected Body body;
    public float itemStateTimer;

    public Item ( PlayScreen screen, float x, float y){
        this.screen = screen;
        this.world = screen.getWorld();
        itemStateTimer = 0;
        setPosition(x,y);
        setBounds(getX(), getY(),16 / projectZeta.PPM, 16 / projectZeta.PPM);
        defineItem();
        todestroy= false;
        destroyed= false;

    }

    public abstract void defineItem();
    public abstract void useItem(Player player);

    public void update(float dt){
        if(todestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
        }
        itemStateTimer += dt;
    }

    public void draw(Batch batch){
        if(!destroyed)
            super.draw(batch);
    }

    public void destroy() {
        todestroy = true;
    }

    public boolean getDestroyed(){
        return destroyed;
    }

}