package com.mygdx.projectZeta;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.projectZeta.Scenes.Hud;
import com.mygdx.projectZeta.Scenes.Screens.LogoScreen;


public class projectZeta extends Game {
    //constants
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final int MAP_WIDTH = 900;
    public static final int MAP_HEIGHT = 508;
    public static final float PPM = 150;

    //Filter initializations
    public static final short GROUND_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short DOOR_BIT = 4;
    public static final short ENEMY_BIT = 8;
    public static final short TRUHAN_BIT = 16;
    public static final short ITEM_BIT = 32;
    public static final short BARRIER_BIT = 64;
    public static final short BULLET_BIT = 128;
    public static final short SKY_BIT = 256;
    public static final short DEATH_BIT = 528;
    public static final short WALL_BIT = 1024;
    public static final short HEALTH_BIT = 2048;


    //Game Variables
    public SpriteBatch batch;
    public World world;
    public float statetimer = 0;

    //Map
    private int area = 1;

    //Sound settings
    public float volume = 0.5f;
    public float soundVolume = 0.5f;
    public Music music;
    public Sound sound;

    //Money
    private int money;
    private int startMoney = 0;

    //HUD
    private Hud hud;

    //Jump
    public int jumpCounter = 0;
    public boolean doubleJumped = false;

    //Interact
    public int justTouched = 0;
    public int interact = 0;

    //Character 1 Atlas'
    private TextureAtlas characterMale;

    //Character 2 Atlas'
    private TextureAtlas characterFemale;

    //Character 3 Atlas'
    private TextureAtlas characterRobot;

    private int level = 1;

    private TextureAtlas enemies;
    private TextureAtlas objects;

    public TextureAtlas playersChoice;
    private int selection = 0;

    public static AssetManager manager;

    public Hud getHud() {
        return hud;
    }

    public void setHud(Hud hud) {
        this.hud = hud;
    }

    public int getStartMoney() {
        return startMoney;
    }

    public void setStartMoney(int startMoney) {
        this.startMoney = startMoney;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        manager = new AssetManager();

        playersChoice = new TextureAtlas();


        /*Sound Loading*/
        manager.load("audio/sounds/coin sound.wav", Sound.class);
        manager.load("audio/sounds/getting-hit.mp3", Sound.class);
        manager.load("audio/sounds/health drink.mp3", Sound.class);
        manager.load("audio/sounds/672801__silverillusionist__level-upmission-complete-resistance.wav", Sound.class);
        manager.load("audio/sounds/mixkit-fast-sword-whoosh-2792.wav", Sound.class);
        manager.load("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class);
        manager.load("audio/sounds/sexynakedbunny-ouch.mp3", Sound.class);
        manager.load("audio/sounds/soundnimja-jump.wav", Sound.class);
        manager.load("audio/sounds/stomp.wav", Sound.class);
        manager.load("audio/sounds/death.wav", Sound.class);
        manager.load("audio/sounds/414888__matrixxx__retro_laser_shot_04(Pistol).wav", Sound.class);
        manager.load("audio/sounds/214990__peridactyloptrix__laser-blast-(Rifle).wav", Sound.class);
        manager.load("audio/sounds/678385__jocabundus__item-pickup-v2.wav", Sound.class);
        manager.load("audio/sounds/364688__alegemaate__electronic-door-opening.wav", Sound.class);
        manager.load("audio/sounds/gun pickup.mp3", Sound.class);
        manager.load("audio/sounds/523553__matrixxx__tv_shutdown.wav", Sound.class);
        manager.load("audio/sounds/394499__mobeyee__hurting-the-robot.wav", Sound.class);


        /*Music Loading*/
        manager.load("audio/music/yoitrax - Fuji.mp3", Music.class);
        manager.load("audio/music/yoitrax - Jade Dragon.mp3", Music.class);
        manager.load("audio/music/yoitrax - Diamonds.mp3", Music.class);
        manager.load("audio/music/jantrax - ai.mp3", Music.class);


        /*Texture Loading*/
        manager.load("backgrounds/menubg.png", Texture.class); // main menu
        manager.load("backgrounds/Background.png", Texture.class); //main menu
        manager.load("backgrounds/characterselect.png", Texture.class);//Charcter Select background
        manager.load("backgrounds/lvlselectbg.png", Texture.class); // level select
        manager.load("backgrounds/lvlcompletebg.png", Texture.class); // level complete
        manager.load("backgrounds/settingsbg.png", Texture.class); // settings
        manager.load("backgrounds/controls.png", Texture.class); // controls Background
        manager.load("backgrounds/pausebg.png", Texture.class); // pause
        manager.load("backgrounds/deadbg.png", Texture.class); // game over
        manager.load("backgrounds/scripts/bg1.png", Texture.class);
        manager.load("backgrounds/scripts/bg2.png", Texture.class);
        manager.load("backgrounds/scripts/bg3.png", Texture.class);
        manager.load("backgrounds/scripts/bg4.png", Texture.class);
        manager.load("backgrounds/scripts/bg5.png", Texture.class);
        manager.load("backgrounds/scripts/textBox.png", Texture.class);


        //Female
        characterFemale = new TextureAtlas("sprites/Characters/girl.pack");//Female Character

        //Male
        characterMale = new TextureAtlas("sprites/Characters/boy.pack");//Male Character

        //Cyborg
        characterRobot = new TextureAtlas("sprites/Characters/robot.pack");//Robot Character

        enemies = new TextureAtlas("sprites/Enemies/enemies.pack");

        objects = new TextureAtlas("sprites/Objects/objects.pack");

        manager.finishLoading();
        setScreen(new LogoScreen(this));
    }

    public static AssetManager getManager() {
        return manager;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public void loadMusic(String path) {
        music = Gdx.audio.newMusic(Gdx.files.internal(path));
    }

    public void loadSound(String path) {
        sound = Gdx.audio.newSound(Gdx.files.internal(path));
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(float soundVolume) {
        this.soundVolume = soundVolume;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setStatetimer(float statetimer) {
        this.statetimer = statetimer;
    }


    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public TextureAtlas getCharacterMale() {
        return characterMale;
    }

    public TextureAtlas getCharacterFemale() {
        return characterFemale;
    }

    public TextureAtlas getCharacterRobot() {
        return characterRobot;
    }

    public TextureAtlas getPlayersChoice() {
        return playersChoice;
    }

    public void setPlayersChoice(TextureAtlas playersChoice) {
        this.playersChoice = playersChoice;
    }

    public int getSelection() {
        return selection;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    public TextureAtlas getEnemies() {
        return enemies;
    }

    public TextureAtlas getObjects() {
        return objects;
    }

    public void setObjects(TextureAtlas objects) {
        this.objects = objects;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setInteract(int interact) {
        this.interact = interact;
    }


}

