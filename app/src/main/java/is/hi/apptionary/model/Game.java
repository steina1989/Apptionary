package is.hi.apptionary.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by steina on 8.3.2018.
 * Simple game object that is the top hierarchical object on the Firebase Server
 *
 */

public class Game {
    private String id;
    private String currentWord;
    private Map<String, Player> players = new HashMap();
    private ImagePoint imagePoint;
    // Time to draw in seconds
    private long endTime;

    public Game(){};

    // Special functions
    public void startGameClock(int gameDuration){
        this.endTime = System.currentTimeMillis() + gameDuration*1000;
    }
    public boolean addPlayer(String name, Player player) {
        if (this.players.containsKey(name)){
            return false;
        }
        else {
            this.players.put(name, player);
            return true;
        }
    }
    public void removePlayer(String name) {
        this.players.remove(name);
    }
    public boolean gameIsOver(){
        return System.currentTimeMillis() > this.endTime;
    }


    // Getters / setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrentWord() {
        return currentWord;
    }

    public void setCurrentWord(String currentWord) {
        this.currentWord = currentWord;
    }

    public ImagePoint getImagePoint() {
        return imagePoint;
    }

    public void setImagePoint(ImagePoint imagePoint) {
        this.imagePoint = imagePoint;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }
    public void setPlayers(Map<String, Player> players) {
        this.players = players;
    }
}
