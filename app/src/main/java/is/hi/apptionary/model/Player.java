package is.hi.apptionary.model;

import android.support.annotation.NonNull;

/**
 * Created by steina on 8.3.2018.
 */

public class Player implements Comparable{
    private String name;
    private int points;
    private boolean isDrawer;

    // Needed for firebase
    public Player(){};

    public Player(String name) {
        this.name = name;
        this.points = 0;
        this.isDrawer = false;
    }

    public String toString() {
        String nafn = "Name: ";
        String pointss = "Points: ";
        return nafn + this.name + pointss + this.points;
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
    public int compareTo(@NonNull Object o) {
        Player otherPlayer = (Player) o;
        return this.points-otherPlayer.getPoints();
    }
}
