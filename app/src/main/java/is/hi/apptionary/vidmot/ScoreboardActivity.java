package is.hi.apptionary.vidmot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import is.hi.apptionary.R;
import is.hi.apptionary.model.Player;

public class ScoreboardActivity extends AppCompatActivity {
    private DatabaseReference dbRef;
    private ArrayList<Player> players = new ArrayList<Player>();
    private ListView playerList;
    private String gamePath, playerName;
    private boolean drawMode;
    Player thisPlayer;
    private ArrayAdapter<Player> arrayAdapterPlayers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerName = this.getIntent().getStringExtra("playerName");
        drawMode = this.getIntent().getBooleanExtra("drawMode", false);
        setContentView(R.layout.activity_scoreboard);


        gamePath = this.getIntent().getStringExtra("gamePath");
        dbRef = FirebaseDatabase.getInstance().getReference().child("games").child(gamePath).child("players");
        playerList = (ListView) findViewById(R.id.Scoreboard);
        populateList();


        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            //Búum til raðaðan lista af player objects eftir stigafjölda
            public void onDataChange(DataSnapshot dataSnapshot) {
                players.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Player player = snapshot.getValue(Player.class);
                    if (player.getName().equals(playerName)) {
                        thisPlayer = player;
                    }
                    players.add(player);
                }
                Collections.sort(players);
                arrayAdapterPlayers.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Button continueBtn = (Button) findViewById(R.id.Continue);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("games").child(gamePath);
                ref.child("players").child(playerName).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Intent startIntent = new Intent(getBaseContext(), TeikniActivity.class);
                        Player p = dataSnapshot.getValue(Player.class);
                        startIntent.putExtra("drawMode", p.isDrawer());
                        startIntent.putExtra("gamePath", gamePath);
                        startIntent.putExtra("playerName", playerName);
                        ref.child("gameOver").setValue(false);
                        startActivity(startIntent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }


    private void populateList() {

        arrayAdapterPlayers = new ArrayAdapter<Player>(this, android.R.layout.simple_list_item_1, players);
        playerList.setAdapter(arrayAdapterPlayers);
        playerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = playerList.getItemAtPosition(position);
                Player chosenPlayer = (Player) o;
                if (chosenPlayer.getName().equals(thisPlayer.getName())) {
                    return;
                }
                chooseWinner(chosenPlayer);


            }
        });

    }

    private void chooseWinner(Player pl) {
        pl.setPoints(pl.getPoints() + 10);
        DatabaseReference playerRef = FirebaseDatabase.getInstance().getReference("games");
        pl.setDrawer(true);
        playerRef.child(gamePath).child("players").child(pl.getName()).setValue(pl);

        arrayAdapterPlayers.notifyDataSetChanged();
        TextView whoGuessed = findViewById(R.id.whoGuessedTextField);
        whoGuessed.setText("");
        findAndUpdateNextDrawer();

    }

    private void findAndUpdateNextDrawer() {
        if (drawMode) {
            DatabaseReference playerRef = FirebaseDatabase.getInstance().getReference("games");
            thisPlayer.setDrawer(false);
            playerRef.child(gamePath).child("players").child(playerName).setValue(thisPlayer);
        }

    }
}
