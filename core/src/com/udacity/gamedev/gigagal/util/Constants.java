package com.udacity.gamedev.gigagal.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {

    // World/Camera
    public static final Color BACKGROUND_COLOR = Color.SKY;
    public static final float WORLD_SIZE = 160;
    public static final float KILL_PLANE = -100;
    public static final float GRAVITY = 10;
    public static final float CHASE_CAM_MOVE_SPEED = WORLD_SIZE;

    public static final String TEXTURE_ATLAS = "images/gigagal.pack.atlas";


    // GigaGal
    public static final Vector2 GIGAGAL_EYE_POSITION = new Vector2(16, 24);
    public static final float GIGAGAL_EYE_HEIGHT = 16.0f;
    public static final float GIGAGAL_STANCE_WIDTH = 21.0f;
    public static final Vector2 GIGAGAL_CANNON_OFFSET = new Vector2(12, -8);
    public static final float GIGAGAL_HEIGHT = 23.0f;
    public static final float GIGAGAL_MOVE_SPEED = 100;
    public static final float GIGAGAL_SHOTS_PER_SECOND = 3.0f;
    public static final float GIGAGAL_SHOT_POWER = 5.0f;

    public static final int GIGAGAL_STARTING_AMMO = 20;

    public static final float JUMP_SPEED = 200;
    public static final Vector2 KNOCKBACK_VELOCITY = new Vector2(200, 200);
    public static final float MAX_JUMP_DURATION = .1f;

    // JSON Tags
    public static final String EXIT_PORTAL_JSON_TAG = "exit-portal";
    public static final String ENEMY_JSON_TAG = "Enemy";
    public static final String POWERUP_JSON_TAG= "powerup";
    public static final String GIGAGAL_JSON_TAG = "walk-1-right";

    // GigaGal Sprites
    public static final String STANDING_RIGHT = "standing-right";
    public static final String STANDING_LEFT = "standing-left";
    public static final String JUMPING_RIGHT = "jumping-right";
    public static final String JUMPING_LEFT = "jumping-left";
    public static final String WALKING_RIGHT_1 = "walk-1-right";
    public static final String WALKING_LEFT_1 = "walk-1-left";
    public static final String WALKING_RIGHT_2 = "walk-2-right";
    public static final String WALKING_LEFT_2 = "walk-2-left";
    public static final String WALKING_RIGHT_3 = "walk-3-right";
    public static final String WALKING_LEFT_3 = "walk-3-left";
    public static final float WALK_LOOP_DURATION = 0.25f;

    // Platform
    public static final String PLATFORM_SPRITE = "platform";
    public static final int PLATFORM_EDGE = 8;

    // Exit Portal
    public static final String EXIT_PORTAL_SPRITE_1 = "exit-portal-1";
    public static final String EXIT_PORTAL_SPRITE_2 = "exit-portal-2";
    public static final String EXIT_PORTAL_SPRITE_3 = "exit-portal-3";
    public static final String EXIT_PORTAL_SPRITE_4 = "exit-portal-4";
    public static final String EXIT_PORTAL_SPRITE_5 = "exit-portal-5";
    public static final String EXIT_PORTAL_SPRITE_6 = "exit-portal-6";

    public static final float EXIT_PORTAL_FRAME_DURATION = 0.2f;

    // Enemy
    public static final String ENEMY_SPRITE = "enemy";
    public static final Vector2 ENEMY_CENTER = new Vector2(14, 22);
    public static final float ENEMY_MOVEMENT_SPEED = 10;
    public static final float ENEMY_BOB_AMPLITUDE = 2;
    public static final float ENEMY_BOB_PERIOD = 3.0f;
    public static final float ENEMY_COLLISION_RADIUS = 15;
    public static final float ENEMY_HEALTH = 25.0f;

    // Bullet
    public static final String BULLET_SPRITE = "bullet";
    public static final float BULLET_HORIZONTAL_SPEED = 180.0f;
    public static final float BULLET_HIT_RADIUS = 2.0f;

    // Powerup
    public static final String POWERUP_SPRITE = "powerup";
    public static final int POWERUP_AMMO_COUNT = 6;
    public static final float POWERUP_COLLISION_RADIUS = 4.0f;

    // Explosion
    public static final String EXPLOSION_SPRITE_1 = "explosion-small";
    public static final String EXPLOSION_SPRITE_2 = "explosion-medium";
    public static final String EXPLOSION_SPRITE_3 = "explosion-large";
    public static final float EXPLOSION_FRAME_DURATION = 0.15f;

}
