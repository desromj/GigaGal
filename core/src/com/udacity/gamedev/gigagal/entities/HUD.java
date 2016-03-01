package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Mike on 2016-03-01.
 */
public class HUD
{
    Viewport viewport;

    BitmapFont font;

    public HUD(Viewport viewport)
    {
        this.viewport = viewport;
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public void render(SpriteBatch batch)
    {
        font.draw(
                batch,
                "lol test text",
                viewport.getWorldWidth() / 2.0f,
                viewport.getWorldHeight() / 2.0f,
                0.0f,
                Align.center,
                false
        );
    }
}
