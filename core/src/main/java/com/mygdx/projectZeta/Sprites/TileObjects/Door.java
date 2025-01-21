package com.mygdx.projectZeta.Sprites.TileObjects;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.utils.Array;
import com.mygdx.projectZeta.Scenes.Screens.PlayScreen;
import com.mygdx.projectZeta.Sprites.Player;
import com.mygdx.projectZeta.projectZeta;

public class Door extends InteractiveTileObject {
    private projectZeta zeta;
    private Animation<TextureRegion> door;
    private TextureRegion closed;
    private boolean open;
    private boolean doorJustTouched = false;
    public int interacted = 0;


    public Door(projectZeta game, PlayScreen screen, MapObject object) {
        super(screen, object);
        this.zeta = game;
        open = false;


        closed = zeta.getObjects().findRegion("door1");


        Array<TextureRegion> doorAnimation = new Array<TextureRegion>();

        for (int i = 1; i <= 4; i++) {
            doorAnimation.add(zeta.getObjects().findRegion("door" + i));
        }

        door = new Animation<TextureRegion>(2f, doorAnimation);

        doorAnimation.clear();

        fixture.setUserData(this);
        setCategoryFilter(projectZeta.DOOR_BIT);
    }

    @Override
    public void onHit(Player player) {

        Gdx.app.log("Door", "Collision");
        if (player.getKey()) {
            doorJustTouched = true;
            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                zeta.loadSound("audio/sounds/364688__alegemaate__electronic-door-opening.wav");
                long id = zeta.sound.play();
                if (zeta.getSoundVolume() != 0) {
                    zeta.sound.setVolume(id, zeta.getSoundVolume());
                } else {
                    zeta.sound.setVolume(id, 0);
                }
            }

            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                zeta.manager.get("audio/sounds/364688__alegemaate__electronic-door-opening.wav", Sound.class).play(zeta.getSoundVolume());
            }
        } else {

            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                zeta.loadSound("audio/sounds/stomp.wav");
                long id = zeta.sound.play();
                if (zeta.getSoundVolume() != 0)
                    zeta.sound.setVolume(id, zeta.getSoundVolume());
                else {
                    zeta.sound.setVolume(id, 0);
                }
            }

            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                zeta.manager.get("audio/sounds/stomp.wav", Sound.class).play(zeta.getSoundVolume());
            }
        }
    }

    public void unlock(float dt) {
        Gdx.app.log("Open", "Triggered");
        open = true;
    }

    public void draw(SpriteBatch batch) {
        if (!open) {
            batch.draw(closed, (bounds.x - 22) / zeta.PPM, bounds.y / zeta.PPM, closed.getRegionWidth() / zeta.PPM, closed.getRegionHeight() / zeta.PPM);
        } else if (open) {
            batch.draw(door.getKeyFrame(objectStateTimer, false), (bounds.x - 22) / zeta.PPM, bounds.y / zeta.PPM, door.getKeyFrame(objectStateTimer).getRegionWidth() / zeta.PPM, door.getKeyFrame(objectStateTimer).getRegionHeight() / zeta.PPM);
        }

    }

    public void setInteracted(int interacted) {
        this.interacted = interacted;
    }

    public Animation<TextureRegion> getDoor() {
        return door;
    }

    public boolean isDoorJustTouched() {
        return doorJustTouched;
    }

    public boolean isOpen() {
        return open;
    }
}
