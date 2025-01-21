package com.mygdx.projectZeta.Sprites.Items;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.projectZeta.Scenes.Screens.PlayScreen;
import com.mygdx.projectZeta.Sprites.Player;
import com.mygdx.projectZeta.projectZeta;

public class Hearts extends Item{
    public projectZeta zeta;
    private Animation<TextureRegion> health;
    private boolean healthJustTouched = false;

    public Hearts(projectZeta sfs, PlayScreen screen, float x, float y) {
        super(screen, x, y);
        this.zeta = sfs;
        this.zeta.setArea(screen.getArea());


        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i = 1; i <= 6; i++) {
            frames.add(sfs.getObjects().findRegion("heart" + i));
        }
        health = new Animation<TextureRegion>(0.2f, frames, Animation.PlayMode.NORMAL);
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            setBounds(getX(), getY(), 25 / projectZeta.PPM, 30 / projectZeta.PPM);
        }
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / projectZeta.PPM);
        fdef.filter.categoryBits = projectZeta.HEALTH_BIT;
        fdef.filter.maskBits = projectZeta.PLAYER_BIT |
                projectZeta.GROUND_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void useItem(Player player) {
        Gdx.app.log("Health", "Collision");
        destroy();
        world.clearForces();
        Player.setHitCounter(0);
    }



    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(health.getKeyFrame(super.itemStateTimer,true));
        if(healthJustTouched){
            onHit(screen.getPlayer());
        }
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    public void onHit(Player player) {
        if (healthJustTouched) {
            healthJustTouched = false;
            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                zeta.loadSound("audio/sounds/health drink.mp3");
                long id = zeta.sound.play();
                if (zeta.getSoundVolume() != 0) {
                    zeta.sound.setVolume(id, zeta.getSoundVolume());
                } else {
                    zeta.sound.setVolume(id, 0);
                }
            }

            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                zeta.manager.get("audio/sounds/health drink.mp3", Sound.class).play(zeta.getSoundVolume());
            }
            useItem(player);
        }
    }

    public void setHealthJustTouched(boolean healthJustTouched) {
        this.healthJustTouched = healthJustTouched;
    }
}
