package is.hi.apptionary.vidmot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import is.hi.apptionary.R;

/**
 * Created by Lenovo on 21.2.2018.
 */

public class FrontpageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontpage);

        Button letsGoBtn = (Button) findViewById(R.id.letsGoBtn);
        letsGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), NicknameActivity.class);
                startActivity(startIntent);
            }
        });

        Button creditsBtn = (Button) findViewById(R.id.creditsBtn);
        creditsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), CreditsActivity.class);
                startActivity(startIntent);
            }
        });
    }
}