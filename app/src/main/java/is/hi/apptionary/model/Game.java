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
    private Map<String,Player> players = new HashMap<>();
    private ImagePoint imagePoint;

    public Game(){};

    public void addPlayer(String playerName, Player player){
        this.players.put(playerName, player);
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

    public Map<String,Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String,  Player> players) {
        this.players = players;
    }
}
