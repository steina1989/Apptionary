package is.hi.apptionary.vidmot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    private ArrayList<Object> players = new ArrayList<Object>();
    private ListView playerList;
    private String gamePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        //dbRef = new Firebase("https://apptionary-8abf1.firebaseio.com/Games/players");

        gamePath = this.getIntent().getStringExtra("gamePath");
        dbRef = FirebaseDatabase.getInstance().getReference().child("games").child(gamePath).child("players");
        playerList = (ListView) findViewById(R.id.Scoreboard);
        final ArrayAdapter<Object> arrayAdapterPlayer = new ArrayAdapter<Object>(this, android.R.layout.simple_list_item_1, players);
        playerList.setAdapter(arrayAdapterPlayer);
        Log.d("ble", "ble" + dbRef.toString());
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Object name = dataSnapshot.child("name").getValue(Object.class);
                    Object points = dataSnapshot.child("points").getValue(Object.class);
                    players.add(name + "          " + points);
                    //String name = (String) dataSnapshot.child("name").getValue();
                    //String points = (String) dataSnapshot.child("points").getValue();
                    //players.add(name + "        " + points);
            }
                arrayAdapterPlayer.notifyDataSetChanged();

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
}
