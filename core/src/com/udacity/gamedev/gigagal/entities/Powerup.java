package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Utils;

/**
 * Created by Mike on 2016-02-08.
 */
public class Powerup
{
    Vector2 position;

    public Powerup(Vector2 position)
    {
        this.position = new Vector2(position.x, position.y);
    }

    public void update(float delta)
    {

    }

    public void render(SpriteBatch batch)
    {
        TextureRegion region = Assets.instance.powerupAssets.powerup;

        Utils.drawTextureRegion(
                batch,
                region,
                this.position.x,
                this.position.y);
    }
}
