package fr.flavi1.flap;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by Flavien on 03/04/2015.
 */
public class Obstacle {
    public float holeMainX;
    public float holeX;
    public float holeY;
    public float pulse;
    public float phi;

    public float amplitude;

    public Obstacle(float x, float y, float pulsation, float deltaphi) {
        holeMainX = x;
        holeY = y;
        pulse = pulsation;
        phi = deltaphi;
        System.out.println(phi);
        amplitude = Math.min(x- TextureManager.wallLeftTexture.getWidth(), 480- TextureManager.wallRightTexture.getWidth()- ObstacleManager.hole_width-x);
    }

    public void update(float delta, long beginTime) {
        holeX =(float) (holeMainX + amplitude*Math.cos(pulse* (TimeUtils.millis()-beginTime))+phi);

        holeY -= ObstacleManager.obstacleSpeed*delta;

        if(holeY < -TextureManager.tuyauLeftTexture.getHeight()) {
            ObstacleManager.obstacles.remove(this);
        }
    }

    public void draw(Batch batch) {
        batch.draw(TextureManager.tuyauLeftTexture, holeX- TextureManager.tuyauLeftTexture.getWidth(), holeY);
        batch.draw(TextureManager.tuyauRightTexture, holeX+ ObstacleManager.hole_width, holeY);
    }

    public FRect getLeftRectangle() {
        return new FRect(holeX- TextureManager.tuyauLeftTexture.getWidth(), holeY, TextureManager.tuyauLeftTexture.getWidth(), TextureManager.tuyauLeftTexture.getHeight());
    }

    public FRect getRightRectangle() {
        return new FRect(holeX+ ObstacleManager.hole_width, holeY, TextureManager.tuyauRightTexture.getWidth(), TextureManager.tuyauRightTexture.getHeight());
    }

}
