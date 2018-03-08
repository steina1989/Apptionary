package is.hi.apptionary.model;

import android.graphics.Color;

/**
 * Created by steina on 8.3.2018.
 */

public class ImagePoint {
    private float x,y;
    private String colorString;
    private boolean actionUp;
    private boolean actionDown;
    private boolean actionMove;


    public ImagePoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public String getColorString() {
        return colorString;
    }

    public void setColorString(String colorString) {
        this.colorString = colorString;
    }

    public boolean isActionUp() {
        return actionUp;
    }

    public void setActionUp(boolean actionUp) {
        this.actionUp = actionUp;
    }

    public boolean isActionDown() {
        return actionDown;
    }

    public void setActionDown(boolean actionDown) {
        this.actionDown = actionDown;
    }

    public boolean isActionMove() {
        return actionMove;
    }

    public void setActionMove(boolean actionMove) {
        this.actionMove = actionMove;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
