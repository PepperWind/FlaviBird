package fr.flavi1.flap.android;

/**
 * Created by Flavien on 05/04/2015.
 */
public class FRect {

    private float x, y, width, height;

    public FRect(float x0, float y0, float width0, float height0) {
        x = x0;
        y = y0;
        width = width0;
        height = height0;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
