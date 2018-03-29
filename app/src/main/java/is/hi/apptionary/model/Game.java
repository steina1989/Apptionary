package is.hi.apptionary.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by steina on 8.3.2018.
 * Simple game object that is the top hierarchical object on the Firebase Server
 *
 */

public class Game {
    private String id;
    private boolean gameOver;
    private String currentWord;
    private List<Player> players = new LinkedList<>();
    private ImagePoint imagePoint;
    // Time to draw in seconds
    private long endTime;

    public Game(){};

    public void addPlayer(Player player){
        this.players.add(player);
    }

    // Getters / setters
    public String getId() {
        return id;
    }

    public boolean isGameOver() {return gameOver;}

    public void setGameOver(boolean gameOver) {this.gameOver = gameOver;}

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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
