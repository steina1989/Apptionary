package is.hi.apptionary.vidmot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import is.hi.apptionary.R;
import is.hi.apptionary.vinnsla.GameIdGenerator;

public class GamePickerActivity extends AppCompatActivity {
boolean creating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_picker);
        creating=this.getIntent().getBooleanExtra("creating",false);
        if(creating){
          hideButtons(true);
        }



        Button createGameBtn = (Button) findViewById(R.id.creategameBtn);
        createGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), TeikniActivity.class);
                startIntent.putExtra("drawMode",true);
                startActivity(startIntent);
            }
        });

        Button joinGameBtn = (Button) findViewById(R.id.joingameBtn);
        joinGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), TeikniActivity.class);
                startIntent.putExtra("drawMode",false);
                startActivity(startIntent);
            }

        });
    }

    /**
     * Felur hnappa eftir því hvort verið sé að búa til leik eða joina
     * @param creating
     */
    private void hideButtons(boolean creating) {
        Button btn;
        if(creating){
             btn = (Button) findViewById(R.id.joingameBtn);
             TextView gameID = findViewById(R.id.game_id_text);
             gameID.setText(GameIdGenerator.getId());

        }else{
             btn = (Button) findViewById(R.id.creategameBtn);
        }
        btn.setVisibility(View.GONE);
    }


}
