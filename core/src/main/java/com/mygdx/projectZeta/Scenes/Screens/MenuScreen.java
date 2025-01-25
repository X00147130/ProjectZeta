package com.mygdx.projectZeta.Scenes.Screens;

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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.projectZeta.Tools.TransitionScreen;
import com.mygdx.projectZeta.projectZeta;


public class MenuScreen implements Screen  {

    private Viewport viewport;
    private Stage stage;
    private final projectZeta zeta;
    private Texture background;
    private SpriteBatch batch;
    //Scene Variable
    private int sceneTracking;

    Texture playImg;
    Drawable playDraw;
    Button playButton;

    Texture lvlselImg;
    Drawable lvlselDraw;
    Button lvlselButton;
    //level variable
    private int level;
    Texture controlsImg;
    Drawable controlsDraw;
    Button controlsButton;

    Texture settingsImg;
    Drawable settingsDraw;
    Button settingsButton;

    Texture quitImg;
    Drawable quitDraw;
    Button quitButton;

    public MenuScreen(final projectZeta game) {
        this.zeta = game;
        batch = new SpriteBatch();
        viewport = new FitViewport(projectZeta.V_WIDTH, projectZeta.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, zeta.batch);

        background = projectZeta.manager.get("backgrounds/menubg.png", Texture.class);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/DigitalDisco.fnt")), WHITE);
        Label.LabelStyle buttonFont = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/DigitalDiscoThin.fnt")), WHITE);
        Label titleLabel = new Label(" PROJECT ", font);
        titleLabel.setFontScale(1.2f, 0.8f);
        Label titleLabel2 = new Label("ZETA", font);
        titleLabel2.setFontScale(1.2f, 0.8f);
        sceneTracking = zeta.getSceneTracking() + 1;

        //controller
        playImg = new Texture("UI/Menu/play.png");
        playDraw = new TextureRegionDrawable(playImg);
        playButton = new ImageButton(playDraw);
        playButton.setSize(46,38);

        lvlselImg = new Texture("UI/Menu/levelselect.png");
        lvlselDraw = new TextureRegionDrawable(lvlselImg);
        lvlselButton = new ImageButton(lvlselDraw);
        lvlselButton.setSize(46,38);

        controlsImg = new Texture("UI/Menu/controls.png");
        controlsDraw = new TextureRegionDrawable(controlsImg);
        controlsButton = new ImageButton(controlsDraw);
        controlsButton.setSize(46,38);

        settingsImg = new Texture("UI/Menu/settings.png");
        settingsDraw = new TextureRegionDrawable(settingsImg);
        settingsButton = new ImageButton(settingsDraw);
        settingsButton.setSize(46,38);

        quitImg = new Texture("UI/Menu/quit.png");
        quitDraw = new TextureRegionDrawable(quitImg);
        quitButton = new ImageButton(quitDraw);
        quitButton.setSize(46,38);

        table.add(titleLabel).expandX();
        table.row();
        table.add(titleLabel2).expandX();
        table.row();
        table.add(playButton).expandX().padTop(10);
        table.row();
        table.add(lvlselButton).expandX().padTop(5);
        table.row();
        table.add(controlsButton).expandX().padTop(5);
        table.row();
        table.add(settingsButton).expandX().padTop(5);
        table.row();
        table.add(quitButton).expandX().padTop(5);
        table.row();

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        playButton.addListener(new ClickListener() {
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
                    zeta.setScreen(new CharacterSelect(zeta, 1,1));
                    dispose();
            }
        });

        lvlselButton.addListener(new ClickListener() {
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

        controlsButton.addListener(new ClickListener() {
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

        settingsButton.addListener(new ClickListener() {
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

        quitButton.addListener(new ClickListener(){
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
        batch.setProjectionMatrix(stage.getCamera().combined);
        batch.begin();
        batch.draw(background,0,0,stage.getViewport().getWorldWidth(),stage.getViewport().getWorldHeight());
        batch.end();
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
