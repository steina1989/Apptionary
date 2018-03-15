package is.hi.apptionary.vidmot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import is.hi.apptionary.model.ImagePoint;

/**
 * Created by notandi on 2/5/2018.
 */

public class PaintView extends View {
    TeikniActivity teikniActivity;
    private boolean drawMode=false;
    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;

    //initial color
    private int paintColor = 0xFF660000;

    //canvas
    private Canvas drawCanvas;

    public boolean isDrawMode() {
        return drawMode;
    }

    public void setDrawMode(boolean drawMode) {
        this.drawMode = drawMode;
    }
    public void setTeikniActivity(TeikniActivity teikn){
        teikniActivity=teikn;
    }
    //canvas bitmap
    private Bitmap canvasBitmap;
    boolean undoFlagged;
    boolean saveFlag = true;

    public PaintView(Context context) {
        super(context);
        init(context);
    }


    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);


    }

    public PaintView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setupPaintField();

    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    private void setupPaintField() {
        drawPath = new Path();
        drawPaint = new Paint();

        undoFlagged = false;

        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(20);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    /***
     * Teiknar á canvas punktinn og teiknar í kjölfarið
     * leiðina á milli nýja punktsins og síðasta punkts sem fékkst.
     * @param ip
     */
    public void drawPoint(ImagePoint ip) {
        setColor(ip.getColor());
        float touchX = ip.getX()*drawCanvas.getWidth();
        float touchY = ip.getY()*drawCanvas.getHeight();
        if(ip.isActionDown()){
            drawPath.moveTo(touchX, touchY);
        }else if(ip.isActionMove()){
            drawPath.lineTo(touchX, touchY);
        }else if(ip.isActionUp()){
            drawCanvas.drawPath(drawPath, drawPaint);
            drawPath.reset();
        }

        invalidate();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(drawMode){

            ImagePoint touched = new ImagePoint(); //Fyrir serverinn
            float touchX = event.getX();
            float touchY = event.getY();
            touched.setX(touchX/drawCanvas.getWidth());
            touched.setY(touchY/drawCanvas.getHeight());
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touched.setActionDown(true);
                    drawPath.moveTo(touchX, touchY);

                    break;
                case MotionEvent.ACTION_MOVE:
                    touched.setActionMove(true);
                    drawPath.lineTo(touchX, touchY);
                    break;
                case MotionEvent.ACTION_UP:
                    touched.setActionUp(true);
                    drawCanvas.drawPath(drawPath, drawPaint);


                    drawPath.reset();
                    break;
                default:
                    return false;
            }
            invalidate();
            touched.setColor(paintColor);
            teikniActivity.broadcastImagePoint(touched);

        }

        return true;
    }

    public void undo() {
        //drawCanvas.restore();
        System.out.println("Virkar ekki hahahaha");
    }

    public void setColor(int newColor){
        drawPaint.setColor(newColor);
    }
    public void setColor(String newColor) {
        //set color
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }


}