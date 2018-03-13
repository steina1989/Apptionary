package is.hi.apptionary.model;

/**
 * Created by steina on 8.3.2018.
 */

public class Player {
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
}
