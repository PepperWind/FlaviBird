package fr.flavi1.flap.android;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Flavien on 03/04/2015.
 */
public class ObstacleManager {

    public static Random random;

    public static float pulse =.003f;

    public static float obstacleSpeed = 250;
    public static float hole_width = 150;
    private static long appearingPace = 1400;
    private static long lastAppearingTime;
    private static long beginTime;

    public static ArrayList<Obstacle> obstacles;

    private static float hardCoeff;

    public static void load() {
        random = new Random();
        lastAppearingTime = TimeUtils.millis()-1000;

        obstacles = new ArrayList<Obstacle>();
        hardCoeff = 1.f;

        beginTime = TimeUtils.millis();
    }

    public static void update(float delta) {
        if(TimeUtils.millis() >= appearingPace + lastAppearingTime) {
            lastAppearingTime = TimeUtils.millis();
            spawnObstacle();
        }

        for (int i = 0; i < obstacles.size(); i++) {
            obstacles.get(i).update(delta, beginTime);
        }
    }

    public static void drawObstacles(Batch batch) {
        for (int i = 0; i < obstacles.size(); i++) {
            obstacles.get(i).draw(batch);
        }
    }

    public static void spawnObstacle() {
        obstacles.add(new Obstacle(TextureManager.wallLeftTexture.getWidth()+random.nextInt((int)(480-TextureManager.wallRightTexture.getWidth()- ObstacleManager.hole_width-70)), 800, pulse, random.nextFloat()%6.28f));
    }

    public static void reset() {
        obstacles.clear();
    }
}
