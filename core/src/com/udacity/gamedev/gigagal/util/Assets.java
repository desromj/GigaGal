package com.udacity.gamedev.gigagal.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;

/**
 * This utility class holds onto all the assets used in GigaGal. It's a singleton, so the
 * constructor is private, and a single instance is created when the class is loaded. That way all
 * the entities that make up GigaGal can access their sprites in the same place, and no work loading
 * up textures is repeated.
 *
 * Each entity gets its own inner class to hold its assets. Below you'll complete the GigaGalAssets
 * class by finding up the standing-right AtlasRegion within the TextureAtlas loaded in init() .
 */

/**
 * Created by Quiv on 2015-12-22.
 */
public class Assets implements Disposable, AssetErrorListener
{
    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();

    public GigaGalAssets gigaGalAssets;
    private AssetManager assetManager;

    private Assets() { }

    public void init() {
        this.assetManager = new AssetManager();
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);
        gigaGalAssets = new GigaGalAssets(atlas);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable)
    {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public class GigaGalAssets
    {
        // Add an AtlasRegion to hold the standing-right sprite
        public TextureAtlas.AtlasRegion standingRight;
        public TextureAtlas.AtlasRegion standingLeft;
        public TextureAtlas.AtlasRegion jumpingRight;
        public TextureAtlas.AtlasRegion jumpingLeft;

        public TextureAtlas.AtlasRegion walk2Right;
        public TextureAtlas.AtlasRegion walk2Left;

        public GigaGalAssets(TextureAtlas atlas)
        {
            // Use atlas.findRegion() to initialize the standing right AtlasRegion
            TextureRegion standingRightRegion = atlas.findRegion(Constants.GIGAGAL_STANDING_RIGHT);
            standingRight = makeAtlasRegion(standingRightRegion);

            // Use atlas.findRegion() to initialize the standing left AtlasRegion
            TextureRegion standingLeftRegion = atlas.findRegion(Constants.GIGAGAL_STANDING_LEFT);
            standingLeft = makeAtlasRegion(standingLeftRegion);

            // Use atlas.findRegion() to initialize the standing right AtlasRegion
            TextureRegion jumpingRightRegion = atlas.findRegion(Constants.GIGAGAL_JUMPING_RIGHT);
            jumpingRight = makeAtlasRegion(jumpingRightRegion);

            // Use atlas.findRegion() to initialize the standing left AtlasRegion
            TextureRegion jumpingLeftRegion = atlas.findRegion(Constants.GIGAGAL_JUMPING_LEFT);
            jumpingLeft = makeAtlasRegion(jumpingLeftRegion);

            // Use atlas.findRegion() to initialize the standing right AtlasRegion
            TextureRegion walk2RightRegion = atlas.findRegion(Constants.GIGAGAL_WALK_2_RIGHT);
            walk2Right = makeAtlasRegion(walk2RightRegion);

            // Use atlas.findRegion() to initialize the standing left AtlasRegion
            TextureRegion walk2LeftRegion = atlas.findRegion(Constants.GIGAGAL_WALK_2_LEFT);
            walk2Left = makeAtlasRegion(walk2LeftRegion);

        }

    }

    private TextureAtlas.AtlasRegion makeAtlasRegion(TextureRegion textureRegion)
    {
        return new TextureAtlas.AtlasRegion(
                textureRegion.getTexture(),
                textureRegion.getRegionX(),
                textureRegion.getRegionY(),
                textureRegion.getRegionWidth(),
                textureRegion.getRegionHeight()
        );
    }
}
