package com.udacity.gamedev.gigagal.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.gamedev.gigagal.Level;
import com.udacity.gamedev.gigagal.entities.Enemy;
import com.udacity.gamedev.gigagal.entities.ExitPortal;
import com.udacity.gamedev.gigagal.entities.GigaGal;
import com.udacity.gamedev.gigagal.entities.Platform;
import com.udacity.gamedev.gigagal.entities.Powerup;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Comparator;

/**
 * Created by Mike on 2016-02-26.
 */
public class LevelLoader
{
    public static final String TAG = LevelLoader.class.getName();

    private LevelLoader() {}

    public static Level load(String levelPath, Viewport viewport)
    {
        Level level = new Level(viewport);

        JSONParser parser = new JSONParser();
        JSONObject levelJson;

        try
        {
            levelJson = (JSONObject) parser.parse(Gdx.files.internal(levelPath).readString());

            // Load images and 9patchs from the JSON file
            JSONArray nonPlatforms = (JSONArray) ((JSONObject)levelJson.get("composite")).get("sImages");
            JSONArray platforms = (JSONArray) ((JSONObject)levelJson.get("composite")).get("sImage9patchs");

            // Load platforms and enemies first - enemies depend on them
            loadPlatforms(platforms, level);

            // Load all other level objects
            loadOtherObjects(nonPlatforms, level);
        }
        catch (ParseException ex)
        {
            Gdx.app.error(TAG, "Could not parse JSON correctly. Message: " + ex.getMessage());
        }

        return level;
    }

    private static void loadOtherObjects(JSONArray objects, Level level)
    {
        for (Object o: objects)
        {
            JSONObject item = (JSONObject) o;
            Vector2 itemXYPos = Utils.getJSONObjectXYVector(item);

            // Add powerups
            if (item.get("imageName").equals("powerup"))
            {
                level.getPowerups().add(new Powerup(itemXYPos));
                continue;
            }

            // Add exit portal
            if (item.get("imageName").equals("exit-portal"))
            {
                ExitPortal portal = new ExitPortal(itemXYPos);
                level.setExitPortal(portal);
                continue;
            }

            // Add GigaGal
            if (item.get("imageName").equals("walk-1-right"))
            {
                // Extra height so GigaGal falls onto first platform
                itemXYPos.y += Constants.GIGAGAL_HEIGHT;
                GigaGal gigaGal = new GigaGal(itemXYPos, level);
                level.setGigaGal(gigaGal);
                continue;
            }
        }
    }

    private static void loadPlatforms(JSONArray platforms, Level level)
    {
        Array<Platform> result = new Array<Platform>();

        for (Object plat: platforms)
        {
            final JSONObject platformObj = (JSONObject) plat;

            final float x = ((Number) platformObj.get("x")).floatValue();
            final float y = ((Number) platformObj.get("y")).floatValue();
            final float width = ((Number) platformObj.get("width")).floatValue();
            final float height = ((Number) platformObj.get("height")).floatValue();

            final Platform platform = new Platform(x, y + height, width, height);
            result.add(platform);

            Gdx.app.log(TAG, "Added a platform at x = " + x);

            // Add enemy to the platform, if necessary
            final String identifier = (String) platformObj.get("itemIdentifier");

            if (identifier != null && identifier.equals("Enemy"))
            {
                final Enemy enemy = new Enemy(platform);
                level.addEnemy(enemy);
            }
        }

        result.sort(new Comparator<Platform>() {
            @Override
            public int compare(Platform o1, Platform o2) {
                if (o1.top < o2.top)
                    return 1;
                else if (o1.top > o2.top)
                    return -1;
                else
                    return 0;
            }
        });

        level.getPlatforms().addAll(result);
    }
}
