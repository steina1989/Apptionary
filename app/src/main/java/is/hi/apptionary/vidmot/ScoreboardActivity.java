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



import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import is.hi.apptionary.R;
import is.hi.apptionary.model.Player;

public class ScoreboardActivity extends AppCompatActivity {

    private DatabaseReference dbRef;
    private ArrayList<Player> players = new ArrayList<Player>();
    private ListView playerList;
    private String gamePath;
    private ArrayAdapter<Player> arrayAdapterPlayers;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        dbRef = new Firebase("https://apptionary-8abf1.firebaseio.com/Games/players");

        gamePath = this.getIntent().getStringExtra("gamePath");
        dbRef = FirebaseDatabase.getInstance().getReference().child("games").child(gamePath).child("players");
        playerList = (ListView) findViewById(R.id.Scoreboard);
        populateList();


        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Player player = d.getValue(Player.class);
                    players.add(player);
            }
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
                Intent startIntent = new Intent(getApplicationContext(), TeikniActivity.class);
                startActivity(startIntent);
            }
        });
    }


    private void populateList(){

        arrayAdapterPlayers = new ArrayAdapter<Player>(this, android.R.layout.simple_list_item_1, players);
        playerList.setAdapter(arrayAdapterPlayers);
        playerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = playerList.getItemAtPosition(position);
                Player chosenPlayer = (Player)o;
                System.out.println(chosenPlayer);


            }
        });

    }
}
