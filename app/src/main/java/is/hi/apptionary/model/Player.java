package is.hi.apptionary.model;

import android.support.annotation.NonNull;

/**
 * Created by steina on 8.3.2018.
 */

public class Player implements Comparable<Player> {
    private String name;
    private int points;
    private boolean isDrawer;

    // Needed for firebase
    public Player() {
    }

    ;

    public Player(String name) {
        this.name = name;
        this.points = 0;
        this.isDrawer = false;
    }

    public String toString() {

        return this.name + "   " + this.points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isDrawer() {
        return isDrawer;
    }

    public void setDrawer(boolean drawer) {
        isDrawer = drawer;
    }


    @Override
    public int compareTo(@NonNull Player o) {
        return o.getPoints() - this.points;
    }
}
