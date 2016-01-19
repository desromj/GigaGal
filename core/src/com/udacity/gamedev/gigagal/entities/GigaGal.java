package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Constants;

/**
 * Created by Quiv on 2015-12-22.
 */
public class GigaGal
{
    public final static String TAG = GigaGal.class.getName();

    Vector2 position, lastFramePosition;
    Facing facing;

    // Add a Vector2 for GigaGal's velocity
    Vector2 velocity;

    // Add a JumpState
    JumpState jumpState;
    WalkState walkState;

    // Add a long for jumpStartTime
    Long jumpStartTime;
    Long walkStartTime;

    public GigaGal() {
        position = new Vector2(Constants.GIGAGAL_EYE_HEIGHT, Constants.GIGAGAL_EYE_HEIGHT);
        lastFramePosition = new Vector2(position.x, position.y);

        facing = Facing.RIGHT;
        velocity = new Vector2();
        jumpState = JumpState.FALLING;
        walkState = WalkState.STANDING;
    }

    public void update(float delta, Array<Platform> platforms) {

        lastFramePosition.set(position.x, position.y);

        // Accelerate GigaGal down
        // Multiple delta by the acceleration due to gravity and subtract it from GG's vertical velocity
        velocity.sub(0, delta * Constants.ACCELERATION_DUE_TO_GRAVITY);

        // Apply GigaGal's velocity to her position
        // Vector2.mulAdd() is very convenient.
        position.mulAdd(velocity, delta);

        // If GigaGal isn't JUMPING, make her now FALLING
        if (jumpState != JumpState.JUMPING) {
            jumpState = JumpState.FALLING;

            for (Platform platform: platforms)
            {
                if (landedOnPlatform(platform)) {
                    jumpState = JumpState.GROUNDED;
                    velocity.y = 0.0f;
                    position.y = platform.top + Constants.GIGAGAL_EYE_HEIGHT;
                }
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            // Handle jump key
            // Add a switch statement. If the jump key is pressed and GG is GROUNDED, then startJump()
            // If she's JUMPING, then continueJump()
            // If she's falling, then don't do anything
            switch (jumpState)
            {
                case GROUNDED:
                    startJump();
                    break;
                case JUMPING:
                    continueJump();
                    break;
                case FALLING:
                default:
                    break;
            }

        } else {
            // If the jump key wasn't pressed, endJump()
            endJump();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            moveLeft(delta);
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            moveRight(delta);
        else
            walkState = WalkState.STANDING;
    }

    private boolean landedOnPlatform(Platform platform)
    {
        boolean leftFootIn = false, rightFootIn = false, straddle = false;

        if (lastFramePosition.y - Constants.GIGAGAL_EYE_HEIGHT >= platform.top
                && position.y - Constants.GIGAGAL_EYE_HEIGHT < platform.top) {

            float leftFoot = position.x - Constants.GIGAGAL_STANCE_WIDTH / 2;
            float rightFoot = position.x + Constants.GIGAGAL_STANCE_WIDTH / 2;

            leftFootIn = (platform.left < leftFoot && platform.right > leftFoot);
            rightFootIn = (platform.left < rightFoot && platform.right > rightFoot);
            straddle = (platform.left > leftFoot && platform.right < rightFoot);
        }

        return leftFootIn || rightFootIn || straddle;
    }

    private void moveLeft(float delta) {
        if (jumpState == JumpState.GROUNDED && walkState != WalkState.WALKING)
            walkStartTime = TimeUtils.nanoTime();

        this.position.add(-Constants.GIGAGAL_MOVEMENT_SPEED * delta, 0);
        facing = Facing.LEFT;
        walkState = WalkState.WALKING;
    }

    private void moveRight(float delta) {
        if (jumpState == JumpState.GROUNDED && walkState != WalkState.WALKING)
            walkStartTime = TimeUtils.nanoTime();

        this.position.add(Constants.GIGAGAL_MOVEMENT_SPEED * delta, 0);
        facing = Facing.RIGHT;
        walkState = WalkState.WALKING;
    }

    private void startJump() {
        // Set jumpState to JUMPING
        jumpState = JumpState.JUMPING;
        // Set the jump start time
        // Using TimeUtils.nanoTime()
        jumpStartTime = TimeUtils.nanoTime();
        // Call continueJump()
        continueJump();
    }

    private void continueJump() {
        // First, check if we're JUMPING, if not, just return
        if (jumpState != JumpState.JUMPING)
            return;

        // Find out how long we've been jumping
        float jumpingFor = MathUtils.nanoToSec * (TimeUtils.nanoTime() - jumpStartTime);

        // If we have been jumping for less than the max jump duration, set GG's vertical speed to the jump speed constant
        // Else, call endJump()
        if (jumpingFor < Constants.GIGAGAL_MAX_JUMP_DURATION)
            velocity.y = Constants.GIGAGAL_JUMP_SPEED;
        else
            endJump();
    }

    private void endJump() {
        // If we're JUMPING, now we're FALLING
        if (jumpState == JumpState.JUMPING)
            jumpState = JumpState.FALLING;
    }

    public void render(SpriteBatch batch) {

        TextureRegion atlasRegion;

        switch (this.facing)
        {
            case LEFT:

                if (jumpState == JumpState.GROUNDED) {

                    if (walkState == WalkState.STANDING) {
                        atlasRegion = Assets.instance.gigaGalAssets.standingLeft;
                    } else {
                        float walkingTime = MathUtils.nanoToSec * (TimeUtils.nanoTime() - walkStartTime);
                        atlasRegion = Assets.instance.gigaGalAssets.walkingLeft.getKeyFrame(walkingTime, true);
                    }

                } else {
                    atlasRegion = Assets.instance.gigaGalAssets.jumpingLeft;
                }

                break;

            case RIGHT:
            default:

                if (jumpState == JumpState.GROUNDED) {
                    if (walkState == WalkState.STANDING) {
                        atlasRegion = Assets.instance.gigaGalAssets.standingRight;
                    } else {
                        float walkingTime = MathUtils.nanoToSec * (TimeUtils.nanoTime() - walkStartTime);
                        atlasRegion = Assets.instance.gigaGalAssets.walkingRight.getKeyFrame(walkingTime, true);
                    }

                } else {
                    atlasRegion = Assets.instance.gigaGalAssets.jumpingRight;
                }

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

    public Vector2 getPosition()
    {
        return this.position;
    }

    public void setPosition(Vector2 newPos)
    {
        this.position.x = newPos.x;
        this.position.y = newPos.y;
    }

    // Do this first! Add a JumpState enum containing JUMPING, FALLING, and GROUNDED
    public enum JumpState
    {
        JUMPING,
        FALLING,
        GROUNDED
    }

    public enum Facing
    {
        RIGHT,
        LEFT
    }

    public enum WalkState
    {
        STANDING,
        WALKING;
    }
}
