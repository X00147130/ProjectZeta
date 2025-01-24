package com.mygdx.projectZeta.Scenes.Screens;

import static com.badlogic.gdx.graphics.Color.WHITE;
import static com.badlogic.gdx.net.HttpRequestBuilder.json;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.projectZeta.Models.Script;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.projectZeta.Tools.TransitionScreen;
import com.mygdx.projectZeta.projectZeta;

import java.util.ArrayList;

public class CutsceneScreen implements Screen {
    //Base screen Variables
    private Stage stage;
    private projectZeta zeta;
    private Viewport viewport;
    private Texture bg;
    private Preferences prefs;
    private boolean finished, transition = false;
    private int map;

    //Script Related Variables
    private ArrayList<Script> list, scripts;
    private int iconId, sceneId;
    private int tempIterator, scriptIterator;
    private Texture nextImg, skipImg;
    private TextureRegionDrawable nextDraw, skipDraw;
    private Button next, skip;
    private Window window, window2;
    private Label title;
    private Label text;
    private String titleString;
    private String testString;
    private Texture character;
    private Image characterImg;
    private boolean pass = false;
    private boolean end = false;

    public CutsceneScreen(projectZeta zeta, final int sceneId, int level, boolean end) {
//Overall Screen Setup
        this.zeta = zeta;
        this.sceneId = sceneId;
        this.end = end;
        map = level;
        viewport = new FitViewport(this.zeta.V_WIDTH, this.zeta.V_HEIGHT, new OrthographicCamera());

//Script setup
        list = json.fromJson(ArrayList.class, Script.class, Gdx.files.internal("Scripts/script.json"));
        load();
        tempIterator = 0;

//Background setup
        bg = this.zeta.manager.get("backgrounds/scripts/bg1.png");

//Table and Stage creation
        Gdx.input.setInputProcessor(stage);
        prefs = Gdx.app.getPreferences("ProjectZetaPrefs");
        if (sceneId >= prefs.getInteger("level")) {
            prefs.putInteger("level", sceneId);
        }
        stage = new Stage(viewport, zeta.batch);
        Table table = new Table();
        table.bottom();
        table.setFillParent(true);

//Window setup
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = new BitmapFont(Gdx.files.internal("skins/DigitalDiscoThin.fnt"));
        windowStyle.stageBackground = new TextureRegionDrawable(new Texture("backgrounds/scripts/textBox.png"));
        windowStyle.stageBackground.setMinHeight(80);
        window = new Window("", windowStyle);
        window2 = new Window("", windowStyle);

//Text setup
        Label.LabelStyle titleStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/DigitalDisco.fnt")), WHITE);
        title = new Label(String.format("title"), titleStyle);
        title.setFontScale(0.4f);

        Label.LabelStyle textStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/DigitalDiscoThin.fnt")), WHITE);
        text = new Label(String.format("Setup"), textStyle);
        text.setFontScale(0.4f);

//Character setup
        character = new Texture("UI/characterSelectRobot.png");
        characterImg = new Image(character);


        dialogUpdate();
        textUpdate();

//Initialising Windows
        window2.add(characterImg);
        window.add(title);
        window.add(text);

//Button Creation
        nextImg = new Texture("UI/buttons/next.png");
        nextDraw = new TextureRegionDrawable(nextImg);
        next = new ImageButton(nextDraw);
        next.setTransform(true);
        next.setScale(2.3f);
        next.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (zeta.getScreen().getClass() == CutsceneScreen.class) {
                    System.out.println("Next Clicked" + sceneId);
                    if (finished) {
                        if (end)
                            zeta.setScreen(new TransitionScreen(zeta.getScreen(), new LevelComplete(zeta, map), zeta, map));
                        else
                            zeta.setScreen(new TransitionScreen(zeta.getScreen(), new PlayScreen(zeta, map), zeta, map));
                    }
                    zeta.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(zeta.getSoundVolume());
                    pass= true;
                }
                return true;
            }
        });

        skipImg = new Texture("UI/buttons/skip.png");
        skipDraw = new TextureRegionDrawable(skipImg);
        skip = new ImageButton(skipDraw);
        skip.setScale(2.3f);
        skip.setTransform(true);
        skip.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("skip Clicked" + skip.isPressed());
                if (zeta.getScreen().getClass() == CutsceneScreen.class) {
                    if (sceneId == 30)
                        zeta.setScreen(new TransitionScreen(zeta.getScreen(), new Credits(zeta), zeta, map));
                    else if (end) {
                        zeta.setScreen(new TransitionScreen(zeta.getScreen(), new LevelComplete(zeta, map), zeta, map));
                    } else {
                        zeta.setScreen(new TransitionScreen(zeta.getScreen(), new PlayScreen(zeta, map), zeta, map));
                    }
                    transition = true;

                    zeta.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(zeta.getSoundVolume());
                }
                return true;
            }
        });

        table.add(window).left();
        table.row();
        table.add(next).expandX();
        table.add(skip).expandX();
        table.row();
        stage.addActor(table);
        bg = new Texture(Gdx.files.internal("backgrounds/scripts/bg" + scripts.get(tempIterator).bg + ".png"));
        window2.setVisible(false);
        window.setVisible(false);
        next.setVisible(false);
        skip.setVisible(false);

        zeta.setSceneTracking(sceneId);

    }

    public void load() {
        scripts = new ArrayList<Script>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).id == sceneId) {
                scripts.add(list.get(i));
            }
        }
        scriptIterator = scripts.size();
    }

    public void dialogUpdate() {
        if (tempIterator <= (scriptIterator - 1)) {
            titleString = scripts.get(tempIterator).titleLine;
            testString = scripts.get(tempIterator).line;
            iconId = scripts.get(tempIterator).icon;
        } else {
            finished = true;
        }
    }

    @Override
    public void render(float delta) {
        update();
        Gdx.input.setInputProcessor(stage);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        zeta.batch.begin();
        zeta.batch.draw(bg, 0, 0, zeta.V_WIDTH, zeta.V_HEIGHT);
        characterImg.setBounds(175, 100, 50, 50);
        characterImg.draw(zeta.batch, 1);
        window.setBounds(20, 15, 320, 30);
        next.setBounds(330, 30, 20, 10);
        skip.setBounds(330, 10, 20, 10);
        zeta.batch.end();
        stage.draw();


    }

    public void textUpdate() {
        title.setText(String.format(titleString));
        text.setText(String.format(testString));
        bg = new Texture(Gdx.files.internal("backgrounds/scripts/bg" + scripts.get(tempIterator).bg + ".png"));
        character = new Texture(Gdx.files.internal("sprites/Icons/Icon" + scripts.get(tempIterator).icon + ".png"));
        characterImg.setDrawable(new TextureRegionDrawable(character));
        if (tempIterator < scriptIterator) {
            if (!finished)
                tempIterator++;
        }
        dialogUpdate();
    }

    public void update() {
        if (zeta.getScreen().getClass() == CutsceneScreen.class && !transition) {
            window2.setVisible(true);
            window.setVisible(true);
            next.setVisible(true);
            skip.setVisible(true);
        }
        if (!finished) {
            dialogUpdate();
            if (pass) {
                textUpdate();
                pass = false;
            }
        }
        if (transition) {
            window2.setVisible(false);
            window.setVisible(false);
            next.setVisible(false);
            skip.setVisible(false);
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
        bg.dispose();
    }

    public int getSceneId() {
        return sceneId;
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void hide() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void show() {

    }
}
