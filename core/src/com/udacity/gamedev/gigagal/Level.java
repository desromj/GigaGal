package com.udacity.gamedev.gigagal;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.udacity.gamedev.gigagal.entities.GigaGal;
import com.udacity.gamedev.gigagal.entities.Platform;
import com.udacity.gamedev.gigagal.util.Constants;

/**
 * Created by Quiv on 2015-12-22.
 */
public class Level
{
    GigaGal gigaGal;
    Array<Platform> platforms;
    Vector2 spawnPosition;

    public Level()
    {
        gigaGal = new GigaGal();
        spawnPosition = new Vector2(gigaGal.getPosition().x, gigaGal.getPosition().y);

        platforms = new Array<Platform>();
        platforms.add(new Platform(10, 30, 80, 20));
        platforms.add(new Platform(40, 50, 20, 20));
        platforms.add(new Platform(110, 80, 60, 40));
        platforms.add(new Platform(10, 80, 30, 20));
        platforms.add(new Platform(-2, -2, 60, 24));

        platforms.add(new Platform(230, 40, 60, 24));
        platforms.add(new Platform(300, 70, 24, 40));
        platforms.add(new Platform(260, 90, 8, 8));
        platforms.add(new Platform(280, 120, 8, 8));
        platforms.add(new Platform(260, 150, 80, 8));

        platforms.add(new Platform(400, 60, 120, 24));
    }

    public void update(float delta)
    {
        gigaGal.update(delta, platforms);

        if (gigaGal.getPosition().y < Constants.KILL_PLANE_Y_HEIGHT)
            gigaGal.setPosition(spawnPosition);
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
