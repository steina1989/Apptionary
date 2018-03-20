package is.hi.apptionary.vidmot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import is.hi.apptionary.R;
import is.hi.apptionary.model.Game;
import is.hi.apptionary.vinnsla.GameIdGenerator;
import is.hi.apptionary.vinnsla.SimpleKeyValuePair;

public class GamePickerActivity extends AppCompatActivity {
    boolean creating;
    TextView gameName;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_picker);
        gameName = findViewById(R.id.game_id_text);
        creating = this.getIntent().getBooleanExtra("creating", false);
        if (creating) {
            hideButtons(true);
        }


        Button createGameBtn = (Button) findViewById(R.id.creategameBtn);
        createGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), TeikniActivity.class);
                startIntent.putExtra("drawMode", true);
                ///Hér þarf að búa til nýjan leik í firebase út frá ID
                String gameId = (String) gameName.getText();
                startActivity(startIntent);
            }
        });

        Button joinGameBtn = (Button) findViewById(R.id.joingameBtn);
        joinGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                final String gameIdKey = "leikur";
                dbRef = FirebaseDatabase.getInstance().getReference("gameKeys");
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, String> hm = (Map) dataSnapshot.getValue();
                        String gameIdValue = hm.get(gameIdKey);

                        Log.d("firebase debug", gameIdValue);
                        Intent startIntent = new Intent(getApplicationContext(), TeikniActivity.class);
                        startIntent.putExtra("drawMode", false);
                        startIntent.putExtra("gameId", gameIdValue);
                        startActivity(startIntent);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

            }

        });
    }

    /**
     * Felur hnappa eftir því hvort verið sé að búa til leik eða joina
     *
     * @param creating
     */
    private void hideButtons(boolean creating) {
        Button btn;
        if (creating) {
            btn = (Button) findViewById(R.id.joingameBtn);
            TextView gameID = findViewById(R.id.game_id_text);
            gameID.setText(GameIdGenerator.getId());

        } else {
            btn = (Button) findViewById(R.id.creategameBtn);
        }
        btn.setVisibility(View.GONE);
    }


}
