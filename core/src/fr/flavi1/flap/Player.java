package fr.flavi1.flap;

/**
 * Created by Flavien on 03/04/2015.
 */
public class Player {

    private float speed;
    private float speedY;
    private float deadRotationSpeed;
    private float rotation;
    private float deadFallAcceleration;

    private float x;
    private float y;
    private boolean dead;
    private boolean fallen;

    private boolean betweenObstacles;

    private boolean flipped;

    public Player() {
        speedY = 0;
        speed = 0;
        deadRotationSpeed = 50;
        deadFallAcceleration = -1000;
        rotation = 0;

        x = 203;
        y = 148;

        fallen = false;
        flipped = false;
        dead = false;
        betweenObstacles = false;
    }

    public void reset() {
        x = 203;
        y = 148;
        speed = 0;
        speedY = 0;
        rotation = 0;
        dead = false;
        betweenObstacles = false;
        fallen = false;

        TextureManager.playerAnimation.getKeyFrames()[0].flip(TextureManager.playerAnimation.getKeyFrames()[0].isFlipX(), false);
        TextureManager.playerAnimation.getKeyFrames()[1].flip(TextureManager.playerAnimation.getKeyFrames()[1].isFlipX(), false);
        flipped = false;

    }

    public void start() {
        speed = 300;
        flipped = false;

        TextureManager.playerAnimation.getKeyFrames()[0].flip(TextureManager.playerAnimation.getKeyFrames()[0].isFlipX(), false);
        TextureManager.playerAnimation.getKeyFrames()[1].flip(TextureManager.playerAnimation.getKeyFrames()[1].isFlipX(), false);
    }

    public void flip() {
        SoundManager.wingSound.play();

        if(speed > 0) speed = -speed-5;
        else speed = -speed+5;
        TextureManager.playerAnimation.getKeyFrames()[0].flip(true, false);
        TextureManager.playerAnimation.getKeyFrames()[1].flip(true, false);

        flipped = !flipped;
    }

    public void update(float delta, ScoreManager scoreManager) {
        if(Game.state == Game.State.BIRDFALL) {
            speedY += deadFallAcceleration*delta;
            if(flipped)
                rotation -= deadRotationSpeed*delta;
            else
                rotation += deadRotationSpeed*delta;
            y += speedY*delta;

            if(y < -TextureManager.playerSteadyTexture.getWidth()*2)
                fallen = true;
            return;
        }


        x += speed*delta;
        if(x <= 72 || x>371 || CollisionManager.isCollisionWithObstacles(this, ObstacleManager.obstacles))
            die();

        if(CollisionManager.isPlayerBetweenObstacles(this, ObstacleManager.obstacles)) {
            betweenObstacles = true;
        }
        else {
            if(betweenObstacles) {
                betweenObstacles = false;
                scoreManager.incrementScore();
            }
        }
    }

    public FRect getRectangle() {
        return new FRect(x, y, TextureManager.playerSteadyTexture.getWidth(), TextureManager.playerSteadyTexture.getHeight());
    }

    public void die() {
        SoundManager.hitSound.play();
        dead = true;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public float getRotation() {
        return rotation;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isFallen() {
        return fallen;
    }
}
