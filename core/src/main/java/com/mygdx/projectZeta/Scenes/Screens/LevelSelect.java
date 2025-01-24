package com.mygdx.projectZeta.Scenes.Screens;

import static com.badlogic.gdx.graphics.Color.CYAN;
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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.mygdx.projectZeta.projectZeta;

public class LevelSelect implements Screen {

    //Game
    private projectZeta zeta;


    //buttons
    Texture level1Img;
    Drawable level1Draw;
    Button level1Button;

    Texture level2Img;
    Drawable level2Draw;
    Button level2Button;

    Texture level3Img;
    Drawable level3Draw;
    Button level3Button;

    Texture level4Img;
    Drawable level4Draw;
    Button level4Button;

    Texture level5Img;
    Drawable level5Draw;
    Button level5Button;

    Texture level6Img;
    Drawable level6Draw;
    Button level6Button;

    Texture level7Img;
    Drawable level7Draw;
    Button level7Button;

    Texture level8Img;
    Drawable level8Draw;
    Button level8Button;

    Texture level9Img;
    Drawable level9Draw;
    Button level9Button;

    Texture level10Img;
    Drawable level10Draw;
    Button level10Button;

    Texture backImg;
    Drawable backDraw;
    Button backButton;

    //Background
    private Texture background;

    //admin
    private Viewport viewport;
    private Stage screen;
    private int level = 1;
    private int sceneTracking;


    public LevelSelect(final projectZeta game) {
        super();

        //Admin
        zeta = game;
        viewport = new FitViewport(projectZeta.V_WIDTH, projectZeta.V_HEIGHT, new OrthographicCamera());
        screen = new Stage(viewport, zeta.batch);
        sceneTracking = zeta.getSceneTracking();

        //Texture
        background = zeta.manager.get("backgrounds/lvlselectbg.png", Texture.class);


        //Label
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/DigitalDisco.fnt")), WHITE);
        final Label.LabelStyle buttonFont = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/DigitalDiscoThin.fnt")), WHITE);
        Label pageLabel = new Label("Level Select", font);
        pageLabel.setFontScale(0.7f, 0.6f);

        level1Img = new Texture("UI/Levelselect/level1.png");
        level1Draw = new TextureRegionDrawable(level1Img);
        level1Button = new ImageButton(level1Draw);

        level2Img = new Texture("UI/Levelselect/level2.png");
        level2Draw = new TextureRegionDrawable(level2Img);
        level2Button = new ImageButton(level2Draw);

        level3Img = new Texture("UI/Levelselect/level3.png");
        level3Draw = new TextureRegionDrawable(level3Img);
        level3Button = new ImageButton(level3Draw);

        level4Img = new Texture("UI/Levelselect/level4.png");
        level4Draw = new TextureRegionDrawable(level4Img);
        level4Button = new ImageButton(level4Draw);

        level5Img = new Texture("UI/Levelselect/level5.png");
        level5Draw = new TextureRegionDrawable(level5Img);
        level5Button = new ImageButton(level5Draw);

        level6Img = new Texture("UI/Levelselect/level6.png");
        level6Draw = new TextureRegionDrawable(level6Img);
        level6Button = new ImageButton(level6Draw);

        level7Img = new Texture("UI/Levelselect/level7.png");
        level7Draw = new TextureRegionDrawable(level7Img);
        level7Button = new ImageButton(level7Draw);

        level8Img = new Texture("UI/Levelselect/level8.png");
        level8Draw = new TextureRegionDrawable(level8Img);
        level8Button = new ImageButton(level8Draw);

        level9Img = new Texture("UI/Levelselect/level9.png");
        level9Draw = new TextureRegionDrawable(level9Img);
        level9Button = new ImageButton(level9Draw);

        level10Img = new Texture("UI/Levelselect/level10.png");
        level10Draw = new TextureRegionDrawable(level10Img);
        level10Button = new ImageButton(level10Draw);

        backImg = new Texture("UI/back.png");
        backDraw = new TextureRegionDrawable(backImg);
        backButton = new ImageButton(backDraw);

        //Table
        final Table grid = new Table();
        grid.top();
        grid.setFillParent(true);

        //Tileset 1
        final Table grid2 = new Table();
        grid2.center();
        grid2.setFillParent(true);


        //filling table for Tileset 1
        grid.add(pageLabel).center().padLeft(10).padBottom(10).padTop(20);
        grid.row();
        grid2.add(level1Button).padTop(10);
        grid2.add(level6Button).padRight(70).padLeft(20).padTop(50).padTop(10);
        grid2.row();
        grid2.add(level2Button).padTop(3);
        grid2.add(level7Button).padRight(70).padLeft(20).padTop(3);
        grid2.row();
        grid2.add(level3Button).padTop(3);
        grid2.add(level8Button).padRight(70).padLeft(20).padTop(3);
        grid2.row();
        grid2.add(level4Button).padTop(3);
        grid2.add(level9Button).padRight(70).padLeft(20).padTop(3);
        grid2.row();
        grid2.add(level5Button).padTop(3);
        grid2.add(level10Button).padRight(70).padLeft(20).padTop(3);
        grid2.row();
        grid2.add(backButton).padTop(10).padLeft(140);

        screen.addActor(grid);
        screen.addActor(grid2);
        Gdx.input.setInputProcessor(screen);

        backButton.addListener(new ClickListener() {
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


                zeta.music.stop();
                zeta.setScreen(new MenuScreen(zeta));
            }
        });


        level1Button.addListener(new ClickListener(){
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


                zeta.music.stop();
                zeta.setScreen(new CharacterSelect(zeta,1,1));
            }
        });

        level2Button.addListener(new ClickListener(){
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


                zeta.music.stop();
                zeta.setScreen(new CharacterSelect(zeta, 2,3));
            }
        });

        level3Button.addListener(new ClickListener(){
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


                zeta.music.stop();
                zeta.setScreen(new CharacterSelect(zeta, 3,3));
            }
        });

        level4Button.addListener(new ClickListener(){
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


                zeta.music.stop();
                zeta.setScreen(new CharacterSelect(zeta,4,4));
            }
        });

        level5Button.addListener(new ClickListener(){
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

                zeta.music.stop();
                zeta.setScreen(new CharacterSelect(zeta, 5,5));
            }
        });

        level6Button.addListener(new ClickListener(){
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


                zeta.music.stop();
                zeta.setScreen(new CharacterSelect(zeta, 6,6));
            }
        });

        level7Button.addListener(new ClickListener(){
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


                zeta.music.stop();
                zeta.setScreen(new CharacterSelect(zeta,7,7));
            }
        });

        level8Button.addListener(new ClickListener(){
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


                zeta.music.stop();
                zeta.setScreen(new CharacterSelect(zeta, 8,8));
            }
        });

        level9Button.addListener(new ClickListener(){
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

                zeta.music.stop();
                zeta.setScreen(new CharacterSelect(zeta, 9,9));
            }
        });

        level10Button.addListener(new ClickListener(){
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


                zeta.music.stop();
                zeta.setScreen(new CharacterSelect(zeta, 10,10));
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            zeta.batch.begin();
            zeta.batch.draw(background, 0, 0, 400, 275);
            zeta.batch.end();
        }
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            zeta.batch.begin();
            zeta.batch.draw(background, 0, -0, 400, 300);
            zeta.batch.end();
        }

        screen.draw();
    }

    @Override
    public void resize(int width, int height) {
        screen.getViewport().update(width,height,true);

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
    public void show() {

    }

    @Override
    public void dispose() {
        screen.dispose();
        zeta.dispose();
        background.dispose();
    }


}
