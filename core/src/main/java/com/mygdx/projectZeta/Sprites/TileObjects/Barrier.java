package com.mygdx.projectZeta.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.projectZeta.Scenes.Screens.PlayScreen;
import com.mygdx.projectZeta.Sprites.Player;
import com.mygdx.projectZeta.projectZeta;

public class Barrier extends InteractiveTileObject {

    public Barrier(PlayScreen screen, MapObject object){
        super(screen,object);
        fixture.setUserData(this);
        setCategoryFilter(projectZeta.BARRIER_BIT);
    }

    @Override
    public void onHit(Player player) {
        Gdx.app.log("Finish", "Collision");
    }
}
