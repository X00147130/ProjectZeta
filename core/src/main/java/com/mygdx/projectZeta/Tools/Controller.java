package com.mygdx.projectZeta.Tools;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.projectZeta.Scenes.Screens.PauseScreen;
import com.mygdx.projectZeta.projectZeta;

public class Controller {
    private Viewport view;
    private boolean upPressed = false, shootPressed = false,leftPressed = false,rightPressed = false, interactPressed = false;
    public Stage stage;
    private OrthographicCamera cam;
    private projectZeta gameplay;
    private float timeSeconds = 0f;
    private float period = 0.01f;

    /*Image Buttons*/
    private Image pause;
    private Image attackImg;
    private Image upImg;
    private Image interactImg;

    public Controller(final projectZeta game){
        cam = new OrthographicCamera(480,320);
        cam.position.set(480/2f, 320/2f,0);
        view = new FitViewport(projectZeta.V_WIDTH, projectZeta.V_HEIGHT,cam);
        stage = new Stage(view);
        Gdx.input.setInputProcessor(stage);
        gameplay = game;

        upImg = (new Image(new Texture("controller/jump.png")));
        upImg.setSize(60,55);

        upImg.addListener(new InputListener() {


            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                gameplay.justTouched++;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        attackImg = new Image(new Texture("controller/Shoot.png"));
        attackImg.setSize(60, 55);
        attackImg.addListener(new InputListener() {


            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                shootPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                shootPressed = false;
            }
        });


        Image rightImg = new Image(new Texture("controller/right.png"));
        rightImg.setSize(60,55);
        rightImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        Image leftImg = new Image(new Texture("controller/left.png"));
        leftImg.setSize(60,55);
        leftImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        interactImg = new Image(new Texture("controller/Interact.png"));
        interactImg.setSize(60,55);
        interactImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                interactPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                interactPressed = false;
            }
        });
        //Image button
        pause = new Image(new Texture("controller/Pause.png"));
        pause.setSize(60,55);

        pause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event,float x, float y){
                gameplay.setScreen(new PauseScreen(gameplay));
            }

        });

        Table paused = new Table();
        paused.left();
        paused.setFillParent(true);

        Table table = new Table();
        table.left().bottom();
        table.setFillParent(true);

        Table action = new Table();
        action.right().bottom();
        action.setFillParent(true);

        paused.add(pause).size(pause.getWidth(), pause.getHeight()).left();

        stage.addActor(paused);

        table.row().padTop(5).padBottom(5);
        table.add(leftImg).size(leftImg.getWidth(),leftImg.getHeight());
        table.add();
        table.add(rightImg).size(rightImg.getWidth(),rightImg.getHeight());
        table.row().padBottom(5);

        stage.addActor(table);

        action.add(upImg).size(upImg.getWidth(), upImg.getHeight()).padRight(10);
        action.add();
        action.add(interactImg).size(interactImg.getWidth(), interactImg.getHeight()).padRight(10).padLeft(10);
        action.add();
        action.add(attackImg).size(attackImg.getWidth(), attackImg.getHeight()).padLeft(10);


        stage.addActor(action);

        stage.addListener(new InputListener() {

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.UP:
                        upPressed = true;
                        break;
                    case Input.Keys.SPACE:
                        shootPressed = true;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = true;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = true;
                        break;
                    case Input.Keys.I:
                        interactPressed = true;
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.UP:
                        upPressed = false;
                        break;
                    case Input.Keys.SPACE:
                        shootPressed = false;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = false;
                    case Input.Keys.RIGHT:
                        rightPressed = false;
                        break;
                    case Input.Keys.I:
                        interactPressed = false;
                        break;
                }
                return true;
            }

        });
    }
    public void draw(){
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            Gdx.input.setInputProcessor(stage);
            timeSeconds += Gdx.graphics.getRawDeltaTime();
            if (timeSeconds > period) {
                timeSeconds -= period;
                handleEvent();
            }
        }
        stage.draw();

    }

    public void handleEvent(){
        if(shootPressed)
            shootPressed = false;

        if(upPressed)
            upPressed = false;

        if(interactPressed)
            interactPressed = false;
    }
    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isIPressed() {
        return interactPressed;
    }

    public boolean isSpacePressed(){return shootPressed;}

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void resize(int width, int height){
        view.update(width,height);
    }

    public void dispose(){
        stage.dispose();
    }
}
