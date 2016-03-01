package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Utils;

/**
 * Created by Quiv on 2016-02-23.
 */
public class ExitPortal
{
    Vector2 position;

    public ExitPortal(Vector2 position)
    {
        this.position = new Vector2(position.x, position.y);
    }

    public void render(SpriteBatch batch)
    {
        final TextureRegion region = Assets.instance.exitPortalAssets.portal;
        Utils.drawTextureRegion(batch, region, position, new Vector2());
    }

}
