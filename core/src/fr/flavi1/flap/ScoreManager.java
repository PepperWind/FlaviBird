package fr.flavi1.flap;

import android.os.Looper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.Batch;
import fr.flavi1.flap.android.AndroidLauncher;

/**
 * Created by Flavien on 05/04/2015.
 */
public class ScoreManager{

    private  int score;
    private  long subScore;
    private  long akey = 86;
    private  long bkey = 211;

    public  Preferences prefs;
    private AndroidLauncher lManager;

    public ScoreManager(AndroidLauncher manager) {
        Looper.prepare();
        lManager = manager;
    }

    public  void load() {
        prefs = Gdx.app.getPreferences("score");
        score = 0;
        subScore = encrypt(score);
    }

    public  void reset() {
        score = 0;
        subScore = encrypt(score);
    }

    public  void displayScore(Batch batch) {
        if(subScore != encrypt(score)) {
            score = -1;
            subScore = encrypt(score);
        }

        TextureManager.scoreFont.draw(batch, Integer.toString(score), 230, 700);
    }

    public  long length(long n) {
        int i = 2;
        int l = 0;

        while (n > 1) {
            if (n%i == 0) {
                n /= i;
                l += 1;
            }
            else {
                i += 1;
            }
        }
        return l;
    }

    public  long encrypt(int n) {
        return n*(akey)+bkey;
    }

    public  long disencrypt(long n) {
        return (n - bkey)/(akey);
    }

    public  boolean corresponds(long n, long cryptedN, long cryptedLen) {
        return (n == disencrypt(cryptedN) && cryptedLen == length(cryptedN));
    }

    public  void registerBestScore(int score) {
        prefs.putInteger("bestScore", score);
        prefs.putLong("bestScore_secureKey", encrypt(score));
        prefs.putLong("jioFS5NJEF34JS", length(encrypt(score)));
        prefs.flush();
    }

    public  void registerLastScore(int score) {
        prefs.putInteger("lastScore", score);
        prefs.putLong("lastScore_secureKey", encrypt(score));
        prefs.putLong("UQH5GRsfUFfzef", length(encrypt(score)));
        prefs.flush();
    }

    public  int readLastScore() {
        int n = prefs.getInteger("lastScore", 0);
        long cryptedN = prefs.getLong("lastScore_secureKey");
        long cryptedLen = prefs.getLong("UQH5GRsfUFfzef");

        if(corresponds(n, cryptedN, cryptedLen)) return n;
        return 0;
    }

    public  int readBestScore() {
        int n = prefs.getInteger("bestScore", 0);
        long cryptedN = prefs.getLong("bestScore_secureKey");
        long cryptedLen = prefs.getLong("jioFS5NJEF34JS");

        if(corresponds(n, cryptedN, cryptedLen)) return n;
        return 0;
    }

    public  void shareBest() {
        lManager.submitScoreGPGS(readBestScore());
        lManager.getLeaderboardGPGS();
    }

    public  void incrementScore() {
        if(subScore != encrypt(score)) {
            score = 0;
            subScore = encrypt(score);
        }
        score++;
        subScore = encrypt(score);
        SoundManager.passSound.play();
    }

    public  int getScore() {
        if(subScore != encrypt(score)) {
            score = 0;
            subScore = encrypt(score);
        }

        return score;
    }
}
