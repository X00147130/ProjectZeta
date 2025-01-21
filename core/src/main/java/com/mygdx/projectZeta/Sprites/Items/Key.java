package com.mygdx.projectZeta.Sprites.Items;

import static com.mygdx.projectZeta.projectZeta.PPM;

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



public class Key extends Item {
    private static int count = 0;
    public projectZeta zeta;
    private Animation<TextureRegion> keycard;


    public Key(projectZeta sfs, PlayScreen screen, float  x, float y) {
        super(screen, x, y);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i <= 4; i++) {
                frames.add(sfs.getObjects().findRegion("key" + i));
            }


        keycard = new Animation<TextureRegion>(0.2f, frames);
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            setBounds(0, 0, 22 / PPM, 22 / PPM);
        }

        setBounds(getX(),getY(),22 / sfs.PPM,22 / sfs.PPM);
        frames.clear();
        this.zeta = sfs;
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / projectZeta.PPM);
        fdef.filter.categoryBits = projectZeta.ITEM_BIT;
        fdef.filter.maskBits = projectZeta.PLAYER_BIT |
                projectZeta.GROUND_BIT |
                projectZeta.DOOR_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void useItem(Player player) {
        destroy();
        Gdx.app.log("KEY", "Collected");
        count = 1;
        screen.setKeys(count);
        player.setKey(true);

        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            zeta.loadSound("audio/sounds/678385__jocabundus__item-pickup-v2.wav");
            long id = zeta.sound.play();
            if (zeta.getSoundVolume() != 0)
                zeta.sound.setVolume(id, zeta.getSoundVolume());
            else {
                zeta.sound.setVolume(id, 0);
            }
        }
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            zeta.manager.get("audio/sounds/678385__jocabundus__item-pickup-v2.wav", Sound.class).play(zeta.getSoundVolume());
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() /2);
        setRegion(keycard.getKeyFrame(zeta.statetimer,true));
        if(screen.isComplete() || (screen.getPlayer().currentState == Player.State.DEAD && screen.getPlayer().getStateTimer() > 3)){
            count = 0;
        }

    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    public void dispose(){
        screen.dispose();
    }
}
