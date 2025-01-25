package com.mygdx.projectZeta.Scenes;

import static com.badlogic.gdx.graphics.Color.WHITE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.projectZeta.Scenes.Screens.PlayScreen;
import com.mygdx.projectZeta.Sprites.Player;
import com.mygdx.projectZeta.projectZeta;

public class Hud implements Disposable {

    public Stage stage;
    private Viewport viewport;

    private int walletAmount;
    private Label walletLabel;

    private int keys;
    private Label keyAcquired;


    //health bar
    private TextureRegion healthbar;

    //Labels
    Label wallet;
    Label key;


    static private boolean projectionMatrixSet;


    private projectZeta zeta;
    public final Screen play;
    private PlayScreen playScreen;


    public Hud(SpriteBatch sb, final projectZeta game, final Screen paused, final PlayScreen playScreen) {
        zeta = game;
        play = paused;

        this.playScreen = playScreen;

        walletAmount = game.getMoney();

        keys = playScreen.getKeys();

        viewport = new FitViewport(projectZeta.V_WIDTH, projectZeta.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        Table table2 = new Table();
        table2.top();
        table2.setFillParent(true);

        walletLabel = new Label(String.format("%01d", walletAmount), new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/DigitalDisco.fnt")), WHITE));
        walletLabel.setFontScale(0.6f,0.5f);
        wallet = new Label("SCORE:", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/DigitalDiscoThin.fnt")), WHITE));
        wallet.setFontScale(0.6f,0.5f);

        keyAcquired = new Label(String.format("%01d", keys), new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/DigitalDisco.fnt")), WHITE));
        keyAcquired.setFontScale(0.4f,0.5f);
        key = new Label("KEY:", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/DigitalDiscoThin.fnt")), WHITE));
        key.setFontScale(0.4f,0.5f);

        //group for health label scaling

        table.add(wallet).right().top();
        table.add(walletLabel).padRight(2).right().padLeft(20).top();;

        table.row();
        table.row();

        table2.add(key).padLeft(360).right();
        table2.add(keyAcquired).padLeft(10).padRight(63).right();

        stage.addActor(table);
        stage.addActor(table2);


        // health bar initialisation
        healthbar = new TextureRegion(zeta.playersChoice.findRegion("playercard5-6"));


    }

    public void update(float dt) {
        walletAmount = zeta.getMoney();
        walletLabel.setText(String.format("%01d", walletAmount));

        keys = playScreen.getKeys();
        keyAcquired.setText(String.format("%01d", keys));

        if (Player.getHitCounter() == 0)
            healthbar = new TextureRegion(zeta.playersChoice.findRegion("playercard5-" + playScreen.getShotsFired()));

        else if (Player.getHitCounter() == 1)
            healthbar = new TextureRegion(zeta.playersChoice.findRegion("playercard4-" + playScreen.getShotsFired()));

        else if (Player.getHitCounter() == 2)
            healthbar = new TextureRegion(zeta.playersChoice.findRegion("playercard3-" + playScreen.getShotsFired()));

        else if (Player.getHitCounter() == 3)
            healthbar = new TextureRegion(zeta.playersChoice.findRegion("playercard2-" + playScreen.getShotsFired()));

        else if (Player.getHitCounter() == 4)
            healthbar = new TextureRegion(zeta.playersChoice.findRegion("playercard1-" + playScreen.getShotsFired()));

        else if (Player.getHitCounter() == 5)
            healthbar = new TextureRegion(zeta.playersChoice.findRegion("playercard0-" + playScreen.getShotsFired()));
    }


    public void draw(SpriteBatch batch, float alpha) {
        batch.begin();
        batch.draw(healthbar, 1, 182, 101, 50);
        batch.end();
    }

    public Screen getPlayScreen() {
        return play;
    }


    @Override
    public void dispose() {
        stage.dispose();
    }
}

