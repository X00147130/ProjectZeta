package com.mygdx.projectZeta.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.projectZeta.Scenes.Screens.PlayScreen;
import com.mygdx.projectZeta.Sprites.Player;
import com.mygdx.projectZeta.projectZeta;

public class Sky extends InteractiveTileObject{

    public Sky(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(projectZeta.SKY_BIT);
    }

    @Override
    public void onHit(Player player) {
        Gdx.app.log("sky","limit hit");
        player.reverseVelocity(false,true);
    }

}
