package fr.flavi1.flap.android;

import java.util.ArrayList;

/**
 * Created by Flavien on 03/04/2015.
 */
public class CollisionManager {

    public static boolean isInRect(float x, float y, FRect r) {
        return x <= r.getX()+r.getWidth()
                && x >= r.getX()
                && y <= r.getY()+r.getHeight()
                && y >= r.getY();
    }

    public static boolean isCollision(FRect r1, FRect r2) {
        return !(r1.getX()+r1.getWidth() < r2.getX()
                    || r2.getX()+r2.getWidth() < r1.getX()
                    || r1.getY()+r1.getHeight() < r2.getY()
                    || r2.getY()+r2.getHeight() < r1.getY());
    }

    public static boolean isVerticalCollision(FRect r1, FRect r2) {
        return !(r1.getY()+r1.getHeight() < r2.getY()
                || r2.getY()+r2.getHeight() < r1.getY());
    }

    public static boolean isCollisionWithObstacles(Player player, ArrayList<Obstacle> obstacles) {
        for(int i = 0; i < obstacles.size(); i++) {
            if(isCollision(player.getRectangle(), obstacles.get(i).getLeftRectangle()))
                return true;
            if(isCollision(player.getRectangle(), obstacles.get(i).getRightRectangle()))
                return true;
        }
        return false;
    }

    public static boolean isPlayerBetweenObstacles(Player player, ArrayList<Obstacle> obstacles) {
        for(int i = 0; i < obstacles.size(); i++) {
            if(isVerticalCollision(player.getRectangle(), obstacles.get(i).getLeftRectangle()))
                return true;
        }
        return false;
    }
}
