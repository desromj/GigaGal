package com.udacity.gamedev.gigagal.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.udacity.gamedev.gigagal.entities.GigaGal;

/**
 * Created by Quiv on 2016-01-18.
 */
public class ChaseCam
{
    Camera camera;
    CameraMode mode;
    GigaGal target;

    public ChaseCam(Camera camera, GigaGal gigaGal)
    {
        this.camera = camera;
        this.mode = CameraMode.CHASE;
        this.target = gigaGal;
    }

    // TODO: Set the camera's position to GigaGal's position
    // Note that the camera's position is a Vector3, while GigaGal's position is a Vector2
    public void update(float delta)
    {
        switch (mode) {
            case CHASE:
                trackTarget();
                break;

            case DEBUG:
                if (Gdx.input.isKeyPressed(Input.Keys.A))
                    this.camera.position.x -= Constants.DEBUG_CAMERA_MOVE_SPEED * delta;
                if (Gdx.input.isKeyPressed(Input.Keys.D))
                    this.camera.position.x += Constants.DEBUG_CAMERA_MOVE_SPEED * delta;
                if (Gdx.input.isKeyPressed(Input.Keys.W))
                    this.camera.position.y += Constants.DEBUG_CAMERA_MOVE_SPEED * delta;
                if (Gdx.input.isKeyPressed(Input.Keys.S))
                    this.camera.position.y -= Constants.DEBUG_CAMERA_MOVE_SPEED * delta;
        }
    }

    public void toggleCameraMode()
    {
        if (mode == CameraMode.CHASE)
        {
            mode = CameraMode.DEBUG;
        }
        else if (mode == CameraMode.DEBUG)
        {
            mode = CameraMode.CHASE;
            trackTarget();
        }
    }

    private void trackTarget()
    {
        this.camera.position.x = target.getPosition().x;
        this.camera.position.y = target.getPosition().y;
    }

    public enum CameraMode
    {
        CHASE,
        DEBUG
    }
}
