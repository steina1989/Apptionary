package is.hi.apptionary.gui;

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

    private boolean drawMode=true;
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

        float touchX = ip.getX();
        float touchY = ip.getY();
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

        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);

                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:

                drawCanvas.drawPath(drawPath, drawPaint);


                drawPath.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    public void undo() {
        //drawCanvas.restore();
        System.out.println("Virkar ekki hahahaha");
    }

    public void setColor(String newColor) {
        //set color
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }


}