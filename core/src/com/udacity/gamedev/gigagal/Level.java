package com.udacity.gamedev.gigagal;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.udacity.gamedev.gigagal.entities.GigaGal;
import com.udacity.gamedev.gigagal.entities.Platform;

/**
 * Created by Quiv on 2015-12-22.
 */
public class Level
{
    GigaGal gigaGal;
    Array<Platform> platforms;

    public Level()
    {
        gigaGal = new GigaGal();
        platforms = new Array<Platform>();
        platforms.add(new Platform(10, 30, 80, 20));
        platforms.add(new Platform(40, 50, 20, 40));
        platforms.add(new Platform(110, 80, 60, 40));
        platforms.add(new Platform(10, 80, 30, 20));
    }

    public void update(float delta)
    {
        gigaGal.update(delta, platforms);
    }

    public void render(SpriteBatch batch, ShapeRenderer renderer)
    {
        batch.begin();

        for (Platform platform: platforms)
            platform.render(batch);

        gigaGal.render(batch);

        batch.end();
    }

}
