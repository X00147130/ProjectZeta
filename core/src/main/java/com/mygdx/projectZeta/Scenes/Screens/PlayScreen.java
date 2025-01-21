package com.mygdx.projectZeta.Scenes.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.projectZeta.Scenes.Hud;
import com.mygdx.projectZeta.Sprites.Enemies.Enemy;
import com.mygdx.projectZeta.Sprites.Items.Item;
import com.mygdx.projectZeta.Sprites.Items.ItemDef;
import com.mygdx.projectZeta.Sprites.Items.Key;
import com.mygdx.projectZeta.Sprites.Items.Hearts;
import com.mygdx.projectZeta.Sprites.Player;
import com.mygdx.projectZeta.Sprites.Items.Bullets;
import com.mygdx.projectZeta.Tools.B2WorldCreator;
import com.mygdx.projectZeta.Tools.Controller;
import com.mygdx.projectZeta.Tools.WorldContactListener;
import com.mygdx.projectZeta.projectZeta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

public class PlayScreen implements Screen {
    private projectZeta zeta;
    public AssetManager manager;

    //basic variables
    private Hud hud;
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    //tiled map variables
    private int area = 1;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2D Variables
    public World world;
    private Box2DDebugRenderer b2dr;
    public B2WorldCreator creator;

    //Player variable
    private Player player;
    private float statetimer;
    private float x = 0.1f;
    private float y = 0.55f;
    private float phoneX = 0.25f;
    private float phoneY = 0.45f;

    //Bullet Variable
    private ArrayList<Bullets> mag;
    private double bulletDamage = 0;
    private static final int MAG_SIZE = 6;
    private int shotsFired = 0;
    private float emptyMagTimer = 0f;
    private static final float RELOAD_TIMER = 3.5f;



    //Sprite Variable
    private Array<Item> items;
    public LinkedBlockingQueue<ItemDef> itemToSpawn;
    private int coins;
    private int keys;

    //Door variable
    private int interact = 0;
    public boolean complete = false;
    private boolean doorJustTouched = false;

    //level variable
    private int level;

    //controller creation
    public Controller controller;

    public PlayScreen(projectZeta g, int level) {

        //game management inits
        this.zeta = g;
        this.level = level;
        this.manager = zeta.getManager();

        //view of the game
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(projectZeta.MAP_WIDTH / projectZeta.PPM, projectZeta.MAP_HEIGHT / projectZeta.PPM, gamecam);
        hud = new Hud(zeta.batch, zeta, zeta.getScreen(), this);

        //controller
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            controller = new Controller(zeta);
        }


        //render/map setup
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Maps/Tileset/Map" + level + ".tmx");

        renderer = new OrthogonalTiledMapRenderer(map, 1 / projectZeta.PPM);


        //initiating game cam
        gamecam.position.set(gamePort.getWorldWidth() / 1.5f, gamePort.getWorldHeight() / 1.5f, 0);

        world = new World(new Vector2(0, -10), true);

        if (Gdx.app.getType() == Application.ApplicationType.Desktop)
            b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(zeta, this);

        //Player creation
        player = new Player(this, zeta);
        world.setContactListener(new WorldContactListener());

        zeta.loadMusic("audio/music/yoitrax - Diamonds.mp3");
        if (zeta.getVolume() != 0) {
            zeta.music.play();
            zeta.music.setVolume(zeta.getVolume());
        }
        zeta.setSoundVolume(zeta.getSoundVolume());

        //bullet init
        mag = new ArrayList<Bullets>(MAG_SIZE);

        items = new Array<Item>();
        itemToSpawn = new LinkedBlockingQueue<ItemDef>();
        coins = zeta.getMoney();
        doorJustTouched = creator.door.isDoorJustTouched();


        zeta.setWorld(world);

    }

    public void handleSpawningItems() {
        if (!itemToSpawn.isEmpty()) {
            ItemDef idef = itemToSpawn.poll();
            if (idef.type == Hearts.class) {
                items.add(new Hearts(zeta, this, idef.position.x, idef.position.y));
            }
            if (idef.type == Key.class) {
                items.add(new Key(zeta, this, idef.position.x, idef.position.y));
            }
        }
    }


    public void handleInput(float dt) {

//PC
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            if (player.currentState != Player.State.DEAD) {
//jump / double jump
                if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && zeta.jumpCounter < 2) {
                    player.b2body.applyLinearImpulse(new Vector2(0, y), player.b2body.getWorldCenter(), true);
                    zeta.jumpCounter++;

                    zeta.loadSound("audio/sounds/soundnimja-jump.wav");
                    long id = zeta.sound.play();
                    if (zeta.getSoundVolume() != 0)
                        zeta.sound.setVolume(id, zeta.getSoundVolume());
                    else {
                        zeta.sound.setVolume(id, 0);
                    }
                }

//shoot / interact
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (shotsFired < MAG_SIZE) {
                        mag.add(new Bullets(zeta, this, player.b2body.getPosition().x, player.b2body.getPosition().y));
                        shotsFired++;
                        zeta.loadSound("audio/sounds/214990__peridactyloptrix__laser-blast-(Rifle).wav");
                    }
                    else if(shotsFired == MAG_SIZE){
                        System.out.println("Mag Empty: " + shotsFired);
                    }

                    long id = zeta.sound.play();
                    if (zeta.getSoundVolume() != 0)
                        zeta.sound.setVolume(id, zeta.getSoundVolume());
                    else {
                        zeta.sound.setVolume(id, 0);
                    }
                }
//dash
                if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
                    if (doorJustTouched && player.getKey()) {
                        interact++;
                        if (interact == 2) {
                            setLevelComplete(true);
                            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                                zeta.loadSound("audio/sounds/672801__silverillusionist__level-upmission-complete-resistance.wav");
                                long id = zeta.sound.play();
                                if (zeta.getSoundVolume() != 0)
                                    zeta.sound.setVolume(id, zeta.getSoundVolume());
                                else {
                                    zeta.sound.setVolume(id, 0);
                                }
                                zeta.music.stop();
                            }
                        }
                    }
                }


//pause
                if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                    zeta.setScreen(new PauseScreen(zeta));
                }

//move right
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 1.5f) {
                    player.b2body.setLinearVelocity(new Vector2(x, player.b2body.getLinearVelocity().y));
                }

//move left
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x <= 1.5f) {
                    player.b2body.setLinearVelocity(new Vector2(-x, player.b2body.getLinearVelocity().y));
                }

//Allows player to fal when forward and back are not pressed
                else if (!Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    player.b2body.setLinearVelocity(new Vector2(0, player.b2body.getLinearVelocity().y));
                }
            } else {
                player.b2body.setLinearVelocity(new Vector2(0, 0));
            }
        }


//Android Phone
        else if (Gdx.app.getType() == Application.ApplicationType.Android) {
            if (player.currentState != Player.State.DEAD) {

//Jump
                if (controller.isUpPressed() && zeta.jumpCounter < 2) {
                    player.b2body.applyLinearImpulse(new Vector2(0, phoneY), player.b2body.getWorldCenter(), true);
                    zeta.jumpCounter++;
                }

//Interact
                if (controller.isIPressed()) {
                    if (doorJustTouched && player.getKey()) {
                        interact++;
                        if (interact == 2) {
                            setLevelComplete(true);
                            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                                zeta.manager.get("audio/sounds/672801__silverillusionist__level-upmission-complete-resistance.wav", Sound.class).play(zeta.getSoundVolume());
                            }
                            zeta.music.stop();
                        }
                    }
                }

//Fire Gun
                if (controller.isSpacePressed()) {
                    if (shotsFired < MAG_SIZE) {
                        mag.add(new Bullets(zeta, this, player.b2body.getPosition().x, player.b2body.getPosition().y));
                        zeta.manager.get("audio/sounds/214990__peridactyloptrix__laser-blast-(Rifle).wav", Sound.class).play(zeta.getSoundVolume());
                        shotsFired++;
                    }
                    else if(shotsFired == MAG_SIZE){
                        System.out.println("Mag Empty: " + shotsFired);
                    }
                }


//Move Forward
                if (controller.isRightPressed() && player.b2body.getLinearVelocity().x <= 1.3) {
                    player.b2body.setLinearVelocity(new Vector2(phoneX, player.b2body.getLinearVelocity().y));
                }
//Move Backward
                if (controller.isLeftPressed() && player.b2body.getLinearVelocity().x <= 1.3) {
                    player.b2body.setLinearVelocity(new Vector2(-phoneX, player.b2body.getLinearVelocity().y));
                }

//Allows player to fal when forward and back are not pressed
                else if (!controller.isLeftPressed() && !controller.isRightPressed()) {
                    player.b2body.setLinearVelocity(new Vector2(0, player.b2body.getLinearVelocity().y));
                }

            } else {
                player.b2body.setLinearVelocity(new Vector2(0f, 0f));
            }
        }
    }

    public void update(float dt) {
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            Gdx.input.setInputProcessor(controller.stage);
        }

        handleInput(dt);
        handleSpawningItems();
        world.step(1 / 30f, 6, 2);

        player.update(dt);


        for (Enemy enemy : creator.getAlien()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 424 / projectZeta.PPM) {
                enemy.krakenBody.setActive(true);
            }
        }

        for (Enemy enemy2 : creator.getZenos()) {
            enemy2.update(dt);
            if (enemy2.getX() < player.getX() + 424 / projectZeta.PPM) {
                enemy2.truhanBody.setActive(true);
            }
        }

       /* for (Bullets bullet : mag) {
            bullet.bulletBody.setActive(true);
            bullet.update(dt);
            bulletDamage = bullet.getDamage();
        }

//Reloading Mag For Gun
        if(shotsFired == MAG_SIZE){
            if(emptyMagTimer < RELOAD_TIMER){
                emptyMagTimer += dt;
            }
            else if(emptyMagTimer >= RELOAD_TIMER){
                shotsFired = 0;
                emptyMagTimer = 0f;
System.out.println("Mag Reloaded " + shotsFired);
            }
        }

        Iterator<Bullets> iterator = mag.iterator();
        while (iterator.hasNext()){
            Bullets ammo = iterator.next();
            if(ammo.isCollided()){
                ammo.destroy();
                iterator.remove();
                System.out.println("Bullet gone");
            }
        }*/


        for (Item item : creator.getCoins()) {
            item.update(dt);
        }

        for (Item item : creator.getVials()) {
            for (int i = 0; i < creator.getVials().size; i++) {
                if (player.isHealthCrate()) {
                    player.setHealthCrate(false);
                }
            }
            item.update(dt);
        }

        for (Item item : creator.getKeys())
            item.update(dt);


        creator.door.objectStateTimer += dt;
        doorJustTouched = creator.door.isDoorJustTouched();

        if (!creator.door.isOpen() && doorJustTouched) {
            System.out.println("Total times Interacted:" + getInteract());
            creator.door.unlock(zeta.statetimer);
            interact++;
            if (statetimer < 1.3f && interact == 1) {
                creator.getDoor().setInteracted(interact);
                zeta.setInteract(interact);
            } else
                interact = 0;
            player.setDoorTouched(false);
        }

        hud.update(dt);
        zeta.setHud(hud);

        for (Bullets bullet : mag) {
            bullet.bulletBody.setActive(true);
            bullet.update(dt);
            bulletDamage = bullet.getDamage();
        }

//Reloading Mag For Gun
        if(shotsFired == MAG_SIZE){
            if(emptyMagTimer < RELOAD_TIMER){
                emptyMagTimer += dt;
            }
            else if(emptyMagTimer >= RELOAD_TIMER){
                shotsFired = 0;
                emptyMagTimer = 0f;
                System.out.println("Mag Reloaded " + shotsFired);
            }
        }

        Iterator<Bullets> iterator = mag.iterator();
        while (iterator.hasNext()){
            Bullets ammo = iterator.next();
            if(ammo.isCollided()){
                ammo.destroy();
                iterator.remove();
                System.out.println("Bullet gone");
            }
        }

        gamecam.update();

        if (player.currentState != Player.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
            gamecam.position.y = player.b2body.getPosition().y + 0.4f;
        }

        renderer.setView(gamecam);
        zeta.setMoney(coins);
        zeta.setStatetimer(player.getStateTimer());
    }

    @Override
    public void render(float delta) {
        update(delta);

        //Clear Game Screen With Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //game map
        renderer.render();

        //box2d debug lines
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            b2dr.render(world, gamecam.combined);
        }

        zeta.batch.setProjectionMatrix(gamecam.combined);
        zeta.batch.begin();

        creator.getDoor().draw(zeta.batch);

        player.draw(zeta.batch);

        for (Enemy enemy : creator.getAlien())
            enemy.draw(zeta.batch);

        for (Enemy enemy2 : creator.getZenos()) {
            enemy2.draw(zeta.batch);
        }

        for (Bullets bullet : mag)
            if (!bullet.destroy) {
                bullet.render(zeta.batch);
            }

        for (Item item : items)
            item.draw(zeta.batch);

        if (creator.getCoins().notEmpty()) {
            for (Item item : creator.getCoins()) {
                item.draw(zeta.batch);
            }
        }

        if (creator.getVials().notEmpty()) {
            for (Item item : creator.getVials()) {
                item.draw(zeta.batch);
            }
        }

        if (creator.getKeys().notEmpty()) {
            for (Item item : creator.getKeys()) {
                item.draw(zeta.batch);
            }
        }

        zeta.batch.end();


        //Set to draw what hud sees
        zeta.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            controller.draw();
        }

        hud.stage.draw();
        hud.draw(zeta.batch, delta);


        if (gameOver()) {
            zeta.music.stop();
            zeta.setScreen(new GameOverScreen(zeta, area, level));
            dispose();
        }

        if (complete) {
            if (player.getStateTimer() > 1.2) {
                if (level < 10) {
                    zeta.setScreen(new LevelComplete(zeta, area, level));
                } else {
                    zeta.setScreen(new LevelSelect(zeta));
                }
                dispose();
            }
        }
    }

    public float getStatetimer() {
        return statetimer;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            controller.resize(width, height);
        }
    }

    public boolean gameOver() {
        if (player.currentState == Player.State.DEAD && player.getStateTimer() > 3) {
            coins = zeta.getStartMoney();
            zeta.setMoney(zeta.getStartMoney());
            return true;
        } else {
            return false;
        }

    }

    public int getShotsFired() {
        return shotsFired;
    }

    public void setLevelComplete(boolean level) {
        complete = level;
    }

    public int getInteract() {
        return interact;
    }

    public Player getPlayer() {
        return player;
    }

    public TiledMap getMap() {
        return map;
    }

    public int getArea() {
        return area;
    }

    public int getLevel() {
        return level;
    }

    public World getWorld() {
        return world;
    }

    public boolean isComplete() {
        return complete;
    }

    public Hud getHud() {
        return hud;
    }

    public void setHud(Hud hud) {
        this.hud = hud;
    }

    public projectZeta getZeta() {
        return zeta;
    }

    public double getBulletDamage() {
        return bulletDamage;
    }

    public int getCoins() {
        return coins;
    }

    public void setMoney(int coins) {
        this.coins = coins;
    }

    public int getKeys() {
        return keys;
    }

    public void setKeys(int keys) {
        this.keys = keys;
    }

    @Override
    public void show() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();

        if (Gdx.app.getType() == Application.ApplicationType.Desktop)
            b2dr.dispose();

        hud.dispose();
        world.dispose();
        creator.dispose();

        if (Gdx.app.getType() == Application.ApplicationType.Android)
            controller.dispose();

        System.gc();
    }
}


