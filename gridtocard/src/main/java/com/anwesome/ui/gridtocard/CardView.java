package com.anwesome.ui.gridtocard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by anweshmishra on 20/04/17.
 */
public class CardView extends View {
    private Bitmap bitmap;
    private float currX,currY,finalX,finalY,scaleX,scaleY,deg=0,xSpeed = 0,ySpeed = 0,dir = 1,scaleYSpeed = 0,scaleXSpeed=0;
    private float initX,initY,initSx,initSy;
    private boolean isAnimated = false;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public CardView(Context context,Bitmap bitmap) {
        super(context);
        this.bitmap = bitmap;
    }
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#9900BCD4"));
        canvas.save();
        canvas.translate(currX,currY);
        canvas.rotate(deg);
        canvas.scale(scaleX,scaleY);
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.addCircle(0,0,bitmap.getWidth()/2, Path.Direction.CCW);
        paint.setStrokeWidth(bitmap.getWidth()/30);
        canvas.drawPath(path,paint);
        canvas.clipPath(path);
        canvas.drawBitmap(bitmap,-bitmap.getWidth()/2,-bitmap.getHeight()/2,paint);
        canvas.restore();
        if(isAnimated) {
            move();
            try {
                Thread.sleep(50);
                invalidate();
            }
            catch (Exception ex) {

            }
        }
    }
    public void move() {
        deg+=60*dir;
        currX += xSpeed;
        currY += ySpeed;
        scaleX += scaleXSpeed;
        if((xSpeed>0 && currX>finalX) || (xSpeed<0 && currX<finalX)) {
            currX = finalX;
            xSpeed = 0;
        }
        if((ySpeed>0 && currY>finalY) || (ySpeed<0 && currY<finalY)) {
            currY = finalY;
            ySpeed = 0;
        }
        if(xSpeed== 0 && ySpeed == 0) {
            dir = 0;
            isAnimated = false;
        }
    }
    public void init(Bitmap bitmap,float cx,float cy,int w,int h) {
        this.bitmap = Bitmap.createScaledBitmap(bitmap,w,h,true);
        finalX = w;
        finalY = h;
        initX = cx;
        initY = cy;
        currX = cx;
        currY = cy;
        xSpeed = (finalX-currX)/6;
        ySpeed = (finalY-currY)/6;
        initSx = bitmap.getWidth()/(w*1.0f);
        initSy = bitmap.getHeight()/(h*1.0f);
        scaleX = initSx;
        scaleY = initSy;
        scaleXSpeed = (1-scaleX)/6;
        scaleYSpeed = (1-scaleY)/6;
        dir = 1;
        isAnimated = true;
    }
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
