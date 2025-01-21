package com.mygdx.projectZeta.Scenes.Screens;

import static com.badlogic.gdx.graphics.Color.CYAN;
import static com.badlogic.gdx.graphics.Color.MAGENTA;
import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.projectZeta.projectZeta;

import java.util.ArrayList;


public class CharacterSelect implements Screen {
    /* Admin Inits */
    private projectZeta zeta;
    private Viewport viewport;
    private Texture background;
    private int map;

    /* Arrays To Loop through for Selection */
    private ArrayList<Texture> characterSelector;
    private ArrayList<Label> characterNames;
    private int i = 0;

    //Selection Variables
    private TextureAtlas selected;

    //Image variables
    private Image left, right;

    //Stage Variables
    private Table table;
    private Stage stage;

    //Button Style Variables
    private TextButton.TextButtonStyle textStyle;
    private BitmapFont buttonFont;

    //Label Variables
    private Label maleLabel;
    private Label femaleLabel;
    private Label robotLabel;
    private Label arrows;
    private Label enter;
    private Label choose;


    //Class Constructors
    public CharacterSelect(projectZeta game, int level) {
//Variable initalisation
        this.zeta = game;
        viewport = new FitViewport(zeta.V_WIDTH, zeta.V_HEIGHT, new OrthographicCamera());

        map = level;
        background = zeta.manager.get("backgrounds/characterselect.png", Texture.class);


//initialising and instantiating of animatimation arrays
        characterSelector = new ArrayList<Texture>(3);

        characterSelector.add(new Texture("UI/characterSelectFemale.png"));

        characterSelector.add(new Texture("UI/characterSelectMale.png"));

        characterSelector.add(new Texture("UI/characterSelectRobot.png"));


//Label Style Initalisations
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/DigitalDisco.fnt")), WHITE);
        Label.LabelStyle font2 = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skins/DigitalDisco.fnt")), WHITE);

//Text Button Style Initalisations
        textStyle = new TextButton.TextButtonStyle();
        buttonFont = new BitmapFont(Gdx.files.internal("skins/quantum-horizon/raw/font-export.fnt"));
        textStyle.font = buttonFont;
        textStyle.fontColor = WHITE;

//Labels Setup
        arrows = new Label("Arrows To Swap Character", font2);
        enter = new Label("Enter to Select", font2);
        arrows.setFontScale(0.4f, 0.5f);
        enter.setFontScale(0.4f, 0.5f);

//Intiatialising and Names of characters added to the arraylist
        characterNames = new ArrayList<Label>(3);

        femaleLabel = new Label("Roslyn", font);
        maleLabel = new Label("Kobi", font);
        robotLabel = new Label("Dave", font);

        characterNames.add(0, femaleLabel);
        characterNames.add(1, maleLabel);
        characterNames.add(2, robotLabel);


//Using Texture
        Texture button1 = new Texture("controller/right.png");
        Texture button2 = new Texture("controller/left.png");

//Right Button Setup
        right = new Image(button1);
        right.setSize(3 / zeta.PPM, 3 / zeta.PPM);

//Left Button Setup
        left = new Image(button2);
        left.setSize(3 / zeta.PPM, 3 / zeta.PPM);

//Selection Button Setup
        choose = new Label("SELECT ", font2);
        choose.setFontScale(0.4f, 0.5f);

//Setup for android
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
//Initialising stage and table
            stage = new Stage(viewport, zeta.batch);
            table = new Table();
            table.setFillParent(true);
            table.center();

//Filling the Table
            table.add(left).left().size(button2.getWidth() + 10, button2.getHeight() + 10).padRight(30).padTop(20);
            table.add(right).right().size(button1.getWidth() + 10, button1.getHeight() + 10).padLeft(30).padTop(20);
            table.row();
            table.row();
            table.add(choose).center().padLeft(70).padTop(30);

//Passing the table to the Stage and passing the Stage in as the location we want to read inputs from
            stage.addActor(table);
            Gdx.input.setInputProcessor(stage);

//Adding a click Listener for the right arrow button
            right.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (Gdx.app.getType() == Application.ApplicationType.Android) {
                        zeta.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(zeta.getSoundVolume());
                    }
                    if (i == 0) {
                        i = 1;
                        characterNames.get(i);
                        characterNames.get(i);
                        characterSelector.get(i);

                    } else if (i == 1) {
                        i = 2;
                        characterNames.get(i);
                        characterNames.get(i);
                        characterSelector.get(i);

                    } else if (i == 2) {
                        i = 0;
                        characterNames.get(i);
                        characterNames.get(i);
                        characterSelector.get(i);
                    }
                }
            });

//Adding a click Listener for the Left arrow button
            left.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (Gdx.app.getType() == Application.ApplicationType.Android) {
                        zeta.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(zeta.getSoundVolume());
                    }
                    if (i == 0) {
                        i = 2;
                        characterNames.get(i);
                        characterSelector.get(i);
                        characterNames.get(i);

                    } else if (i == 1) {
                        i = 0;
                        characterNames.get(i);
                        characterSelector.get(i);
                        characterNames.get(i);
                    } else if (i == 2) {
                        i = 1;
                        characterNames.get(i);
                        characterSelector.get(i);
                        characterNames.get(i);
                    }
                }
            });

//Adding a click Listener for the Selection button
            choose.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (Gdx.app.getType() == Application.ApplicationType.Android) {
                        zeta.manager.get("audio/sounds/421837__prex2202__blipbutton.mp3", Sound.class).play(zeta.getSoundVolume());
                    }
                    switch (i) {
                        case 0:
                            selected = zeta.getCharacterFemale();
                            break;

                        case 1:
                            selected = zeta.getCharacterMale();
                            break;

                        case 2:
                            selected = zeta.getCharacterRobot();
                            break;

                        default:
                    }
                    zeta.setPlayersChoice(selected);

                    zeta.setScreen(new PlayScreen(zeta, map));
                }
            });
        }

    }

    //Render Method
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.app.getType() == Application.ApplicationType.Desktop)
            update(delta);


//Open the Batch from the main page
        zeta.batch.begin();

        zeta.batch.draw(background, 0, 0, 500, 300);

        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            arrows.setBounds(130, 155, 70, 70);
            arrows.draw(zeta.batch, 1);

            enter.setBounds(160, 5, 70, 70);
            enter.draw(zeta.batch, 1);
        }

        zeta.batch.draw(characterSelector.get(i), 185, 105);

        characterNames.get(0).setBounds(155, 55, 70, 70);
        characterNames.get(1).setBounds(169, 55, 70, 70);
        characterNames.get(2).setBounds(166, 55, 70, 70);
        characterNames.get(i).draw(zeta.batch, 1);

        zeta.batch.end();

        if (Gdx.app.getType() == Application.ApplicationType.Android)
            stage.draw();
    }

    //A getter for retrieving i as i is the choice of Character the player makes
    public int getI() {
        return i;
    }

    //Handling inputs for the Computer when testing for selection
    public void handleInput(float dt) {
        /*Move Left*/
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    zeta.loadSound("audio/sounds/421837__prex2202__blipbutton.mp3");
                    long id = zeta.sound.play();
                    if (zeta.getSoundVolume() != 0)
                        zeta.sound.setVolume(id, zeta.getSoundVolume());
                    else {
                        zeta.sound.setVolume(id, 0);
                    }
                }
                if (i == 0) {
                    i = 2;
                    characterNames.get(i).setColor(WHITE);
                    characterSelector.get(i);
                    characterNames.get(i);

                } else if (i == 1) {
                    i = 0;
                    characterNames.get(i).setColor(WHITE);
                    characterSelector.get(i);
                    characterNames.get(i);
                } else if (i == 2) {
                    i = 1;
                    characterNames.get(i).setColor(WHITE);
                    characterSelector.get(i);
                    characterNames.get(i);
                }
            }

            /*Move Right*/
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    zeta.loadSound("audio/sounds/421837__prex2202__blipbutton.mp3");
                    long id = zeta.sound.play();
                    if (zeta.getSoundVolume() != 0)
                        zeta.sound.setVolume(id, zeta.getSoundVolume());
                    else {
                        zeta.sound.setVolume(id, 0);
                    }
                }
                if (i == 0) {
                    i = 1;
                    characterNames.get(i).setColor(WHITE);
                    characterSelector.get(i);
                    characterNames.get(i);

                } else if (i == 1) {
                    i = 2;
                    characterNames.get(i).setColor(WHITE);
                    characterSelector.get(i);
                    characterNames.get(i);
                } else if (i == 2) {
                    i = 0;
                    characterNames.get(i).setColor(WHITE);
                    characterSelector.get(i);
                    characterNames.get(i);
                }
            }

            /*Selection*/
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    zeta.loadSound("audio/sounds/421837__prex2202__blipbutton.mp3");
                    long id = zeta.sound.play();
                    if (zeta.getSoundVolume() != 0)
                        zeta.sound.setVolume(id, zeta.getSoundVolume());
                    else {
                        zeta.sound.setVolume(id, 0);
                    }
                }
                switch (i) {
                    case 0:
                        selected = zeta.getCharacterFemale();
                        break;

                    case 1:
                        selected = zeta.getCharacterMale();
                        break;

                    case 2:
                        selected = zeta.getCharacterRobot();
                        break;

                    default:
                }


                zeta.setPlayersChoice(selected);
                zeta.setScreen(new PlayScreen(zeta, map));
            }
        }
    }

    public void update(float dt) {
        handleInput(dt);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void show() {

    }


    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    //Trash disposing
    @Override
    public void dispose() {
        stage.dispose();
        System.gc();
    }
}
