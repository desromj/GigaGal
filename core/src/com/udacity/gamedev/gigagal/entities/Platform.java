package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.udacity.gamedev.gigagal.util.Assets;

/**
 * Created by Quiv on 2015-12-22.
 */
public class Platform
{
    float
        left,
        right,
        top,
        bottom;

    public Platform(float left, float top, float width, float height)
    {
        this.left = left;
        this.top = top;
        this.right = left + width;
        this.bottom = top - height;
    }

    public void render(SpriteBatch batch)
    {
        float width = right - left;
        float height = top - bottom;

        Assets.instance.platformAssets.platformNinePatch.draw(
                batch,
                left,
                bottom,
                width,
                height
        );
    }
}
