package com.mygdx.projectZeta.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.projectZeta.Sprites.Enemies.Enemy;
import com.mygdx.projectZeta.Sprites.Enemies.Grunts.Kraken;
import com.mygdx.projectZeta.Sprites.Enemies.Grunts.Truhan;
import com.mygdx.projectZeta.Sprites.Items.Bullets;
import com.mygdx.projectZeta.Sprites.Items.Hearts;
import com.mygdx.projectZeta.Sprites.Items.Item;
import com.mygdx.projectZeta.Sprites.Player;
import com.mygdx.projectZeta.Sprites.TileObjects.InteractiveTileObject;
import com.mygdx.projectZeta.projectZeta;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {

            case projectZeta.PLAYER_BIT | projectZeta.DOOR_BIT:
                if (fixA.getFilterData().categoryBits == projectZeta.PLAYER_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHit((Player) fixA.getUserData());

                else
                    ((InteractiveTileObject) fixA.getUserData()).onHit((Player) fixB.getUserData());
                break;

            case projectZeta.PLAYER_BIT | projectZeta.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == projectZeta.PLAYER_BIT) {
                    ((Player) fixA.getUserData()).jumpReset();
                } else
                    ((Player) fixB.getUserData()).jumpReset();
                break;


            case projectZeta.PLAYER_BIT | projectZeta.WALL_BIT:
                if (fixA.getFilterData().categoryBits == projectZeta.PLAYER_BIT) {
                    ((Player) fixA.getUserData()).wall();
                } else
                    ((Player) fixB.getUserData()).wall();
                break;


            case projectZeta.ENEMY_BIT | projectZeta.BULLET_BIT:
                if (fixA.getFilterData().categoryBits == projectZeta.BULLET_BIT) {
                    ((Bullets) fixA.getUserData()).setCollided(true);
                    ((Kraken) fixB.getUserData()).shot();
                } else {
                    ((Bullets) fixB.getUserData()).setCollided(true);
                    ((Kraken) fixA.getUserData()).shot();
                }
                break;


            case projectZeta.GROUND_BIT | projectZeta.BULLET_BIT:
                if (fixA.getFilterData().categoryBits == projectZeta.BULLET_BIT) {
                    ((Bullets) fixA.getUserData()).setCollided(true);
                } else {
                    ((Bullets) fixB.getUserData()).setCollided(true);
                }
                break;

            case projectZeta.WALL_BIT | projectZeta.BULLET_BIT:
                if (fixA.getFilterData().categoryBits == projectZeta.BULLET_BIT) {
                    ((Bullets) fixA.getUserData()).setCollided(true);
                } else {
                    ((Bullets) fixB.getUserData()).setCollided(true);
                }
                break;

            case projectZeta.TRUHAN_BIT | projectZeta.BARRIER_BIT:
                if (fixA.getFilterData().categoryBits == projectZeta.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;

            case projectZeta.TRUHAN_BIT | projectZeta.WALL_BIT:
                if (fixA.getFilterData().categoryBits == projectZeta.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;

            case projectZeta.ENEMY_BIT | projectZeta.BARRIER_BIT:
                if (fixA.getFilterData().categoryBits == projectZeta.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;

            case projectZeta.ENEMY_BIT | projectZeta.WALL_BIT:
                if (fixA.getFilterData().categoryBits == projectZeta.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;


            case projectZeta.PLAYER_BIT | projectZeta.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == projectZeta.PLAYER_BIT) {
                    ((Player) fixA.getUserData()).hit();
                    ((Kraken) fixB.getUserData()).setAttack(true);

                } else {
                    ((Player) fixB.getUserData()).hit();
                    ((Kraken) fixA.getUserData()).setAttack(true);
                }
                break;

            case projectZeta.ENEMY_BIT | projectZeta.TRUHAN_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;

            case projectZeta.TRUHAN_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;

            case projectZeta.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;

            case projectZeta.TRUHAN_BIT | projectZeta.BULLET_BIT:
                if (fixA.getFilterData().categoryBits == projectZeta.BULLET_BIT) {
                    ((Bullets) fixA.getUserData()).setCollided(true);
                    ((Truhan) fixB.getUserData()).shot();
                } else {
                    ((Bullets) fixB.getUserData()).setCollided(true);
                    ((Truhan) fixA.getUserData()).shot();
                }
                break;

            case projectZeta.PLAYER_BIT | projectZeta.TRUHAN_BIT:
                if (fixA.getFilterData().categoryBits == projectZeta.PLAYER_BIT) {
                    ((Player) fixA.getUserData()).hit();
                    ((Truhan) fixB.getUserData()).setAttack(true);

                } else {
                    ((Player) fixB.getUserData()).hit();
                    ((Truhan) fixA.getUserData()).setAttack(true);
                }
                break;


            case projectZeta.ITEM_BIT | projectZeta.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == projectZeta.ITEM_BIT) {
                    ((Item) fixA.getUserData()).useItem((Player) fixB.getUserData());

                } else {
                    ((Item) fixB.getUserData()).useItem((Player) fixA.getUserData());
                }
                break;

            case projectZeta.HEALTH_BIT | projectZeta.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == projectZeta.HEALTH_BIT) {
                    ((Hearts) fixA.getUserData()).setHealthJustTouched(true);

                } else {
                    ((Hearts) fixB.getUserData()).setHealthJustTouched(true);
                }
                break;

            case projectZeta.SKY_BIT | projectZeta.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == projectZeta.SKY_BIT)
                    ((InteractiveTileObject) fixA.getUserData()).onHit((Player) fixB.getUserData());
                else
                    ((InteractiveTileObject) fixB.getUserData()).onHit((Player) fixA.getUserData());
                break;

            case projectZeta.DEATH_BIT | projectZeta.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == projectZeta.PLAYER_BIT) {
                    ((Player) fixA.getUserData()).setFellToDeath(true);
                    ((Player) fixA.getUserData()).fellToDeath();
                } else {
                    ((Player) fixB.getUserData()).setFellToDeath(true);
                    ((Player) fixB.getUserData()).fellToDeath();
                }
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
