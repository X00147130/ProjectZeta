package com.mygdx.projectZeta.Scenes.Screens;

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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.projectZeta.Tools.TransitionScreen;
import com.mygdx.projectZeta.projectZeta;

public class LevelComplete implements Screen {

    //Admin
    private projectZeta zeta;
    private Viewport screen;
    private Stage stage;
    private SpriteBatch batch;
    private int score = 0;
    private int sceneTracking;


    //Next level button variables
    private int map;


    //Buttons
    Label menuButton;
    Label nextLevelButton;
    Label levelSelectButton;

    Label title;
    Label title2;
    Label Coins;
    private Texture background;

    public LevelComplete(projectZeta game, int level){
        super();
        //admin setup
        this.zeta = game;
        screen = new FitViewport(projectZeta.V_WIDTH, projectZeta.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(screen, zeta.batch);

        sceneTracking = zeta.getSceneTracking() +1;
        map = level + 1;
        batch = game.batch;

        score = zeta.getMoney();

        background = zeta.manager.get("backgrounds/lvlcompletebg.png",Texture.class);

        //Label Admin
        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/DigitalDisco.fnt")), WHITE);
        Label.LabelStyle style2 = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/DigitalDiscoThin.fnt")), WHITE);
        title = new Label("Level",style);
        title.setFontScale(0.9f,0.8f);
        title2 = new Label("Complete",style);
        title2.setFontScale(0.9f,0.8f);
        Coins = new Label(String.format("Score: %4d" ,score),style2);
        Coins.setFontScale(0.5f,0.5f);

        //Setting up the TextButtons
        menuButton = new Label("Main Menu", style2);
        menuButton.setFontScale(0.5f,0.5f);
        nextLevelButton = new Label("Next Level", style2);
        nextLevelButton.setFontScale(0.5f,0.5f);
        levelSelectButton = new Label("Level Select", style2);
        levelSelectButton.setFontScale(0.5f,0.5f);

        //Table Setup
        Table table = new Table();
        table.center();
        table.setFillParent(true);


        table.add(title).expandX().top();
        table.row();
        table.add(title2).expandX().padBottom(10);
        table.row();
        table.add(Coins).center().expandX();
        table.row();

        if(map != 10) {
            table.add(nextLevelButton).expandX().padTop(20).padBottom(5);
            table.row();
        }
        table.add(levelSelectButton).expandX().center().padBottom(5);
        table.row();
        table.add(menuButton).expandX().center().padBottom(5);
        table.row();

        //Setting up the stage
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        if(zeta.music.getVolume() == 0)
            zeta.setVolume(0);

        //Setting up ClickListners for buttons
      if(map != 10) {
          nextLevelButton.addListener(new ClickListener() {
              @Override
              public void clicked(InputEvent event, float x, float y) {

                  if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                      zeta.sound.stop();
                      zeta.loadSound("audio/sounds/421837__prex2202__blipbutton.mp3");
                      long id = zeta.sound.play();
                      if (zeta.getSoundVolume() != 0) {
                          zeta.sound.setVolume(id, zeta.getSoundVolume());
                      } else {
                          zeta.sound.setVolume(id, 0);
                      }
                  }

                  if(Gdx.app.getType() == Application.ApplicationType.Android) {
                      zeta.manager.get("audio/sounds/672801__silverillusionist__level-upmission-complete-resistance.wav", Sound.class).stop();
                      zeta.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(zeta.getSoundVolume());
                  }
                  zeta.setScreen(new TransitionScreen(zeta.getScreen(),(new CutsceneScreen(zeta,sceneTracking, map, false)),zeta,map));
                  /*sfs.setMoney(0);*/
              }
          });
      }

        menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

                if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    zeta.sound.stop();
                    zeta.loadSound("audio/sounds/421837__prex2202__blipbutton.mp3");
                    long id = zeta.sound.play();
                    if (zeta.getSoundVolume() != 0) {
                        zeta.sound.setVolume(id, zeta.getSoundVolume());
                    } else {
                        zeta.sound.setVolume(id, 0);
                    }
                }

                if(Gdx.app.getType() == Application.ApplicationType.Android) {
                    zeta.manager.get("audio/sounds/672801__silverillusionist__level-upmission-complete-resistance.wav", Sound.class).stop();
                    zeta.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(zeta.getSoundVolume());
                }
                zeta.setScreen(new MenuScreen(zeta));
            }
        });

        levelSelectButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    zeta.sound.stop();
                    zeta.loadSound("audio/sounds/421837__prex2202__blipbutton.mp3");

                    long id = zeta.sound.play();
                    if (zeta.getSoundVolume() != 0) {
                        zeta.sound.setVolume(id, zeta.getSoundVolume());
                    } else {
                        zeta.sound.setVolume(id, 0);
                    }
                }
                if(Gdx.app.getType() == Application.ApplicationType.Android) {
                    zeta.manager.get("audio/sounds/672801__silverillusionist__level-upmission-complete-resistance.wav", Sound.class).stop();
                    zeta.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(zeta.getSoundVolume());
                }
                zeta.setScreen(new LevelSelect(zeta));
                zeta.loadMusic("audio/music/jantrax - ai.mp3");
                if (zeta.getVolume() != 0) {
                    zeta.music.play();
                    zeta.setVolume(zeta.getVolume());
                }
            }
        });

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();
        batch.draw(background,0,0,400,300);
        batch.end();

        stage.draw();
        Gdx.input.setInputProcessor(stage);
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
    }
}
