package is.hi.apptionary.Vidmot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import is.hi.apptionary.R;

public class TeikniActivity extends AppCompatActivity {
    PaintView canvas;
    Button undoButton, redButton, blueButton, greenButton, orangeButton, purpleButton, blackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teikni);
        initializePalette();
        canvas = (PaintView) findViewById(R.id.paintingCanvas);
        initializeListeners();
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

                canvas.undo();

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
