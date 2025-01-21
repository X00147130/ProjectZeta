package com.mygdx.projectZeta.Sprites.Enemies.Grunts;

import static com.mygdx.projectZeta.projectZeta.PPM;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.projectZeta.Scenes.Screens.PlayScreen;
import com.mygdx.projectZeta.Sprites.Enemies.Enemy;
import com.mygdx.projectZeta.projectZeta;

public class Truhan extends Enemy {
    //animation variables
    public enum State {RUNNING, ATTACK, DEAD}

    public State currentState;
    public State previousState;
    private boolean alien2Dead;

    private projectZeta zeta;

    private float stateTime;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> dieAnimation;
    private Animation<TextureRegion> attackAnimation;
    private Animation<TextureRegion> hurtAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private boolean hit = false;
    private int hitCounter;
    private double hurt = 0;
    private double bulletDamage = 0;
    private boolean runningRight;
    private boolean attack = false;
    private int enemyHitCounter;

    public Truhan(projectZeta sfs, PlayScreen screen, float x, float y) {
        super(screen, x, y);
        this.zeta = sfs;
        previousState = State.RUNNING;
        currentState = State.RUNNING;

        //Run animation
        frames = new Array<TextureRegion>();

        for (int c = 1; c < 5; c++) {
            frames.add(sfs.getEnemies().findRegion("Truhan/chase" + c));
        }

        runAnimation = new Animation<TextureRegion>(0.5f, frames);
        setBounds(truhanBody.getWorldCenter().x, truhanBody.getWorldCenter().y, 18 / PPM, 35 / PPM);
        frames.clear();

        //Death animation
        frames.clear();

        for (int d = 1; d < 4; d++) {
            frames.add(sfs.getEnemies().findRegion("Truhan/death" + d));
        }


        dieAnimation = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();

        //Attack Animation
        frames.clear();

        for (int a = 1; a < 8; a++) {
            frames.add(sfs.getEnemies().findRegion("Truhan/attack" + a));
        }

        attackAnimation = new Animation<TextureRegion>(0.5f, frames);
        frames.clear();

        stateTime = 0;
        setBounds(truhanBody.getWorldCenter().x, truhanBody.getWorldCenter().y, 30 / PPM, 35 / PPM);
        setToDestroy = false;
        destroyed = false;
        enemyHitCounter = 0;
        alien2Dead = false;
        runningRight = true;

        bulletDamage = screen.getBulletDamage();
    }

    public State getState() {
        if (attack && !alien2Dead)
            return State.ATTACK;

        else if (!alien2Dead)
            return State.RUNNING;

        else
            return State.DEAD;

    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;

        switch (currentState) {
            case DEAD:
                region = dieAnimation.getKeyFrame(stateTime, false);
                break;

            case ATTACK:
                region = attackAnimation.getKeyFrame(stateTime, true);
                break;

            case RUNNING:

            default:
                region = runAnimation.getKeyFrame(stateTime, true);
                break;

        }

        if ((truhanBody.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;

        } else if ((truhanBody.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
        stateTime = currentState == previousState ? stateTime + dt : 0;
        previousState = currentState;
        return region;
    }


    public void update(float dt) {
        stateTime += dt;
        setRegion(getFrame(dt));

        if (setToDestroy && !destroyed) {
            alien2Dead = true;
            destroyed = true;
            destroy();
            stateTime = 0;


        } else if (!destroyed) {
            if (!attack)
                truhanBody.setLinearVelocity(velocity);
            else if (attack) {
                truhanBody.setLinearVelocity(0, 0);
                attack = false;
            }

            bulletDamage = screen.getBulletDamage();
            setPosition(truhanBody.getPosition().x - getWidth() / 2, truhanBody.getPosition().y - getHeight() / 3);
            setRegion(getFrame(dt));
        }

    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        truhanBody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PPM);
        fdef.filter.categoryBits = projectZeta.TRUHAN_BIT;
        fdef.filter.maskBits = projectZeta.GROUND_BIT |
            projectZeta.ENEMY_BIT |
            projectZeta.TRUHAN_BIT |
            projectZeta.BARRIER_BIT |
            projectZeta.BULLET_BIT |
            projectZeta.WALL_BIT |
            projectZeta.PLAYER_BIT;

        fdef.shape = shape;
        truhanBody.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch) {
        if (!destroyed || stateTime < 1)
            super.draw(batch);
    }

    @Override
    public void shot() {
        /*if (hitCounter < 1) {*/    //Truhan is pushed back
        hurt += bulletDamage;
        if (hurt == 0.5f) {
            setToDestroy = true;
            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                zeta.loadSound("audio/sounds/sexynakedbunny-ouch.mp3");
                long id = zeta.sound.play();
                if (zeta.getSoundVolume() != 0) {
                    zeta.sound.setVolume(id, zeta.getSoundVolume());
                } else {
                    zeta.sound.setVolume(id, 0);
                }
            }

            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                zeta.manager.get("audio/sounds/sexynakedbunny-ouch.mp3", Sound.class).play(zeta.getSoundVolume());
            }
            hitCounter = 1;
        }
        hit = false;
    }


    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public void destroy() {

        if (destroyed && !world.isLocked()) {
            truhanBody.destroyFixture(truhanBody.getFixtureList().get(0));
            truhanBody.setUserData(null);
            world.destroyBody(truhanBody);
        }
    }
}
