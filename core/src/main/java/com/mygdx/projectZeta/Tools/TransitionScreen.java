package com.mygdx.projectZeta.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.projectZeta.projectZeta;

public class TransitionScreen implements Screen {
    private Screen currentScreen;
    private Screen newScreen;

    private projectZeta zeta;

    private float alpha = 0;

    private boolean fadeDirection = true;
    private ShapeRenderer shapeRenderer;

    public TransitionScreen(Screen currentScreen, Screen newScreen, projectZeta zeta) {
        this.currentScreen = currentScreen;
        this.newScreen = newScreen;
        this.zeta = zeta;

        zeta.setScreen(newScreen);
        zeta.setScreen(currentScreen);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        if(fadeDirection){
            currentScreen.render(Gdx.graphics.getDeltaTime());
        }
        else{
            newScreen.render(Gdx.graphics.getDeltaTime());
        }

        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setColor(0,0,0,alpha);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);

        if(alpha >= 1){
            fadeDirection = false;
        }
        else if(alpha <= 0 && fadeDirection == false){
            zeta.setScreen(newScreen);
            dispose();
        }
        alpha += fadeDirection == true ? 0.01 : -0.01;
    }

    @Override
    public void resize(int width, int height) {

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
        shapeRenderer.dispose();
        currentScreen.dispose();
    }
}
