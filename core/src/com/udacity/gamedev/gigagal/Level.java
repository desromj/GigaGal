package com.udacity.gamedev.gigagal;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.udacity.gamedev.gigagal.entities.GigaGal;

/**
 * Created by Quiv on 2015-12-22.
 */
public class Level
{
    GigaGal gigaGal;

    public Level() {
        gigaGal = new GigaGal();
    }

    public void update(float delta) {
        gigaGal.update(delta);
    }

    public void render(SpriteBatch batch) {
        gigaGal.render(batch);
    }

}
