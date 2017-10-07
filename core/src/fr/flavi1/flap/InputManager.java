package fr.flavi1.flap;

import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by Flavien on 03/04/2015.
 */
public class InputManager implements InputProcessor {

    public int[] coord = {0, 0};

    private boolean touchToRead;

    public InputManager() {
        touchToRead = false;

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        coord[0] = screenX;
        coord[1] = screenY;
        Log.d("x: ", ""+screenX);
        Log.d("y: ", ""+screenY);
        touchToRead = true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public boolean touched() {
        if(!touchToRead)
            return false;
        touchToRead = false;
        return true;
    }
}
