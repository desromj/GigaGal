package com.udacity.gamedev.gigagal.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;


public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();

    public GigaGalAssets gigaGalAssets;
    public PlatformAssets platformAssets;
    public EnemyAssets enemyAssets;
    public BulletAssets bulletAssets;
    public PowerupAssets powerupAssets;
    public ExplosionAssets explosionAssets;
    public ExitPortalAssets exitPortalAssets;

    private AssetManager assetManager;


    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);

        gigaGalAssets = new GigaGalAssets(atlas);
        platformAssets = new PlatformAssets(atlas);
        enemyAssets = new EnemyAssets(atlas);
        bulletAssets = new BulletAssets(atlas);
        powerupAssets = new PowerupAssets(atlas);
        explosionAssets = new ExplosionAssets(atlas);
        exitPortalAssets = new ExitPortalAssets(atlas);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public class GigaGalAssets {

        public final AtlasRegion standingLeft;
        public final AtlasRegion standingRight;
        public final AtlasRegion walkingLeft;
        public final AtlasRegion walkingRight;
        public final AtlasRegion jumpingLeft;
        public final AtlasRegion jumpingRight;

        public final Animation walkingLeftAnimation;
        public final Animation walkingRightAnimation;


        public GigaGalAssets(TextureAtlas atlas) {
            standingLeft = atlas.findRegion(Constants.STANDING_LEFT);
            standingRight = atlas.findRegion(Constants.STANDING_RIGHT);
            walkingLeft = atlas.findRegion(Constants.WALKING_LEFT_2);
            walkingRight = atlas.findRegion(Constants.WALKING_RIGHT_2);

            jumpingLeft = atlas.findRegion(Constants.JUMPING_LEFT);
            jumpingRight = atlas.findRegion(Constants.JUMPING_RIGHT);

            Array<AtlasRegion> walkingLeftFrames = new Array<AtlasRegion>();
            walkingLeftFrames.add(atlas.findRegion(Constants.WALKING_LEFT_2));
            walkingLeftFrames.add(atlas.findRegion(Constants.WALKING_LEFT_1));
            walkingLeftFrames.add(atlas.findRegion(Constants.WALKING_LEFT_2));
            walkingLeftFrames.add(atlas.findRegion(Constants.WALKING_LEFT_3));
            walkingLeftAnimation = new Animation(Constants.WALK_LOOP_DURATION, walkingLeftFrames, PlayMode.LOOP);

            Array<AtlasRegion> walkingRightFrames = new Array<AtlasRegion>();
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_2));
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_1));
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_2));
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_3));
            walkingRightAnimation = new Animation(Constants.WALK_LOOP_DURATION, walkingRightFrames, PlayMode.LOOP);
        }
    }

    public class PlatformAssets {

        public final NinePatch platformNinePatch;

        public PlatformAssets(TextureAtlas atlas) {
            AtlasRegion region = atlas.findRegion(Constants.PLATFORM_SPRITE);
            int edge = Constants.PLATFORM_EDGE;
            platformNinePatch = new NinePatch(region, edge, edge, edge, edge);
        }
    }


    public class EnemyAssets {

        public final AtlasRegion enemy;

        public EnemyAssets(TextureAtlas atlas) {
            enemy = atlas.findRegion(Constants.ENEMY_SPRITE);
        }
    }

    public class BulletAssets {
        public final AtlasRegion bullet;

        public BulletAssets(TextureAtlas atlas) {
            bullet = atlas.findRegion(Constants.BULLET_SPRITE);
        }
    }

    public class PowerupAssets {
        public final AtlasRegion powerup;

        public PowerupAssets(TextureAtlas atlas) {
            powerup = atlas.findRegion(Constants.POWERUP_SPRITE);
        }
    }

    public class ExitPortalAssets {
        public final Animation portalAnimation;

        public ExitPortalAssets(TextureAtlas atlas) {
            Array<AtlasRegion> portalFrames = new Array<AtlasRegion>();

            portalFrames.add(atlas.findRegion(Constants.EXIT_PORTAL_SPRITE_1));
            portalFrames.add(atlas.findRegion(Constants.EXIT_PORTAL_SPRITE_2));
            portalFrames.add(atlas.findRegion(Constants.EXIT_PORTAL_SPRITE_3));
            portalFrames.add(atlas.findRegion(Constants.EXIT_PORTAL_SPRITE_4));
            portalFrames.add(atlas.findRegion(Constants.EXIT_PORTAL_SPRITE_5));
            portalFrames.add(atlas.findRegion(Constants.EXIT_PORTAL_SPRITE_6));

            portalAnimation = new Animation(Constants.EXIT_PORTAL_FRAME_DURATION, portalFrames, PlayMode.LOOP);
        }
    }

    public class ExplosionAssets {
        public final Animation explosionAnimation;

        public ExplosionAssets(TextureAtlas atlas){
            Array<AtlasRegion> explosionFrames = new Array<AtlasRegion>();
            explosionFrames.add(atlas.findRegion(Constants.EXPLOSION_SPRITE_1));
            explosionFrames.add(atlas.findRegion(Constants.EXPLOSION_SPRITE_2));
            explosionFrames.add(atlas.findRegion(Constants.EXPLOSION_SPRITE_3));
            explosionAnimation = new Animation(Constants.EXPLOSION_FRAME_DURATION, explosionFrames, PlayMode.NORMAL);
        }
    }

}
