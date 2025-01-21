package com.mygdx.projectZeta.Sprites.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.projectZeta.Scenes.Screens.PlayScreen;
import com.mygdx.projectZeta.projectZeta;

import java.util.ArrayList;

public class Bullets {

    public static final float SPEED = 3f;

    private TextureRegion clip;
    private projectZeta zeta;

    private FixtureDef bulletDef;
    public Body bulletBody;
    public boolean destroy;
    public boolean collided = false;

    private World world;
    private PlayScreen screen;

    private double damage = 0.5;

    public float x, y;

    public Bullets(projectZeta sfs, PlayScreen screen, float x, float y) {

        world = screen.getWorld();
        this.zeta = sfs;
        this.y = y - 1f / sfs.PPM;
        this.x = x + 0.05f / sfs.PPM;
        this.screen = screen;
        destroy = false;

        defineBullet();
    }

    public Fixture defineBullet() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bulletBody = world.createBody(bdef);


        bulletBody.setBullet(true);
        if (!screen.getPlayer().isFlipX()) {
            bulletBody.setLinearVelocity(SPEED, 0);
        } else {
            bulletBody.setLinearVelocity(-SPEED, 0);
        }

        bulletDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(0.6f / projectZeta.PPM);
        bulletDef.filter.categoryBits = projectZeta.BULLET_BIT;
        bulletDef.filter.maskBits = projectZeta.ENEMY_BIT |
            projectZeta.GROUND_BIT |
            projectZeta.TRUHAN_BIT |
            projectZeta.WALL_BIT |
            projectZeta.PLAYER_BIT;

        bulletDef.shape = shape;
        Fixture fix1 = bulletBody.createFixture(bulletDef);
        fix1.setUserData(this);
        fix1.setFriction(1f);
        bulletBody.setGravityScale(0);
        Gdx.app.log("bullet", "shoot");

        if (destroy) {
            bulletBody.setUserData(null);
            bodyRemoval();
        }

        return fix1;
    }


    public void destroy() {
        destroy = true;
        bodyRemoval();
    }

    public void update(float dt) {
        y = SPEED * dt;

        /*if (dt > 0.6f) {
            destroy();
        }*/

        clip = zeta.getPlayersChoice().findRegion("bullet2");
        damage = 0.5;
    }


    public void bodyRemoval() {
        /*try {*/
            if (!world.isLocked()) {
                System.out.println("Bullet Removal");
                while (bulletBody.getFixtureList().size > 0) {
                    bulletBody.destroyFixture(bulletBody.getFixtureList().get(0));
                }
                bulletBody.setUserData(null);
                world.destroyBody(bulletBody);
            }
        /*} catch (Exception e) {
            System.out.println("Failed to remove");
        }*/
    }

    public void render(SpriteBatch batch) {
        batch.draw(clip, bulletBody.getPosition().x, bulletBody.getPosition().y, clip.getRegionWidth() / zeta.PPM, clip.getRegionHeight() / zeta.PPM);
    }

    public double getDamage() {
        return damage;
    }

    public boolean isCollided() {
        return collided;
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }
}
