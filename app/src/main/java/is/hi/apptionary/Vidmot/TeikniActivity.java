package is.hi.apptionary.Vidmot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import is.hi.apptionary.R;
import is.hi.apptionary.model.Game;
import is.hi.apptionary.model.ImagePoint;

public class TeikniActivity extends AppCompatActivity {
    PaintView canvas;
    Game currentGame;
    boolean drawMode = true;
    Button undoButton, redButton, blueButton, greenButton, orangeButton, purpleButton, blackButton;
    private DatabaseReference dbref, imagePointRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teikni);
        initializePalette();
        canvas = (PaintView) findViewById(R.id.paintingCanvas);
        canvas.setTeikniActivity(this);
        initializeListeners();

        dbref = FirebaseDatabase.getInstance().getReference("Games");
        final String id = "leikur";

        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    Game g = dsp.getValue(Game.class);
                    Log.d("firebaseDebug", g.getCurrentWord());
                    if (g.getId().equals(id)){
                        imagePointRef = dsp.getRef().child("imagePoint");
                        currentGame = g;
                        return;

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("firebaseDebug", databaseError.getMessage());


            }
        });

        if (!drawMode) {
            imagePointRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snap) {
                    ImagePoint ip = snap.getValue(ImagePoint.class);
                    if (ip == null) {
                        Log.d("firebaseDebug", "ip is null");
                    }
                    else{
                        currentGame.setImagePoint(ip);
                        canvas.drawPoint(currentGame.getImagePoint());
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

    /**
     * Talar við Firebase og uppfærir current point
     *
     * @param touched
     */
    public void broadcastImagePoint(ImagePoint touched) {
        imagePointRef.setValue(touched);

    }


    private void initializePalette() {
        undoButton = (Button) findViewById(R.id.undoButton);
        redButton = (Button) findViewById(R.id.redButton);
        blueButton = (Button) findViewById(R.id.blueButton);
        greenButton = (Button) findViewById(R.id.greenButton);
        orangeButton = (Button) findViewById(R.id.orangeButton);
        purpleButton = (Button) findViewById(R.id.purpleButton);
        blackButton = (Button) findViewById(R.id.blackButton);


    }

    private View.OnClickListener paletteButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Button button = (Button) v;
            String colorCode = (String) v.getTag();
            canvas.setColor(colorCode);
        }
    };

    private void initializeListeners() {
        undoButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                drawMode = !drawMode;

            }
        });
        redButton.setOnClickListener(paletteButtonListener);
        blueButton.setOnClickListener(paletteButtonListener);
        greenButton.setOnClickListener(paletteButtonListener);
        orangeButton.setOnClickListener(paletteButtonListener);
        purpleButton.setOnClickListener(paletteButtonListener);
        blackButton.setOnClickListener(paletteButtonListener);


    }


}
