package is.hi.apptionary.vidmot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import is.hi.apptionary.R;
import is.hi.apptionary.model.Player;

public class ScoreboardActivity extends AppCompatActivity {

    private Firebase dbRef;
    private ArrayList<Object> players = new ArrayList<Object>();
    private ListView playerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        Firebase.setAndroidContext(this);
        dbRef = new Firebase("https://apptionary-8abf1.firebaseio.com/Games/players");

        playerList = (ListView) findViewById(R.id.Scoreboard);
        final ArrayAdapter<Object> arrayAdapterPlayer = new ArrayAdapter<Object>(this, android.R.layout.simple_list_item_1, players);
        playerList.setAdapter(arrayAdapterPlayer);

        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Object name = dataSnapshot.child("name").getValue();
                Object points = dataSnapshot.child("points").getValue();
                String score = points.toString();
                players.add(name + "        " + score);
                arrayAdapterPlayer.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

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
