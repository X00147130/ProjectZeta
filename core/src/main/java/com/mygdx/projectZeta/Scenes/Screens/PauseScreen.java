package com.mygdx.projectZeta.Scenes.Screens;

import static com.badlogic.gdx.graphics.Color.CYAN;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.projectZeta.Scenes.Hud;
import com.mygdx.projectZeta.projectZeta;

public class PauseScreen implements Screen {

//Display tools
    private Table table;
    private Stage stage;

//Labels and Buttons
    private Label titleLabel;
    private Label.LabelStyle style;
    private Label.LabelStyle buttonStyle;
    private Label resume;
    private Label quit;

//Admin
    private projectZeta zeta;
    private Screen screen;
    private Viewport viewport;
    private Hud hud;
    private Texture background;



//Constructor
    public PauseScreen(final projectZeta gameplay){
//Variable intialisations
        zeta = gameplay;
        screen = gameplay.getScreen();
        hud = gameplay.getHud();
        viewport = new FitViewport(projectZeta.V_WIDTH, projectZeta.V_HEIGHT,  new OrthographicCamera());
        stage = new Stage(viewport, zeta.batch);

//Setting up of the background
        background = zeta.manager.get("backgrounds/pausebg.png",Texture.class);


//Label Style set up
        style = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/DigitalDisco.fnt")), WHITE);
        buttonStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/DigitalDiscoThin.fnt")), WHITE);

//Title Label Setup
        titleLabel = new Label("PAUSED",style);
        titleLabel.setFontScale(0.8f, 0.6f);

//Button Setups using Labels as Buttons
        resume = new Label("resume", buttonStyle);
        quit = new Label("quit", buttonStyle);
        resume.setFontScale(0.5f, 0.5f);
        quit.setFontScale(0.5f, 0.5f);

//Table Setup
        table = new Table ();
        table.center();
        table.setFillParent(true);

//Filling the Table
        table.add(titleLabel).width(70).height(60).center();
        table.row();
        table.add(resume).width(110).height(50).center().padLeft(70);
        table.row();
        table.add(quit).width(110).height(50).center().padLeft(95);

//Adding Table to the Stage and setting input reading on the stage to pick up any Clicks/Taps
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

//Setting up the Resume Button backend
        resume.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
//Sets the sound of the volume and retrieves the volume from the settings slider bar that updates a variable in the main page ShootForSurvival
//this sets it for Desktop devices
                if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    zeta.loadSound("audio/sounds/421837__prex2202__blipbutton.mp3");
                    long id = zeta.sound.play();
                    if (zeta.getSoundVolume() != 0)
                        zeta.sound.setVolume(id, zeta.getSoundVolume());
                    else {
                        zeta.sound.setVolume(id, 0);
                    }
                }
//Same as the above but this sets it for android devices
                if(Gdx.app.getType() == Application.ApplicationType.Android) {
                    zeta.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(zeta.getSoundVolume());
                }

//Set the screen back to game that we read in in the constructor and dispose of this pause screen
                zeta.setScreen(screen);
                dispose();

            }
        });

//Setting up the Quit Button backend
        quit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
//Sets the sound of the volume and retrieves the volume from the settings slider bar that updates a variable in the main page ShootForSurvival
//this sets it for Desktop devices
                if(Gdx.app.getType() ==Application.ApplicationType.Desktop){
                    zeta.loadSound("audio/sounds/421837__prex2202__blipbutton.mp3");
                    long id = zeta.sound.play();
                    if (zeta.getSoundVolume() != 0)
                        zeta.sound.setVolume(id, zeta.getSoundVolume());
                    else {
                        zeta.sound.setVolume(id, 0);
                    }
                }
//Same as the above but this sets it for android devices
                if(Gdx.app.getType() == Application.ApplicationType.Android) {
                    zeta.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(zeta.getSoundVolume());
                }

//Set the screen back to Main Menu dispose of this pause screen
//Set the atlas packs back to stage 1 as the game needs to have them set in case the person chooses play currently as not set up continue button
                zeta.music.stop();
                zeta.setScreen(new MenuScreen (zeta));
                dispose();
            }
        });

    }

    @Override
    public void show() {

    }

//Render Method clears the screen and then uses main classes batch to draw the background to the screen.
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        zeta.batch.begin();
        zeta.batch.draw(background, 0, 0, 400, 300);
        zeta.batch.end();

        stage.draw();
    }

//Resizes the screen to fit the Device it is currently running on
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume(){
    }

    @Override
    public void hide() {
    }

//Disposes of the stage created in the constructor
    @Override
    public void dispose() {
        stage.dispose();
    }
}
