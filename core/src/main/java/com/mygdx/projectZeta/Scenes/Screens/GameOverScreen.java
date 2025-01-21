package com.mygdx.projectZeta.Scenes.Screens;

import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.projectZeta.projectZeta;


public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private projectZeta zeta;
    private Table table;
    public boolean reset = false;

    private int area = 1;
    private int map = 1;

    private Texture background;

    //buttons
    private Label playAgainButton;
    private Label mainMenuButton;

    public GameOverScreen(projectZeta game, int location, int level){
        this.zeta = game;
        viewport = new FitViewport(projectZeta.V_WIDTH, projectZeta.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, zeta.batch);
        this.area = location;
        this.map = level;

        background = zeta.manager.get("backgrounds/deadbg.png", Texture.class);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/DigitalDisco.fnt")), RED);
        Label.LabelStyle buttonFont = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/DigitalDiscoThin.fnt")), WHITE);

        table = new Table();
        table.center();
        table.setFillParent(true);

        playAgainButton = new Label(" Play Again? ", buttonFont);
        mainMenuButton = new Label(" Main Menu ",buttonFont);
        playAgainButton.setFontScale(0.4f, 0.4f);
        mainMenuButton.setFontScale(0.4f, 0.4f);


        Label gameOverLabel = new Label("YOU GOT", font);
        gameOverLabel.setFontScale(0.8f, 0.5f);
        Label gameOverLabel2 = new Label("PROBED!!!", font);
        gameOverLabel2.setFontScale(0.8f, 0.7f);
        table.add(gameOverLabel).expandX().center();
        table.row();
        table.add(gameOverLabel2).expandX().center();
        table.row();
        table.add(playAgainButton).expandX().padTop(30).center();
        table.row();
        table.add(mainMenuButton).expandX().padTop(10).center();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);


       // makes button take us to new playscreen
        playAgainButton.addListener(new ClickListener(){
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
                zeta.setScreen(new PlayScreen(zeta,map));
            }
        });

        //Makes Button take us to main menu
        mainMenuButton.addListener(new ClickListener(){
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
        zeta.loadMusic("audio/music/mixkit-piano-horror-671.mp3");
        if(zeta.getVolume() != 0) {
            zeta.music.setVolume(zeta.getVolume());
            zeta.music.play();
        }

    }

    public boolean isReset() {
        return reset;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        zeta.batch.begin();
        zeta.batch.draw(background,0,0,400,300);
        zeta.batch.end();
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
        zeta.dispose();
    }
}
