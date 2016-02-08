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
public class Bullet
{
    Vector2 position, velocity;

    public Bullet(Vector2 position, Vector2 velocity)
    {
        this.position = new Vector2(position.x, position.y);
        this.velocity = new Vector2(velocity.x, velocity.y);
    }

    public void update(float delta)
    {
        this.position.x += this.velocity.x * delta;
        this.position.y += this.velocity.y * delta;
    }

    public void render(SpriteBatch batch)
    {
        TextureRegion region = Assets.instance.bulletAssets.bullet;

        Utils.drawTextureRegion(
                batch,
                region,
                this.position.x,
                this.position.y);
    }
}
