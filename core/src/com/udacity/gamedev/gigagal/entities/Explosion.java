package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Utils;

/**
 * Created by Mike on 2016-02-08.
 */
public class Explosion
{
    Vector2 position;
    long startTime;

    public Explosion(Vector2 position)
    {
        this.position = new Vector2(position.x, position.y);
        startTime = TimeUtils.nanoTime();
    }

    public boolean isOver()
    {
        float lifeTime = Assets.instance.explosionAssets.explosionAnimation.getAnimationDuration();

        if (((TimeUtils.nanoTime() - this.startTime) * MathUtils.nanoToSec) > lifeTime)
            return true;

        return false;
    }

    public void render(SpriteBatch batch)
    {
        Animation animation = Assets.instance.explosionAssets.explosionAnimation;
        float elapsedTime = (TimeUtils.nanoTime() - startTime) * MathUtils.nanoToSec;
        TextureRegion region = animation.getKeyFrame(elapsedTime, false);

        Utils.drawTextureRegion(
                batch,
                region,
                this.position.x,
                this.position.y);
    }
}
