package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Constants;

/**
 * Created by Quiv on 2015-12-22.
 */
public class GigaGal
{
    public final static String TAG = GigaGal.class.getName();

    public enum Facing
    {
        RIGHT,
        LEFT
    }

    Vector2 position;
    Facing facing;

    public GigaGal() {
        position = new Vector2(Constants.GIGAGAL_EYE_HEIGHT, Constants.GIGAGAL_EYE_HEIGHT);
        facing = Facing.RIGHT;
    }

    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            moveLeft(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            moveRight(delta);
    }

    private void moveLeft(float delta) {
        this.position.add(-Constants.GIGAGAL_MOVEMENT_SPEED * delta, 0);
        facing = Facing.LEFT;
    }

    private void moveRight(float delta) {
        this.position.add(Constants.GIGAGAL_MOVEMENT_SPEED * delta, 0);
        facing = Facing.RIGHT;
    }

    public void render(SpriteBatch batch) {

        TextureAtlas.AtlasRegion atlasRegion;

        switch (this.facing)
        {
            case LEFT:
                atlasRegion = Assets.instance.gigaGalAssets.standingLeft;
                break;

            case RIGHT:
            default:
                atlasRegion = Assets.instance.gigaGalAssets.standingRight;
                break;
        }

        batch.draw(
                atlasRegion.getTexture(),
                this.position.x - Constants.GIGAGAL_EYE_POSITION.x,
                this.position.y - Constants.GIGAGAL_EYE_POSITION.y,
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
}
