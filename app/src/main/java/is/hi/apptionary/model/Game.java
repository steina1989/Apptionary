package is.hi.apptionary.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by steina on 8.3.2018.
 */

public class Game {
    private String id;
    private String currentWord;
    private List<Player> players = new ArrayList<Player>();
    private ImagePoint imagePoint;
    // Time to draw in seconds
    private int gameDuration;

    public HashMap<String,Game> getGameObject() {
        // láta undirklasa gera hasmap af sér
        //láta game gera hashmap af sér
        // Skila hashpammi

        return new HashMap<String,Game>();
    };


    public int getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(int gameDuration) {
        this.gameDuration = gameDuration;
    }

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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public ImagePoint getImagePoint() {
        return imagePoint;
    }

    public void setImagePoint(ImagePoint imagePoint) {
        this.imagePoint = imagePoint;
    }


}
