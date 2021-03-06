package com.udacity.gamedev.gigagal.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.udacity.gamedev.gigagal.Level;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Constants;
import com.udacity.gamedev.gigagal.util.Enums;
import com.udacity.gamedev.gigagal.util.Enums.Direction;
import com.udacity.gamedev.gigagal.util.Enums.JumpState;
import com.udacity.gamedev.gigagal.util.Enums.WalkState;
import com.udacity.gamedev.gigagal.util.Utils;

public class GigaGal {

    public final static String TAG = GigaGal.class.getName();

    Vector2 spawnLocation;
    Vector2 position;
    Vector2 lastFramePosition;
    Vector2 velocity;

    Direction facing;
    JumpState jumpState;
    WalkState walkState;

    long walkStartTime;
    long jumpStartTime;

    float shotDelay;
    float cannotShootFor;

    int ammo;

    Level level;

    public GigaGal(Vector2 spawnLocation, Level level) {
        this.spawnLocation = spawnLocation;
        this.level = level;
        position = new Vector2();
        lastFramePosition = new Vector2();
        velocity = new Vector2();
        init();
    }


    public void init() {
        position.set(spawnLocation);
        lastFramePosition.set(spawnLocation);
        velocity.setZero();
        jumpState = Enums.JumpState.FALLING;
        facing = Direction.RIGHT;
        walkState = Enums.WalkState.NOT_WALKING;

        cannotShootFor = 0.0f;
        this.shotDelay = 1.0f / Constants.GIGAGAL_SHOTS_PER_SECOND;
        this.ammo = Constants.GIGAGAL_STARTING_AMMO;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void update(float delta, Array<Platform> platforms) {

        cannotShootFor -= delta;

        lastFramePosition.set(position);
        velocity.y -= Constants.GRAVITY;
        position.mulAdd(velocity, delta);

        if (position.y < Constants.KILL_PLANE) {
            init();
        }

        // Land on/fall off platforms
        if (jumpState != Enums.JumpState.JUMPING) {
            // If GigaGal is not RECOILING, set FALLING jump state
            if (jumpState != JumpState.RECOILING) {
                jumpState = Enums.JumpState.FALLING;
            }

            for (Platform platform : platforms) {
                if (landedOnPlatform(platform)) {
                    jumpState = Enums.JumpState.GROUNDED;
                    velocity.y = 0;
                    // Zero horizontal velocity
                    velocity.x = 0;
                    position.y = platform.top + Constants.GIGAGAL_EYE_HEIGHT;
                }
            }
        }

        // Collide with enemies

        Rectangle gigaGalBounds = new Rectangle(
                position.x - Constants.GIGAGAL_STANCE_WIDTH / 2,
                position.y - Constants.GIGAGAL_EYE_HEIGHT,
                Constants.GIGAGAL_STANCE_WIDTH,
                Constants.GIGAGAL_HEIGHT);

        for (Enemy enemy : level.getEnemies()) {
            Rectangle enemyBounds = new Rectangle(
                    enemy.position.x - Constants.ENEMY_COLLISION_RADIUS,
                    enemy.position.y - Constants.ENEMY_COLLISION_RADIUS,
                    2 * Constants.ENEMY_COLLISION_RADIUS,
                    2 * Constants.ENEMY_COLLISION_RADIUS
            );
            if (gigaGalBounds.overlaps(enemyBounds)) {

                if (position.x < enemy.position.x) {
                    recoilFromEnemy(Direction.LEFT);
                } else {
                    recoilFromEnemy(Direction.RIGHT);
                }
            }
        }

        // Move left/right
        // Disable left/right movement if RECOILING
        if (jumpState != JumpState.RECOILING) {
            if (Gdx.input.isKeyPressed(Keys.LEFT)) {
                moveLeft(delta);
            } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
                moveRight(delta);
            } else {
                walkState = Enums.WalkState.NOT_WALKING;
            }
        }

        // Jump
        if (Gdx.input.isKeyPressed(Keys.Z)) {
            switch (jumpState) {
                case GROUNDED:
                    startJump();
                    break;
                case JUMPING:
                    continueJump();
            }
        } else {
            endJump();
        }


    }

    public void shootBullet(Array<Bullet> bullets)
    {
        if (this.ammo > 0 && this.cannotShootFor <= 0.0f)
        {
            cannotShootFor = shotDelay;
            ammo--;

            Vector2 spawnVelocity = new Vector2(
                    (facing == Direction.LEFT) ? -Constants.BULLET_HORIZONTAL_SPEED : Constants.BULLET_HORIZONTAL_SPEED,
                    0.0f
            );

            bullets.add(new Bullet(
                    new Vector2(
                            this.position.x + ((facing == Direction.LEFT) ? -Constants.GIGAGAL_CANNON_OFFSET.x : Constants.GIGAGAL_CANNON_OFFSET.x),
                            this.position.y + Constants.GIGAGAL_CANNON_OFFSET.y
                    ),
                    spawnVelocity
            ));
        }
    }

    public void addAmmo(Powerup ammoPowerup)
    {
        this.ammo += ammoPowerup.getAmmoCount();
    }

    boolean landedOnPlatform(Platform platform) {
        boolean leftFootIn = false;
        boolean rightFootIn = false;
        boolean straddle = false;

        if (lastFramePosition.y - Constants.GIGAGAL_EYE_HEIGHT >= platform.top &&
                position.y - Constants.GIGAGAL_EYE_HEIGHT < platform.top) {

            float leftFoot = position.x - Constants.GIGAGAL_STANCE_WIDTH / 2;
            float rightFoot = position.x + Constants.GIGAGAL_STANCE_WIDTH / 2;

            leftFootIn = (platform.left < leftFoot && platform.right > leftFoot);
            rightFootIn = (platform.left < rightFoot && platform.right > rightFoot);
            straddle = (platform.left > leftFoot && platform.right < rightFoot);
        }
        return leftFootIn || rightFootIn || straddle;
    }


    private void moveLeft(float delta) {
        if (jumpState == Enums.JumpState.GROUNDED && walkState != Enums.WalkState.WALKING) {
            walkStartTime = TimeUtils.nanoTime();
        }
        walkState = Enums.WalkState.WALKING;
        facing = Direction.LEFT;
        position.x -= delta * Constants.GIGAGAL_MOVE_SPEED;
    }

    private void moveRight(float delta) {
        if (jumpState == Enums.JumpState.GROUNDED && walkState != Enums.WalkState.WALKING) {
            walkStartTime = TimeUtils.nanoTime();
        }
        walkState = Enums.WalkState.WALKING;
        facing = Direction.RIGHT;
        position.x += delta * Constants.GIGAGAL_MOVE_SPEED;
    }

    private void startJump() {
        jumpState = Enums.JumpState.JUMPING;
        jumpStartTime = TimeUtils.nanoTime();
        continueJump();
    }

    private void continueJump() {
        if (jumpState == Enums.JumpState.JUMPING) {
            if (Utils.secondsSince(jumpStartTime) < Constants.MAX_JUMP_DURATION) {
                velocity.y = Constants.JUMP_SPEED;
            } else {
                endJump();
            }
        }
    }

    private void endJump() {
        if (jumpState == Enums.JumpState.JUMPING) {
            jumpState = Enums.JumpState.FALLING;
        }
    }

    private void recoilFromEnemy(Direction direction) {

        // Set RECOILING jump state
        jumpState = JumpState.RECOILING;
        velocity.y = Constants.KNOCKBACK_VELOCITY.y;

        if (direction == Direction.LEFT) {
            velocity.x = -Constants.KNOCKBACK_VELOCITY.x;
        } else {
            velocity.x = Constants.KNOCKBACK_VELOCITY.x;
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion region = Assets.instance.gigaGalAssets.standingRight;

        if (facing == Direction.RIGHT && jumpState != Enums.JumpState.GROUNDED) {
            region = Assets.instance.gigaGalAssets.jumpingRight;
        } else if (facing == Direction.RIGHT && walkState == Enums.WalkState.NOT_WALKING) {
            region = Assets.instance.gigaGalAssets.standingRight;
        } else if (facing == Direction.RIGHT && walkState == Enums.WalkState.WALKING) {
            float walkTimeSeconds = Utils.secondsSince(walkStartTime);
            region = Assets.instance.gigaGalAssets.walkingRightAnimation.getKeyFrame(walkTimeSeconds);
        } else if (facing == Direction.LEFT && jumpState != Enums.JumpState.GROUNDED) {
            region = Assets.instance.gigaGalAssets.jumpingLeft;
        } else if (facing == Direction.LEFT && walkState == Enums.WalkState.NOT_WALKING) {
            region = Assets.instance.gigaGalAssets.standingLeft;
        } else if (facing == Direction.LEFT && walkState == Enums.WalkState.WALKING) {
            float walkTimeSeconds = Utils.secondsSince(walkStartTime);
            region = Assets.instance.gigaGalAssets.walkingLeftAnimation.getKeyFrame(walkTimeSeconds);
        }

        Utils.drawTextureRegion(batch, region,
                position.x - Constants.GIGAGAL_EYE_POSITION.x,
                position.y - Constants.GIGAGAL_EYE_POSITION.y
        );

    }

}
