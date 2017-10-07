package fr.flavi1.flap.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Flavien on 03/04/2015.
 */
public class TextureManager {

    public static BitmapFont scoreFont;

    public static Texture backTexure;
    public static Texture menuOverScreen;
    public static Texture menuShadow;
    public static Texture wallLeftTexture;
    public static Texture wallRightTexture;
    public static Texture tuyauLeftTexture;
    public static Texture tuyauRightTexture;
    public static Texture panelTexture;
    public static Texture shareTexture;

    public static Texture playerSpriteSheet;
    public static TextureRegion[] playerFrames;
    public static Animation playerAnimation;
    public static Texture playerSteadyTexture;

    public static void load() {
        scoreFont = new BitmapFont(Gdx.files.internal("font2.fnt"),false);

        backTexure = new Texture(Gdx.files.internal("back.png"));
        menuOverScreen = new Texture(Gdx.files.internal("menu_overscreen.png"));
        menuShadow = new Texture(Gdx.files.internal("menu_shadow.png"));
        wallLeftTexture = new Texture(Gdx.files.internal("wall_left.png"));
        wallRightTexture = new Texture(Gdx.files.internal("wall_right.png"));
        tuyauLeftTexture = new Texture(Gdx.files.internal("tuyau_left.png"));
        tuyauRightTexture = new Texture(Gdx.files.internal("tuyau_right.png"));
        panelTexture = new Texture(Gdx.files.internal("panel.png"));
        shareTexture = new Texture(Gdx.files.internal("share.png"));

        playerSpriteSheet = new Texture(Gdx.files.internal("player_spritesheet.png"));
        TextureRegion[][] playerTempFrames = TextureRegion.split(playerSpriteSheet, 37, 26);
        playerFrames = new TextureRegion[2];
        int id = 0;
        for (int x = 0; x < 2; x++) {
            playerFrames[id++] = playerTempFrames[0][x];
        }
        playerAnimation = new Animation(.15f, playerFrames);
        playerAnimation.setPlayMode(Animation.PlayMode.LOOP);
        playerSteadyTexture = new Texture(Gdx.files.internal("player_steady.png"));
    }
}
