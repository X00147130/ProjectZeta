package com.mygdx.projectZeta.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.projectZeta.Scenes.Screens.PlayScreen;
import com.mygdx.projectZeta.Sprites.Enemies.Grunts.Truhan;
import com.mygdx.projectZeta.Sprites.Enemies.Grunts.Kraken;
import com.mygdx.projectZeta.Sprites.Items.Key;
import com.mygdx.projectZeta.Sprites.Items.Coin;
import com.mygdx.projectZeta.Sprites.Items.Hearts;
import com.mygdx.projectZeta.Sprites.TileObjects.Barrier;
import com.mygdx.projectZeta.Sprites.TileObjects.Death;
import com.mygdx.projectZeta.Sprites.TileObjects.Door;
import com.mygdx.projectZeta.Sprites.TileObjects.Sky;
import com.mygdx.projectZeta.projectZeta;

public class B2WorldCreator {
    private projectZeta zeta;
    private Array<Kraken> alien;
    private Array<Truhan> zenos;
    private Array<Coin> coins;
    private Array<Hearts> vials;
    private Array<Key> keys;
    public Door door;


    public B2WorldCreator(projectZeta game, PlayScreen screen) {
        this.zeta = game;

        World world = screen.getWorld();
        TiledMap map = screen.getMap();
//temp body creation
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

//create ground bodies fixtures
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / projectZeta.PPM, (rect.getY() + rect.getHeight() / 2) / projectZeta.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / projectZeta.PPM, rect.getHeight() / 2 / projectZeta.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }


//create Coins fixtures
        coins = new Array<Coin>();
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
// creation of coin objects
            coins.add(new Coin(zeta, screen, rect.x / projectZeta.PPM, rect.y / projectZeta.PPM));

        }

//create finish fixtures
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            // creation of door object
            door = new Door(zeta, screen, object);
        }

// create Kraken enemies
        alien = new Array<Kraken>();
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            alien.add(new Kraken(zeta, screen, rect.x / projectZeta.PPM, rect.y / projectZeta.PPM));
        }

// create Kraken 2 enemies
        zenos = new Array<Truhan>();
        for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            zenos.add(new Truhan(zeta, screen, rect.x / projectZeta.PPM, rect.y / projectZeta.PPM));
        }


//Create barriers
        for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
            new Barrier(screen, object);
        }


//create health fixtures
        vials = new Array<Hearts>();
        for (MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

// creation of health vials objects
            vials.add(new Hearts(zeta, screen, rect.x / projectZeta.PPM, rect.y / projectZeta.PPM));
        }


//create Key fixtures
        keys = new Array<Key>();
        for (MapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
// creation of Key Card objects
            keys.add(new Key(zeta, screen, rect.x / projectZeta.PPM, rect.y / projectZeta.PPM));
        }


//Sky limit
        for (MapObject object : map.getLayers().get(13).getObjects().getByType(RectangleMapObject.class)) {
            new Sky(screen, object);
        }


//Death fixtures
        for (MapObject object : map.getLayers().get(14).getObjects().getByType(RectangleMapObject.class)) {
            new Death(screen, object);
        }

//create wall fixtures
        for (MapObject object : map.getLayers().get(15).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / projectZeta.PPM, (rect.getY() + rect.getHeight() / 2) / projectZeta.PPM);

            body = world.createBody(bdef);


            shape.setAsBox(rect.getWidth() / 2 / projectZeta.PPM, rect.getHeight() / 2 / projectZeta.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = projectZeta.WALL_BIT;
            body.createFixture(fdef);
        }
    }

    public Door getDoor() {
        return door;
    }

    public Array<Kraken> getAlien() {
        return alien;
    }

    public Array<Truhan> getZenos() {
        return zenos;
    }

    public Array<Hearts> getVials() {
        return vials;
    }

    public Array<Coin> getCoins() {
        return coins;
    }

    public Array<Key> getKeys() {
        return keys;
    }
    public void dispose() {
    }
}

