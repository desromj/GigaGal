package com.udacity.gamedev.gigagal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.gamedev.gigagal.entities.Bullet;
import com.udacity.gamedev.gigagal.entities.Enemy;
import com.udacity.gamedev.gigagal.entities.Explosion;
import com.udacity.gamedev.gigagal.entities.GigaGal;
import com.udacity.gamedev.gigagal.entities.Platform;
import com.udacity.gamedev.gigagal.entities.Powerup;
import com.udacity.gamedev.gigagal.util.Assets;
import com.udacity.gamedev.gigagal.util.Constants;
import com.udacity.gamedev.gigagal.util.Utils;

public class Level {

    public static final String TAG = Level.class.getName();

    private Viewport viewport;

    private GigaGal gigaGal;
    private Array<Platform> platforms;

    private DelayedRemovalArray<Enemy> enemies;
    private DelayedRemovalArray<Bullet> bullets;
    private DelayedRemovalArray<Explosion> explosions;
    private DelayedRemovalArray<Powerup> powerups;

    public Level(Viewport viewport) {
        this.viewport = viewport;
        // initializeDebugLevel();
        init();
    }

    public void init()
    {
        platforms = new Array<Platform>();
        enemies = new DelayedRemovalArray<Enemy>();
        bullets = new DelayedRemovalArray<Bullet>();
        explosions = new DelayedRemovalArray<Explosion>();
        powerups = new DelayedRemovalArray<Powerup>();
    }

    public void update(float delta) {
        // Update GigaGal
        gigaGal.update(delta, platforms);


        // Update Enemies
        for (int i = 0; i < enemies.size; i++) {
            Enemy enemy = enemies.get(i);
            enemy.update(delta);
        }

        // Update Bullets
        for (int i = 0; i < bullets.size; i++) {
            bullets.get(i).update(delta);
        }

        // Update Explosions, and remove if necessary
        for (int i = 0; i < explosions.size; i++)
        {
            if (explosions.get(i).isOver())
                explosions.removeIndex(i);
        }

        // Timing is handled in the gigagal class
        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            gigaGal.shootBullet(bullets);
        }

        // Handle bullets offscreen
        removeBullets();
        checkBulletEnemyCollision();
        checkPowerupPickup();
    }

    private void checkPowerupPickup()
    {
        float collisionDist = Constants.POWERUP_COLLISION_RADIUS + Constants.GIGAGAL_STANCE_WIDTH;
        Vector2 ggPos = gigaGal.getPosition();

        for (int i = 0; i < powerups.size; i++)
        {
            Vector2 puPos = powerups.get(i).getPosition();

            if (Vector2.dst(ggPos.x, ggPos.y, puPos.x, puPos.y) < collisionDist)
            {
                gigaGal.addAmmo(powerups.get(i));
                powerups.removeIndex(i);
            }
        }
    }

    private void checkBulletEnemyCollision()
    {
        float collisionDist = Constants.ENEMY_COLLISION_RADIUS + Constants.BULLET_HIT_RADIUS;

        for (int b = 0; b < bullets.size; b++)
        {
            Bullet bullet = bullets.get(b);
            Vector2 bulletPos = bullet.getPosition();

            for (int e = 0; e < enemies.size; e++)
            {
                Enemy enemy = enemies.get(e);

                if (Vector2.dst(enemy.position.x, enemy.position.y, bulletPos.x, bulletPos.y) < collisionDist)
                {
                    enemy.hit(bullet);
                    spawnExplosion(bulletPos);

                    bullets.removeIndex(b);

                    if (enemy.isDead()) {
                        spawnExplosion(enemy.position);
                        enemies.removeIndex(e);
                    }
                }
            }
        }
    }

    private void removeBullets()
    {
        for (int i = 0; i < bullets.size; i++)
        {
            Bullet bullet = bullets.get(i);

            if (Math.abs(bullet.getPosition().x - gigaGal.getPosition().x) > viewport.getWorldWidth() / 2.0f + Constants.GIGAGAL_STANCE_WIDTH)
            {
                bullets.removeIndex(i);
            }
        }
    }

    public void spawnExplosion(Vector2 position)
    {
        explosions.add(new Explosion(position));
    }

    public void render(SpriteBatch batch) {

        for (Platform platform : platforms) {
            platform.render(batch);
        }

        for (Enemy enemy : enemies) {
            enemy.render(batch);
        }

        for (Bullet bullet: bullets) {
            bullet.render(batch);
        }

        for (Explosion explosion: explosions) {
            explosion.render(batch);
        }

        for (Powerup powerup: powerups) {
            powerup.render(batch);
        }

        gigaGal.render(batch);
    }

    private void initializeDebugLevel() {

        gigaGal = new GigaGal(new Vector2(15, 40), this);

        platforms = new Array<Platform>();
        enemies = new DelayedRemovalArray<Enemy>();

        platforms.add(new Platform(15, 100, 30, 20));

        Platform enemyPlatform = new Platform(75, 90, 100, 65);
        enemies.add(new Enemy(enemyPlatform));

        platforms.add(enemyPlatform);
        platforms.add(new Platform(35, 55, 50, 20));
        platforms.add(new Platform(10, 20, 20, 9));

        bullets = new DelayedRemovalArray<Bullet>();
        explosions = new DelayedRemovalArray<Explosion>();
        powerups = new DelayedRemovalArray<Powerup>();

        powerups.add(new Powerup(new Vector2(20, 100)));
    }

    public void addPlatform(Platform platform)
    {
        this.platforms.add(platform);
    }

    public void addEnemy(Enemy enemy)
    {
        this.enemies.add(enemy);
    }

    public Array<Platform> getPlatforms() {
        return platforms;
    }

    public DelayedRemovalArray<Enemy> getEnemies() {
        return enemies;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public GigaGal getGigaGal() {
        return gigaGal;
    }

    public void setGigaGal(GigaGal gigaGal) {
        this.gigaGal = gigaGal;
    }


    public void setPlatforms(Array<Platform> platforms) {
        this.platforms = platforms;
    }

    public void setEnemies(DelayedRemovalArray<Enemy> enemies) {
        this.enemies = enemies;
    }

    public DelayedRemovalArray<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(DelayedRemovalArray<Bullet> bullets) {
        this.bullets = bullets;
    }

    public DelayedRemovalArray<Explosion> getExplosions() {
        return explosions;
    }

    public void setExplosions(DelayedRemovalArray<Explosion> explosions) {
        this.explosions = explosions;
    }

    public DelayedRemovalArray<Powerup> getPowerups() {
        return powerups;
    }

    public void setPowerups(DelayedRemovalArray<Powerup> powerups) {
        this.powerups = powerups;
    }
}
