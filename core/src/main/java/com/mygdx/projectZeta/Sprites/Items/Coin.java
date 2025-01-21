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

public class Coin extends Item {
    private static int count = 0;
    private static int startCount = 0;
    public projectZeta zeta;
    private Animation<TextureRegion> money;


    public Coin(projectZeta sfs, PlayScreen screen, float x, float y) {
        super(screen, x, y);
        this.zeta = sfs;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i <= 6; i++) {
            frames.add(sfs.getObjects().findRegion("coin" + i));
        }

        money = new Animation<TextureRegion>(0.2f, frames);
        setBounds(0, 0, 20 / PPM, 20 / PPM);
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            setBounds(0, 0, 20 / PPM, 20 / PPM);
        }
        frames.clear();
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(9 / projectZeta.PPM);
        fdef.filter.categoryBits = projectZeta.ITEM_BIT;
        fdef.filter.maskBits = projectZeta.PLAYER_BIT |
            projectZeta.GROUND_BIT |
            projectZeta.DOOR_BIT;

        fdef.shape = shape;
        body.setGravityScale(0);
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void useItem(Player player) {
        destroy();
        count += 100;
        screen.setMoney(count);
        Gdx.app.log("Coin", "destroyed");
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            zeta.loadSound("audio/sounds/coin sound.wav");
            long id = zeta.sound.play();
            if (zeta.getSoundVolume() != 0)
                zeta.sound.setVolume(id, zeta.getSoundVolume());
            else {
                zeta.sound.setVolume(id, 0);
            }
        }
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            zeta.manager.get("audio/sounds/coin sound.wav", Sound.class).play(zeta.getSoundVolume());
        }

    }

    @Override
    public void update(float dt) {
        super.update(dt);

        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(money.getKeyFrame(zeta.statetimer, true));
        if (screen.getPlayer().currentState == Player.State.DEAD && screen.getPlayer().getStateTimer() > 0.01) {
            count = startCount;
        } else if (screen.isComplete()) {
            count = screen.getCoins();
            startCount = screen.getCoins();
            zeta.setStartMoney(startCount);
            zeta.setMoney(startCount);
        }

    }


    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    public void dispose() {
        screen.dispose();
    }
}
