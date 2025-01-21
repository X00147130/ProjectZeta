package com.mygdx.projectZeta.Scenes.Screens;

import static com.badlogic.gdx.graphics.Color.CYAN;
import static com.badlogic.gdx.graphics.Color.MAGENTA;
import static com.badlogic.gdx.graphics.Color.WHITE;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.projectZeta.projectZeta;


public class MenuScreen implements Screen  {

    private AssetManager manager;
    private Viewport viewport;
    private Stage stage;
    private final projectZeta zeta;
    private Texture background;
    private SpriteBatch batch;


    //Buttons
    private Label play;
    private Label levelSelect;
    private Label controls;
    private Label settings;
    private Label quit;


    public MenuScreen(final projectZeta game) {
        this.zeta = game;
        this.manager = projectZeta.getManager();
        batch = new SpriteBatch();
        viewport = new FitViewport(projectZeta.V_WIDTH, projectZeta.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, zeta.batch);


        //background
        background = manager.get("backgrounds/menubg.png", Texture.class);


        Table table = new Table();
        table.center();
        table.setFillParent(true);


        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/DigitalDisco.fnt")), WHITE);
        Label.LabelStyle buttonFont = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/DigitalDiscoThin.fnt")), WHITE);
        Label titleLabel = new Label(" PROJECT ", font);
        titleLabel.setFontScale(1.2f, 0.8f);
        Label titleLabel2 = new Label("ZETA", font);
        titleLabel2.setFontScale(1.2f, 0.8f);

        play = new Label("Play", buttonFont);
        play.setFontScale(0.5f, 0.5f);

        levelSelect = new Label("Level Select", buttonFont);
        levelSelect.setFontScale(0.5f, 0.5f);

        controls = new Label("Controls", buttonFont);
        controls.setFontScale(0.5f, 0.5f);

        settings = new Label("Settings", buttonFont);
        settings.setFontScale(0.5f, 0.5f);

        quit = new Label("Exit", buttonFont);
        quit.setFontScale(0.5f, 0.5f);


        table.add(titleLabel).expandX();
        table.row();
        table.add(titleLabel2).expandX();
        table.row();
        table.add(play).expandX().padTop(10);
        table.row();
        table.add(levelSelect).expandX().padTop(5);
        table.row();
        table.add(controls).expandX().padTop(5);
        table.row();
        table.add(settings).expandX().padTop(5);
        table.row();
        table.add(quit).expandX().padTop(5);
        table.row();


        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);


        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
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
                if(zeta.music.isPlaying())
                    zeta.music.stop();

                zeta.setScreen(new CharacterSelect(zeta, 1));
                dispose();

            }
        });

        levelSelect.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Gdx.app.getType() == Application.ApplicationType.Android) {
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

                zeta.setScreen(new LevelSelect(zeta));
            }
        });

        controls.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
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

                zeta.setScreen(new Controls(zeta));
            }
        });

        settings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
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

                zeta.setScreen(new Settings(zeta));
            }
        });

        quit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
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

                /*System.gc();*/
                dispose();
                System.exit(0);
            }
        });


        zeta.loadMusic("audio/music/jantrax - ai.mp3");
        if(zeta.getVolume() != 0) {
            zeta.music.play();
            zeta.music.setVolume(zeta.getVolume());
            }
    }
    @Override
    public void show() {

    }
    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            batch.begin();
            batch.draw(background,0,-20,1950,1250);
            batch.end();
        }
        else if(Gdx.app.getType() == Application.ApplicationType.Android){
            batch.begin();
            batch.draw(background, 0, 0, 2250,1075);
            batch.end();
        }
        stage.draw();
    }


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

    @Override
    public void dispose() {
    stage.dispose();
    System.gc();
    }
}
