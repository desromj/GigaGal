package com.udacity.gamedev.gigagal.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import org.json.simple.JSONObject;

public class Utils {

    public static void drawTextureRegion(SpriteBatch batch, TextureRegion region, Vector2 position, Vector2 offset) {
        drawTextureRegion(batch, region, position.x - offset.x, position.y - offset.y);
    }

    public static void drawTextureRegion(SpriteBatch batch, TextureRegion region, float x, float y) {
        batch.draw(
                region.getTexture(),
                x,
                y,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                1,
                1,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false);
    }

    public static float secondsSince(long timeNanos) {
        return MathUtils.nanoToSec * (TimeUtils.nanoTime() - timeNanos);
    }

    public static <T extends Object> T castJSON(JSONObject o, String key)
    {
        return (T) o.get(key);
    }

    public static Vector2 getJSONObjectXYVector(JSONObject object)
    {
        float x = 0.0f, y = 0.0f;

        if (object.get("x") != null)
            x = ((Number) object.get("x")).floatValue();
        if (object.get("y") != null)
            y = ((Number) object.get("y")).floatValue();

        return new Vector2(x,y);
    }
}
