package is.hi.apptionary.Vidmot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import is.hi.apptionary.R;

public class GamePickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_picker);

        Button createGameBtn = (Button) findViewById(R.id.creategameBtn);
        createGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), PlayerListCreatorActivity.class);
                startActivity(startIntent);
            }
        });

        Button joinGameBtn = (Button) findViewById(R.id.joingameBtn);
        joinGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), PlayerListJoinerActivity.class);
                startActivity(startIntent);
            }

        });
    }
}
