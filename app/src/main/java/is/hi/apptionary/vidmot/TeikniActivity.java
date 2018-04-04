package is.hi.apptionary.vidmot;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import is.hi.apptionary.R;
import is.hi.apptionary.model.Game;
import is.hi.apptionary.model.ImagePoint;
import is.hi.apptionary.model.Player;

public class TeikniActivity extends AppCompatActivity {
    PaintView canvas;

    Game currentGame;
    boolean drawMode;
    Button undoButton, redButton, blueButton, greenButton, orangeButton, purpleButton, blackButton, endRoundButton;
    Button[] buttons; //Hnapparnir í pallettunni
    private String gamePath;//Path á núverandi leik í database
    private DatabaseReference dbRef, imagePointRef, gameOverRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teikni);
        gamePath = this.getIntent().getStringExtra("gamePath");
        Log.d("onCreateTeikni", "gamePath is: " + gamePath);
        drawMode = this.getIntent().getExtras().getBoolean("drawMode");
        initializePalette();

        initializeListeners();
        final TextView currentWord = findViewById(R.id.textToGuess);
        dbRef = FirebaseDatabase.getInstance().getReference().child("games").child(gamePath);
        gameOverRef = dbRef.child("gameOver").getRef();
        setGameOverListener();

        endRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameOver();

                gameOverRef.setValue(true);
            }
        });


        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Game g = dataSnapshot.getValue(Game.class);
                Log.d("firebaseDebug", g.getCurrentWord());
                imagePointRef = dataSnapshot.getRef().child("imagePoint");
                currentGame = g;
                //Uppfæra orðið
                if (drawMode) {
                    currentWord.setText(g.getCurrentWord());
                } else {
                    currentWord.setText("");
                }
                setImagePointListener();
                return;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("firebaseDebug", databaseError.getMessage());


            }
        });


    }

    /**
     * Bregðumst við því þegar leik er lokið.
     */
    private void gameOver() {
        Intent startIntent = new Intent(getBaseContext(), ScoreboardActivity.class);
        startIntent.putExtra("gamePath", gamePath);
        startIntent.putExtra("drawMode", drawMode);
        String playerName = this.getIntent().getStringExtra("playerName");
        startIntent.putExtra("playerName",playerName);

        startActivity(startIntent);


    }


    private void setGameOverListener() {
        gameOverRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snap) {
                boolean gameOver = (boolean) snap.getValue();
                if (gameOver) {
                    gameOver();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void setImagePointListener() {
        if (!drawMode) {
            imagePointRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snap) {
                    ImagePoint ip = snap.getValue(ImagePoint.class);
                    if (ip == null) {
                        Log.d("firebaseDebug", "ip is null");
                    } else {
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
        endRoundButton = (Button) findViewById(R.id.end_round_button);
        undoButton = (Button) findViewById(R.id.undoButton);
        redButton = (Button) findViewById(R.id.redButton);
        blueButton = (Button) findViewById(R.id.blueButton);
        greenButton = (Button) findViewById(R.id.greenButton);
        orangeButton = (Button) findViewById(R.id.orangeButton);
        purpleButton = (Button) findViewById(R.id.purpleButton);
        blackButton = (Button) findViewById(R.id.blackButton);
        if (!drawMode) {

            Button[] buttons = new Button[]{endRoundButton, undoButton, redButton, blueButton, greenButton, orangeButton, purpleButton, blackButton};

            TextView randomOrd = findViewById(R.id.textToGuess);
            randomOrd.setVisibility(View.GONE);
            for (Button b : buttons) {
                b.setVisibility(View.GONE);
            }

        } else {
            getRandomWord();
        }

        canvas = (PaintView) findViewById(R.id.paintingCanvas);
        canvas.setTeikniActivity(this);
        canvas.setDrawMode(drawMode);


    }

    private View.OnClickListener paletteButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Button button = (Button) v;
            String colorCode = (String) v.getTag();
            canvas.setColor(colorCode);
        }
    };

    private void initializeListeners() {
        //Hreinsar canvas og sendir nýjan ImagePoint með allar touch breytur
        // stilltar sem false, sem hreinsar canvas hjá öllum
        undoButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                canvas.clear();
                ImagePoint ip = new ImagePoint();
                broadcastImagePoint(ip);
            }
        });
        redButton.setOnClickListener(paletteButtonListener);
        blueButton.setOnClickListener(paletteButtonListener);
        greenButton.setOnClickListener(paletteButtonListener);
        orangeButton.setOnClickListener(paletteButtonListener);
        purpleButton.setOnClickListener(paletteButtonListener);
        blackButton.setOnClickListener(paletteButtonListener);
        endRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentGame.setGameOver(true);
                dbRef.setValue(currentGame);

            }
        });

    }

    private void getRandomWord() {
        DatabaseReference wordRef = FirebaseDatabase.getInstance().getReference().child("words");
        wordRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> hm = (ArrayList) dataSnapshot.getValue();
                int randomIndex = (int) (Math.random() * hm.size());
                String word = hm.get(randomIndex);
                TextView randomOrd = findViewById(R.id.textToGuess);
                word = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
                randomOrd.setText(word);
                DatabaseReference currentWordRef = FirebaseDatabase.getInstance().getReference();
                currentWordRef.child("games").child(gamePath).child("currentWord").setValue(word);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
