package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Utils;

/**
 * Created by Quiv on 2016-02-23.
 */
public class ExitPortal
{
    Vector2 position;
    long startTime;

    public ExitPortal(Vector2 position)
    {
        this.position = new Vector2(position.x, position.y);
        this.startTime = TimeUtils.nanoTime();
    }

    public void render(SpriteBatch batch)
    {
        Animation animation = Assets.instance.exitPortalAssets.portalAnimation;
        float elapsedTime = (TimeUtils.nanoTime() - startTime) * MathUtils.nanoToSec;
        TextureRegion region = animation.getKeyFrame(elapsedTime, true);

        Utils.drawTextureRegion(batch, region, position, new Vector2());
    }

}
