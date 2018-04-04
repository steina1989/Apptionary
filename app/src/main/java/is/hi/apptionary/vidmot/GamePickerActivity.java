package is.hi.apptionary.vidmot;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import is.hi.apptionary.R;
import is.hi.apptionary.model.Game;
import is.hi.apptionary.model.Player;
import is.hi.apptionary.vinnsla.GameIdGenerator;

public class GamePickerActivity extends AppCompatActivity {
    boolean creating;
    TextView gameNameTextView,playerNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_picker);
        gameNameTextView = findViewById(R.id.game_id_text);
        playerNameTextView=findViewById(R.id.playerNickname);
        creating = this.getIntent().getBooleanExtra("creating", false);
        final Button proceedBtn = (Button) findViewById(R.id.proceedBtn);

        if (creating) {
            proceedBtn.setText("Create game");
        } else {
            proceedBtn.setText("Find game");
        }

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (creating) {
                    createGame();
                } else {
                    joinGame();
                }
            }

        });
    }
    /**
     * Creates a new game and pushes a key value pair to the gameKey index where key is the
     * human friendly identifier and value is the game firebase identifier.
     */
    private void createGame() {
        String gameName = gameNameTextView.getText().toString();
        String playerName = playerNameTextView.getText().toString();
        if(playerName.equals("")){
            makeToast("Please input your name.");
            return;
        }

        if(gameName.equals("")){
            makeToast("Please input a game name.");
            return;
        }
        Player player = new Player(playerName);
        player.setDrawer(true);

        Game game = new Game();
        game.setCurrentWord("Fetching...");
        game.addPlayer(playerName,player);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference newUniqueChild = dbRef.child("games").push();
        newUniqueChild.setValue(game);

        DatabaseReference gameKeysRef = dbRef.child("gameKeys").getRef();
        gameKeysRef.child(gameName).setValue(newUniqueChild.getKey());

        Intent startIntent = new Intent(getApplicationContext(), TeikniActivity.class);
        startIntent.putExtra("drawMode", true);
        startIntent.putExtra("gamePath", newUniqueChild.getKey());
        startIntent.putExtra("playerName", playerName);
        startActivity(startIntent);
    }

    /**
     * Tries to match human friendly key to a firebase unique identifier.
     */
    private void joinGame() {
        final String gameName = gameNameTextView.getText().toString();
        final String playerName = playerNameTextView.getText().toString();
        if(playerName.equals("")){
            makeToast("Please input your name.");
            return;
        }

        if(gameName.equals("")){
            makeToast("Please input a game name.");
            return;
        }
        DatabaseReference gameKeyRef = FirebaseDatabase.getInstance().getReference("gameKeys");
        gameKeyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> hm = (Map) dataSnapshot.getValue();
                String gamePath = hm.get(gameName);
                if (gamePath == null) {
                    makeToast("I can't find that game.");
                } else {
                    DatabaseReference playerRef = FirebaseDatabase.getInstance().getReference("games");
                    playerRef.child(gamePath).child("players").child(playerName).setValue(new Player(playerName));
                    Intent startIntent = new Intent(getApplicationContext(), TeikniActivity.class);
                    startIntent.putExtra("drawMode", false);
                    startIntent.putExtra("gamePath", gamePath);
                    startIntent.putExtra("playerName", playerName);
                    startActivity(startIntent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void makeToast(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
        toast.show();
    }

}
