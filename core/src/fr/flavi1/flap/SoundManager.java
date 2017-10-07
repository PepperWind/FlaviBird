package fr.flavi1.flap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Flavien on 05/04/2015.
 */
public class SoundManager {

    public static Sound fallSound;
    public static Sound hitSound;
    public static Sound passSound;
    public static Sound wingSound;

    public static void load() {
        fallSound = Gdx.audio.newSound(Gdx.files.internal("fall_sound.ogg"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("hit_sound.ogg"));
        passSound = Gdx.audio.newSound(Gdx.files.internal("pass_sound.ogg"));
        wingSound = Gdx.audio.newSound(Gdx.files.internal("wing_sound.ogg"));
    }
}
