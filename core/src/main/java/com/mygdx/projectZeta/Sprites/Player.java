package com.mygdx.projectZeta.Sprites;

import static com.mygdx.projectZeta.projectZeta.PPM;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.projectZeta.Scenes.Screens.PlayScreen;
import com.mygdx.projectZeta.projectZeta;


public class Player extends Sprite {
    //State Variables for animation purposes
    public enum State {FALLING, JUMPING, STANDING, RUNNING, DEAD}

    public State currentState;
    public State previousState;
    private boolean animationTriggered = false;


    //Basic variables
    public World world;
    private PlayScreen screen;
    private projectZeta zeta;
    public Body b2body;
    private Boolean key = false;


    //Animation Variables
    private Animation<TextureRegion> playerStand;
    private Animation<TextureRegion> playerRun;
    private Animation<TextureRegion> playerJump;
    private Animation<TextureRegion> playerDead;

    private TextureAtlas playersChoice;

    private boolean runningRight;
    private float stateTimer;
    private boolean rifle = false;


    //boolean tests
    private boolean playerIsDead;
    private boolean fellToDeath;
    private boolean hit = false;
    private boolean doorTouched = false;


    //health variables
    private int health;
    private boolean healthCrate;
    private int damage;
    private static int hitCounter;

    /*Jump Variables*/
    private int jumpCounter = 0;

    //Attack Variables
    private Fixture fix;

    //movement variables
    private Vector2 limit;
    private boolean dash = false;

    public Player(PlayScreen screen, projectZeta game) {
        this.world = screen.getWorld();
        definePlayer();
        this.zeta = game;

        limit = new Vector2(0, 0);

        this.screen = screen;



        /*initialising health variables*/
        health = 100;
        damage = 50;
        hitCounter = 0;
        healthCrate = false;


        /*Animation variables initialization*/
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;



        /*Standing Animation*/
        Array<TextureRegion> frames = new Array<TextureRegion>();
        Array<TextureRegion> frames2 = new Array<TextureRegion>();

        frames.clear();
        frames2.clear();

        for (int i = 1; i < 5; i++) {
            frames.add(zeta.playersChoice.findRegion("idle" + i));
        }
        playerStand = new Animation<TextureRegion>(0.3f, frames, Animation.PlayMode.LOOP);

        setBounds(getX(), getY(), 24 / zeta.PPM, 27 / zeta.PPM);

        frames.clear();


        /*Running Animation*/
        frames.clear();

        for (int r = 1; r < 9; r++) {
            frames.add(zeta.playersChoice.findRegion("run" + r));
        }
        playerRun = new Animation<TextureRegion>(0.2f, frames, Animation.PlayMode.LOOP);

        setBounds(getX(), getY(), 24 / zeta.PPM, 27 / zeta.PPM);

        frames.clear();


        /*Jump Animation*/
        frames.clear();

        for (int j = 1; j < 7; j++) {
            frames.add(zeta.playersChoice.findRegion("jump" + j));
        }
        playerJump = new Animation<TextureRegion>(0.3f, frames, Animation.PlayMode.NORMAL);

        setBounds(getX(), getY(), 24 / zeta.PPM, 27 / zeta.PPM);
        frames.clear();



        /*Player death animation*/

        for (int d = 1; d < 7; d++) {
            frames.add(zeta.playersChoice.findRegion("dead" + d));
        }
        playerDead = new Animation<TextureRegion>(0.3f, frames, Animation.PlayMode.NORMAL);


        setBounds(getX(), getY(), 24 / zeta.PPM, 27 / zeta.PPM);

        frames.clear();

    }

    public void update(float dt) {
        if (Gdx.app.getType() == Application.ApplicationType.Android)
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

        //Pistol Read in
        playersChoice = zeta.getPlayersChoice();


        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 3);
        setRegion(getFrame(dt));
        isDead();


        if (getY() < 0) {
            zeta.music.stop();
            playerIsDead = true;
            b2body.applyLinearImpulse(new Vector2(0, 20f), b2body.getWorldCenter(), true);
        }

    }


    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region = new TextureRegion();

        switch (currentState) {
            case DEAD:
                region = playerDead.getKeyFrame(stateTimer, false);
                break;

            case JUMPING:
                region = playerJump.getKeyFrame(stateTimer, false);
                break;


            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;

            case FALLING:

            case STANDING:

            default:
                region = playerStand.getKeyFrame(stateTimer, true);
                break;
        }


        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;

    }

    public State getState() {
        State state;
        if (playerIsDead)
            return State.DEAD;

        else if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0) && stateTimer < 1.2f)
            return State.JUMPING;

        else if (b2body.getLinearVelocity().y < 0)
            return State.FALLING;

        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;

        else {
                state = State.STANDING;
            }
            return state;
        }


    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(80 / PPM, 405 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4 / PPM);
        fdef.filter.categoryBits = projectZeta.PLAYER_BIT;
        fdef.filter.maskBits = projectZeta.GROUND_BIT |
            projectZeta.ENEMY_BIT |
            projectZeta.TRUHAN_BIT |
            projectZeta.ITEM_BIT |
            projectZeta.WALL_BIT |
            projectZeta.DOOR_BIT |
            projectZeta.SKY_BIT |
            projectZeta.HEALTH_BIT |
            projectZeta.DEATH_BIT;

        fdef.shape = shape;
        b2body.setGravityScale(.02f);
        b2body.createFixture(fdef).setUserData(this);
    }

    /*Jump Counter System*/
    public void jumpReset() {
        zeta.jumpCounter = 0;
        zeta.doubleJumped = false;
        Gdx.app.log("Jumps", "Reset");
    }

    public void wall() {
        if (!isFlipX()) {
            b2body.applyLinearImpulse(new Vector2(-0.1f, 0), new Vector2(0.1f, 0), true);
        } else {
            b2body.applyLinearImpulse(new Vector2(0.1f, 0), new Vector2(-0.1f, 0), true);
        }
        b2body.setAwake(true);
    }


    //getting hit method
    public void hit() {

        if (hitCounter < 4) {    //Player is pushed back and says ow
            hit = true;
            if (b2body.getLinearVelocity().x > 0)
                b2body.applyLinearImpulse(new Vector2(-0.3f, 0.2f), b2body.getWorldCenter(), true);

            else if (b2body.getLinearVelocity().x < 0)
                b2body.applyLinearImpulse(new Vector2(0.3f, 0.2f), b2body.getWorldCenter(), true);

            else {
                b2body.applyLinearImpulse(new Vector2(-0.3f, 0.2f), b2body.getWorldCenter(), true);
            }

            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                zeta.loadSound("audio/sounds/getting-hit.mp3");
                long id = zeta.sound.play();
                if (zeta.getSoundVolume() != 0) {
                    zeta.sound.setVolume(id, zeta.getSoundVolume());
                } else {
                    zeta.sound.setVolume(id, 0);
                }
            }
            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                zeta.manager.get("audio/sounds/getting-hit.mp3", Sound.class).play(zeta.getSoundVolume());
            }

            hitCounter++;
        } else {   //Player death
            zeta.music.stop();
            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                zeta.loadSound("audio/sounds/death.wav");
                long id = zeta.sound.play();
                if (zeta.getSoundVolume() != 0) {
                    zeta.sound.setVolume(id, zeta.getSoundVolume());
                } else {
                    zeta.sound.setVolume(id, 0);
                }
            }
            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                zeta.manager.get("audio/sounds/death.wav", Sound.class).play(zeta.getSoundVolume());
            }


            playerIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = projectZeta.GROUND_BIT |
                projectZeta.DEATH_BIT;
            for (Fixture fixture : b2body.getFixtureList())
                fixture.setFilterData(filter);
            b2body.applyLinearImpulse(new Vector2(-1f, 2f), b2body.getWorldCenter(), true);
            hitCounter = 5;
        }
    }

    public void fellToDeath() {
        if (fellToDeath) {
            zeta.music.stop();
            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                zeta.loadSound("audio/sounds/death.wav");
                long id = zeta.sound.play();
                if (zeta.getSoundVolume() != 0) {
                    zeta.sound.setVolume(id, zeta.getSoundVolume());
                } else {
                    zeta.sound.setVolume(id, 0);
                }
            }
            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                zeta.manager.get("audio/sounds/death.wav", Sound.class).play(zeta.getSoundVolume());
            }


            playerIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = projectZeta.GROUND_BIT |
                projectZeta.DEATH_BIT;
            for (Fixture fixture : b2body.getFixtureList())
                fixture.setFilterData(filter);
            hitCounter = 5;
        }
    }

    public boolean isDead() {
        return playerIsDead;
    }

    public float getStateTimer() {
        return stateTimer;
    }

    public static int getHitCounter() {
        return hitCounter;
    }

    public static void setHitCounter(int resetHits) {
        hitCounter = resetHits;
    }

    public boolean isHealthCrate() {
        return healthCrate;
    }

    public void setHealthCrate(boolean healthCrate) {
        this.healthCrate = healthCrate;
    }

    public void reverseVelocity(boolean x, boolean y) {
        if (x) {
            limit.x = -limit.x;
        }
        if (y) {
            limit.y = -limit.y;
        }
    }

    public boolean isRifle() {
        return rifle;
    }

    public void setRifle(boolean rifle) {
        this.rifle = rifle;
    }

    public Boolean getKey() {
        return key;
    }

    public void setKey(Boolean key) {
        this.key = key;
    }

    public void setFellToDeath(boolean fellToDeath) {
        this.fellToDeath = fellToDeath;
    }

    public void setDoorTouched(boolean doorTouched) {
        this.doorTouched = doorTouched;
        animationTriggered = true;
    }
}
