package is.hi.apptionary.vidmot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import is.hi.apptionary.R;

public class PlayerListCreatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list_creator);

        Button startGameBtn = (Button) findViewById(R.id.startgameBtn);
        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), TeikniActivity.class);
                startActivity(startIntent);

}
    });
}
}
