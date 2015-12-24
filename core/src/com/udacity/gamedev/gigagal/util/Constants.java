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

    // Add a constant for the name of the standing-right sprite
    public static final String GIGAGAL_STANDING_RIGHT = "standing-right";
    public static final String GIGAGAL_STANDING_LEFT = "standing-left";

    // Define a Vector2 Constant for GigaGal's eye position within her sprites (12, 24)
    public static final Vector2 GIGAGAL_EYE_POSITION = new Vector2(12, 24);

    // Define a float constant for the height of GigaGal's eye above her feet (16)
    public static final float GIGAGAL_EYE_HEIGHT = 16.0f;

    // Add a constant for GigaGal's movement speed
    public static float GIGAGAL_MOVEMENT_SPEED = 75.0f;
}
