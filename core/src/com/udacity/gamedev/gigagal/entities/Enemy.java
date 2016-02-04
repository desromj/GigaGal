package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Constants;

/**
 * Created by Mike on 2016-02-04.
 */
public class Enemy
{
    Platform patrolPlatform;
    Vector2 position, velocity;
    PatrolDirection patrolDirection;

    public Enemy(Platform patrolPlatform)
    {
        this.patrolPlatform = patrolPlatform;
        this.patrolDirection = PatrolDirection.RIGHT;

        this.position = new Vector2(
                patrolPlatform.left,
                patrolPlatform.top);

        this.velocity = new Vector2(Constants.ENEMY_PATROL_SPEED, 0.0f);
    }

    public void update(float delta)
    {
        this.position.x += velocity.x * delta;

        if (position.x < patrolPlatform.left - getIdleSpriteWidth() / 2.0f) {
            velocity.x = -velocity.x;
            this.patrolDirection = PatrolDirection.RIGHT;
        }
        if (position.x > patrolPlatform.right - getIdleSpriteWidth() / 2.0f) {
            velocity.x = -velocity.x;
            this.patrolDirection = PatrolDirection.LEFT;
        }
    }

    public float getIdleSpriteWidth()
    {
        return Assets.instance.enemyAssets.idle.getRegionWidth();
    }

    public void render(SpriteBatch batch)
    {
        TextureRegion atlasRegion = Assets.instance.enemyAssets.idle;

        batch.draw(
                atlasRegion.getTexture(),
                this.position.x,
                this.position.y,
                0,
                0,
                atlasRegion.getRegionWidth(),
                atlasRegion.getRegionHeight(),
                1,
                1,
                0,
                atlasRegion.getRegionX(),
                atlasRegion.getRegionY(),
                atlasRegion.getRegionWidth(),
                atlasRegion.getRegionHeight(),
                false,
                false
        );
    }

    public enum PatrolDirection
    {
        LEFT,
        RIGHT
    }
}
