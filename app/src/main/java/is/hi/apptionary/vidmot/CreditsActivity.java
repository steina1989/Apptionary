package is.hi.apptionary.vidmot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import is.hi.apptionary.R;

/**
 * Created by Lenovo on 21.2.2018.
 */

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        Spanned sp = Html.fromHtml( getString(R.string.credits_text));
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(sp);
    }
}