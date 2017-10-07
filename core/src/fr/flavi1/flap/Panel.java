package fr.flavi1.flap;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Flavien on 11/04/2015.
 */
public class Panel {

    private static final float x = 115;
    private static float y = 0;

    public static void reset() {
        y = 0;
    }

    public static boolean isSteady() {
        return Math.abs(y-350) <= 50;
    }

    public static void update(float delta) {
        if(Math.abs(y-350) > 50)
            y += delta*.0003*Math.pow((350 - y), 3);
    }

    public static FRect getLeaderboardsRect() {
        return new FRect(x+85, y+132, TextureManager.shareTexture.getWidth(), TextureManager.shareTexture.getHeight());
    }

    public static void draw(Batch batch, ScoreManager scoreManager) {
        batch.draw(TextureManager.panelTexture, x, y);
        batch.draw(TextureManager.shareTexture, x+85, y+132);
        TextureManager.scoreFont.draw(batch, Integer.toString(scoreManager.getScore()), x+65, y+50);
        TextureManager.scoreFont.draw(batch, Integer.toString(scoreManager.readBestScore()), x+190, y+50);
    }
}
