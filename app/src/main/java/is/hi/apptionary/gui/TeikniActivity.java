package is.hi.apptionary.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import is.hi.apptionary.R;
import is.hi.apptionary.model.ImagePoint;

public class TeikniActivity extends AppCompatActivity {
    PaintView canvas;
    Button undoButton, redButton, blueButton, greenButton, orangeButton, purpleButton, blackButton;
    private Button[] buttons;

    boolean isDrawing=false; //Default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teikni);
        canvas = (PaintView) findViewById(R.id.paintingCanvas);
        initializeCanvas();
    }

    /***
     * Upphafsstillir canvas og önnur object.
     * Bætir við listeners á pallettu hnappa, en
     * felur hnappanna ef isDrawing er ósatt.
     */
    private void initializeCanvas() {
        setDrawing(false);
        initializePaletteButtons();
        if(isDrawing){
            initializeListeners();
            canvas.setDrawMode(true);
        }else{
            for(Button b : buttons){
                b.setVisibility(View.GONE);
            }
            canvas.setDrawMode(false);
        testCanvas();

        }


    }
    private void testCanvas(){
        ArrayList<ImagePoint> p= new ArrayList();
        for(float i = 0;i<20;i++){
            p.add(new ImagePoint(i,i));
            if(i==0){
                p.get(0).setActionDown(true);

            }else if(i==19) {
                p.get(18).setActionUp(true);
            }else{
                p.get((int)i).setActionMove(true);
            }

        }

        for(ImagePoint poing : p){
            canvas.drawPoint(poing);
        }
    }
    private void initializePaletteButtons() {
        undoButton = (Button) findViewById(R.id.undoButton);
        redButton = (Button) findViewById(R.id.redButton);
        blueButton = (Button) findViewById(R.id.blueButton);
        greenButton = (Button) findViewById(R.id.greenButton);
        orangeButton = (Button) findViewById(R.id.orangeButton);
        purpleButton = (Button) findViewById(R.id.purpleButton);
        blackButton = (Button) findViewById(R.id.blackButton);
        buttons = new Button[]{ undoButton, redButton, blueButton, greenButton, orangeButton, purpleButton, blackButton};


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
        for(Button b: buttons){
            b.setOnClickListener(paletteButtonListener);
        }



    }


    public boolean isDrawing() {
        return isDrawing;
    }

    public void setDrawing(boolean drawing) {
        isDrawing = drawing;
        canvas.setDrawMode(drawing);
    }


}
