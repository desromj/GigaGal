package com.udacity.gamedev.gigagal.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Quiv on 2015-12-22.
 */
public class Constants
{
    public static final Color BACKGROUND_COLOR = Color.SKY;

    /**
     * We'll draw our sprites at their natural size, so this is the number of pixels of our Pixel
     * art that will fit on the screen. We're going to use this size to initialize both dimensions
     * of an ExtendViewport, and we'll run the game in landscape mode, so this will really end up
     * specifying the height of the world. We recommend 128.
     */
    public static final int WORLD_SIZE = 128;

    public static final String TEXTURE_ATLAS = "images/gigagal.pack.atlas";

    /*
     Texture Atlas file names
      */
    public static final String GIGAGAL_STANDING_RIGHT = "standing-right";
    public static final String GIGAGAL_STANDING_LEFT = "standing-left";
    public static final String GIGAGAL_JUMPING_RIGHT = "jumping-right";
    public static final String GIGAGAL_JUMPING_LEFT = "jumping-left";

    public static final String GIGAGAL_WALK_1_RIGHT = "walk-1-right";
    public static final String GIGAGAL_WALK_1_LEFT = "walk-1-left";
    public static final String GIGAGAL_WALK_2_RIGHT = "walk-2-right";
    public static final String GIGAGAL_WALK_2_LEFT = "walk-2-left";
    public static final String GIGAGAL_WALK_3_RIGHT = "walk-3-right";
    public static final String GIGAGAL_WALK_3_LEFT = "walk-3-left";

    public static final String PLATFORM_SPRITE = "platform";

    public static final String ENEMY_IDLE = "enemy";

    /*
     Done with texture atlasses
     */
    public static final int PLATFORM_EDGE = 8;

    public static final float GIGAGAL_WALK_LOOP_DURATION = 0.25f;

    // Define a Vector2 Constant for GigaGal's eye position within her sprites (12, 24)
    public static final Vector2 GIGAGAL_EYE_POSITION = new Vector2(16, 24);
    // Define a float constant for the height of GigaGal's eye above her feet (16)
    public static final float GIGAGAL_EYE_HEIGHT = 16.0f;

    public static final float GIGAGAL_STANCE_WIDTH = 21.0f;

    // Add a constant for GigaGal's movement speed
    public static final float GIGAGAL_MOVEMENT_SPEED = WORLD_SIZE / 2;
    public static final float GIGAGAL_JUMP_SPEED = WORLD_SIZE * 1.5f;
    public static final float GIGAGAL_MAX_JUMP_DURATION = 0.1f;

    public static final float ENEMY_PATROL_SPEED = WORLD_SIZE / 8.0f;

    public static final float ACCELERATION_DUE_TO_GRAVITY = WORLD_SIZE / 2 * 9.81f;

    public static final float KILL_PLANE_Y_HEIGHT = -120.0f;
    public static final float DEBUG_CAMERA_MOVE_SPEED = GIGAGAL_MOVEMENT_SPEED * 3.0f;
}
