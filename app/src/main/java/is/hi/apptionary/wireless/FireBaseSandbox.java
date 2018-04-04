package is.hi.apptionary.wireless;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import is.hi.apptionary.R;
import is.hi.apptionary.model.Game;
import is.hi.apptionary.model.ImagePoint;
import is.hi.apptionary.model.Player;

/**
 * Created by steina on 11.3.2018.
 * Demonstrating basic functionality of a firebase db.
 * Should not go into production.
 */

public class FireBaseSandbox extends AppCompatActivity {

    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_base_sandbox);

        // Fetch an instance of our custom database, and since we don't use the database itself,
        // get a reference to it. We can push data onto database references.
        // It seems getInstance only works within a class that extends activity
        final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Games");

        // push a new unique entry to the root, and get a reference to it.
        DatabaseReference newUniqueKey = dbref.push();

        Game g = new Game();
        g.setId("leikur");
        g.setCurrentWord("Api");
        g.setImagePoint(new ImagePoint(201,212));

        Player p = new Player("Gunnar");
        p.setDrawer(true);

        Player p2 = new Player("Max");
        p2.setPoints(30);

        g.addPlayer("Gunnar",p);
        g.addPlayer("Max", p2);

        this.game = g;
        // Pushes the game object to the new entry.
        newUniqueKey.setValue(g);

        // We can listen to changes to our newly pushed game object.
        newUniqueKey.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snap) {
                game = snap.getValue(Game.class);
                String svar = "Game er ekki null";
                if (game==null){
                    svar = "Game er null";
                }
                Log.d("Fbsandbox", svar);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }
}
