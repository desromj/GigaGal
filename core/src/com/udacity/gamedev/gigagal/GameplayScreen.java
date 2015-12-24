package com.udacity.gamedev.gigagal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Constants;

/**
 * Created by Quiv on 2015-12-22.
 */
public class GameplayScreen extends ScreenAdapter
{
    public static final String TAG = GameplayScreen.class.getName();

    SpriteBatch spriteBatch;
    ExtendViewport viewport;

    Level level;

    @Override
    public void show() {
        Assets.instance.init();
        spriteBatch = new SpriteBatch();
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        level = new Level();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
        spriteBatch.dispose();
    }

    @Override
    public void render(float delta) {
        level.update(delta);

        // Apply the viewport
        viewport.apply();

        // Clear the screen to the BACKGROUND_COLOR
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, Constants.BACKGROUND_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Set the SpriteBatch's projection matrix
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        // Begin the SpriteBatch
        spriteBatch.begin();

        level.render(spriteBatch);

        // End the SpriteBatch
        spriteBatch.end();
    }
}
