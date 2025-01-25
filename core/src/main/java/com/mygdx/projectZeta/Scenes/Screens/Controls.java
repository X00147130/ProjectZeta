package com.mygdx.projectZeta.Scenes.Screens;


import static com.badlogic.gdx.graphics.Color.MAGENTA;
import static com.badlogic.gdx.graphics.Color.WHITE;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

public class Controls implements Screen {
    /*Game Variables*/
    private final projectZeta zeta;
    private Viewport viewport;

    /*Labels*/
    private Label title;
    private Label pause;
    private Label forward;
    private Label backward;
    private Label shoot;
    private Label interact;
    private Label jump;

    private Texture pauseImg;
    private Texture forwardImg;
    private Texture backwardImg;
    private Texture shootImg;
    private Texture interactImg;
    private Texture jumpImg;

    /*labelStyle*/
    private Label.LabelStyle titleStyle;
    private Label.LabelStyle style;

    /*Background Settings*/
    private Stage stage;
    private Texture background;

    public Controls(projectZeta game) {
        this.zeta = game;
        viewport = new FitViewport(projectZeta.V_WIDTH, projectZeta.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, zeta.batch);

        /*Label Style*/
        titleStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/DigitalDiscoThin.fnt")), WHITE);
        style = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/DigitalDisco.fnt")), WHITE);

        /*Labels*/
        title = new Label("Controls", titleStyle);

        /*Desktop*/
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            pause = new Label("Pause :   esc ", style);
            forward = new Label("Forward :   Right Arrow Key", style);
            backward = new Label("Backward :   Left Arrow Key", style);
            shoot = new Label("Shoot :   Space Bar ", style);
            jump = new Label("Jump :   Up Arrow Key", style);

            pause.setFontScale(0.3f, 0.3f);
            forward.setFontScale(0.3f, 0.3f);
            backward.setFontScale(0.3f, 0.3f);
            shoot.setFontScale(0.3f, 0.3f);
            jump.setFontScale(0.3f, 0.3f);
        }

        /*Android*/
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            pause = new Label("Pause :   ", style);
            forward = new Label("Forward :   ", style);
            backward = new Label("Backward :   ", style);
            shoot = new Label("Shoot :   ", style);
            interact = new Label("Interact :   ", style);
            jump = new Label("Jump :   ", style);

            pauseImg = new Texture("controller/Pause.png");
            forwardImg = new Texture("controller/right.png");
            backwardImg = new Texture("controller/left.png");
            shootImg = new Texture("controller/Shoot.png");
            interactImg = new Texture("controller/Interact.png");
            jumpImg = new Texture("controller/jump.png");

            pause.setFontScale(0.3f, 0.3f);
            forward.setFontScale(0.3f, 0.3f);
            backward.setFontScale(0.3f, 0.3f);
            shoot.setFontScale(0.3f, 0.3f);
            interact.setFontScale(0.3f, 0.3f);
            jump.setFontScale(0.3f, 0.3f);
        }

        /*Background Texture*/
        background = zeta.manager.get("backgrounds/controls.png", Texture.class);


        Texture controlsImg;
        Drawable controlsDraw;
        Button controlsButton;

        Texture backImg;
        Drawable backDraw;
        Button backButton;

        controlsImg = new Texture("UI/Controls/controlstitle.png");
        controlsDraw = new TextureRegionDrawable(controlsImg);
        controlsButton = new ImageButton(controlsDraw);
        controlsButton.setScale(2.8f);

        backImg = new Texture("UI/back.png");
        backDraw = new TextureRegionDrawable(backImg);
        backButton = new ImageButton(backDraw);


        /*Table Creation*/
        Table table = new Table();
        table.setFillParent(true);

        /*Populating Table*/
        table.add(controlsButton).center().top().padBottom(10);
        table.row();
        table.add(forward).center().padBottom(10);
        table.row();
        table.add(backward).center().padBottom(10);
        table.row();
        table.add(jump).center().padBottom(10);
        table.row();
        table.add(shoot).center().padBottom(10);
        table.row();
        table.add(interact).center().padBottom(10);
        table.row();
        table.add(pause).center();
        table.row();
        table.add(backButton).center().padTop(15);

        /*Passing Table into the Stage and Stage then into the input processor*/
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                zeta.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(zeta.getSoundVolume());
                zeta.music.stop();
                zeta.setScreen(new MenuScreen(zeta));
            }
        });
    }

    @Override
    public void show() {

    }

    /*Back button set up for Desktop*/
    public void handleInput(float dt) {
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                zeta.music.stop();
                zeta.setScreen(new MenuScreen(zeta));
            }
        }
    }

    /*Update that checks for inputs */
    public void update(float dt) {
        handleInput(dt);
    }

    /*Clearing the screen and then displaying the background and drawing the stage on top of the background*/
    @Override
    public void render(float delta) {
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            update(delta);
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        zeta.batch.begin();
        zeta.batch.draw(background, 0, 0, 400, 350);
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            zeta.batch.draw(forwardImg, 260, 145, 20, 20);
            zeta.batch.draw(backwardImg, 260, 125, 20, 20);
            zeta.batch.draw(jumpImg, 260, 105, 20, 20);
            zeta.batch.draw(shootImg, 260, 85, 20, 20);
            zeta.batch.draw(interactImg, 260, 65, 20, 20);
            zeta.batch.draw(pauseImg, 260, 45, 20, 20);
        }

        zeta.batch.end();

        stage.draw();
    }

    /*Resizes the viewport of the app to fit the device your on*/
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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

    /*Takes out the trash*/
    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
    }
}


