package com.mygdx.projectZeta.Scenes.Screens;

import static com.badlogic.gdx.graphics.Color.GREEN;
import static com.badlogic.gdx.graphics.Color.MAGENTA;
import static com.badlogic.gdx.graphics.Color.WHITE;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.projectZeta.projectZeta;

public class Settings implements Screen {
    //Declaration Of Variables
    private final projectZeta zeta;
    private Viewport viewport;

    private Label page;

    Texture backImg;
    Drawable backDraw;
    Button backButton;

    Texture musicImg;
    Drawable musicDraw;
    Button musicLabel;

    Texture soundImg;
    Drawable soundDraw;
    Button soundLabel;


    private SpriteBatch batch;

    Slider music;
    Slider sound;
    Skin skin;

    Stage stage;
    BitmapFont buttonFont;

    private Texture background;

    public Settings(final projectZeta game){
        //Initialisation of Variables
        this.zeta = game;
        viewport = new FitViewport(projectZeta.V_WIDTH, projectZeta.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        batch = new SpriteBatch();
        background = zeta.manager.get("backgrounds/settingsbg.png",Texture.class);

        //Label Styling
        //Title
        Label.LabelStyle title = new Label.LabelStyle();
        title.font = new BitmapFont(Gdx.files.internal("skins/DigitalDisco.fnt"));
        title.fontColor = WHITE;

        //Label Set up
        page = new Label("SETTINGS",title);
        page.setFontScale(0.9f, 0.7f);

        musicImg = new Texture("UI/Settings/musicVolume.png");
        musicDraw = new TextureRegionDrawable(musicImg);
        musicLabel = new ImageButton(musicDraw);
        musicLabel.setSize(46,38);

        soundImg = new Texture("UI/Settings/soundVolume.png");
        soundDraw = new TextureRegionDrawable(soundImg);
        soundLabel = new ImageButton(soundDraw);
        soundLabel.setSize(46,38);


        //skin setup
        skin = new Skin(Gdx.files.internal("skins/quantum-horizon/skin/quantum-horizon-ui.json"));

        //Music Slider initialisation
        music = new Slider(0f,1f,0.01f,false,skin);
        music.setValue(zeta.getVolume());

        //Sound Slider initialisation
        sound = new Slider(0f,1f,0.01f,false,skin);
        sound.setValue(zeta.getSoundVolume());

        //Music slider backend
        music.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!music.isDragging()) {
                    zeta.setVolume(music.getValue());
                    zeta.music.setVolume(zeta.getVolume());

                    if(!zeta.music.isPlaying()){
                        zeta.music.play();
                    }
                }
            }
        });

        //Sound slider backend
        sound.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!sound.isDragging()){
                    zeta.setSoundVolume(sound.getValue());
                    if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                        zeta.loadSound("audio/sounds/gun pickup.mp3");
                        long id = zeta.sound.play();
                        if (zeta.getSoundVolume() != 0)
                            zeta.sound.setVolume(id, zeta.getSoundVolume());
                        else {
                            zeta.sound.setVolume(id, 0);
                        }
                    }
                    if(Gdx.app.getType() == Application.ApplicationType.Android) {
                        zeta.manager.get("audio/sounds/gun pickup.mp3", Sound.class).play(zeta.getSoundVolume());
                    }
                }
            }
        });

        //Container/Bar for Music Volume
        Container<Slider> container = new Container<Slider>(music);
        container.setTransform(true); // enables scaling and rotation
        container.setSize(50 /  game.PPM,50/ game.PPM);
        container.setOrigin(container.getWidth() / 2 , container.getHeight() / 2);
        container.setScale(0.2f);

        //Container/Bar for Sound Volume
        Container<Slider> container1 = new Container<Slider>(sound);
        container1.setTransform(true); // enables scaling and rotation
        container1.setSize(50 /  game.PPM,50/ game.PPM);
        container1.setOrigin(container.getWidth() / 2 , container.getHeight() / 2);
        container1.setScale(0.2f);

        backImg = new Texture("UI/back.png");
        backDraw = new TextureRegionDrawable(backImg);
        backButton = new ImageButton(backDraw);
        backButton.setSize(46,38);

        //Creating a Table for positioning
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        //Adding to the Table
        table.row();
        table.add(page).padBottom(20).padLeft(250);
        table.row();
        table.add(musicLabel).padLeft(200);
        table.add(music).left().padRight(130);
        table.add(container).left().padBottom(20).padRight(130);
        table.row();
        table.row();
        table.add(soundLabel).padLeft(200);
        table.add(sound).left().padRight(130);
        table.add(container1).left().padRight(130);
        table.row();
        table.row();
        table.add(backButton).padTop(10).padLeft(250);
        table.row();


        //Setting up ClickListener for the back button
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
               if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                   zeta.loadSound("audio/sounds/421837__prex2202__blipbutton.mp3");
                   long id = zeta.sound.play();
                   if (zeta.getSoundVolume() != 0)
                       zeta.sound.setVolume(id, zeta.getSoundVolume());
                   else {
                       zeta.sound.setVolume(id, 0);
                   }
               }
                if(Gdx.app.getType() == Application.ApplicationType.Android) {
                    zeta.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(zeta.getSoundVolume());
                }

                zeta.music.stop();
                zeta.setScreen(new MenuScreen(zeta));
            }
        });

        //Setting up the stage using the table and reading input based off of clicks/Taps on stage
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);



    }

    @Override
    public void show() {

    }

    //Render Method to display
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        zeta.batch.begin();
        zeta.batch.draw(background,0,-10,400,350);
        zeta.batch.end();

        stage.draw();


    }

    //Resizing the screen to match the device it is on
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
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

    //Cleanup of the trash
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        buttonFont.dispose();
        batch.dispose();
        zeta.dispose();
        zeta.batch.dispose();
    }
}
