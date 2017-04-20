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
    private float currX,currY,finalX,finalY,scale,deg=0,xSpeed = 0,ySpeed = 0,dir = 1, scaleSpeed=0;
    private float initX,initY,initScale= 0;
    private boolean isAnimated = false;
    private BackArrow backArrow;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private GridToCard gridToCard;
    public CardView(Context context,GridToCard gridToCard) {
        super(context);
        this.gridToCard = gridToCard;
    }
    public void onDraw(Canvas canvas) {
        if(bitmap!=null) {
            canvas.drawColor(Color.parseColor("#e53935"));
            canvas.save();
            canvas.translate(currX, currY);
            canvas.rotate(deg);
            canvas.scale(scale, scale);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.WHITE);
            Path path = new Path();
            path.addCircle(0, 0, bitmap.getWidth() / 2, Path.Direction.CCW);
            paint.setStrokeWidth(bitmap.getWidth() / 30);
            canvas.drawPath(path, paint);
            canvas.clipPath(path);
            canvas.drawBitmap(bitmap, -bitmap.getWidth() / 2, -bitmap.getHeight() / 2, paint);
            canvas.restore();
            if(backArrow!=null) {
                backArrow.draw(canvas, paint);
            }
            if (isAnimated) {
                move();
                try {
                    Thread.sleep(50);
                    invalidate();
                } catch (Exception ex) {

                }
            }
        }
    }
    public void move() {
        deg+=60*dir;
        currX += xSpeed;
        currY += ySpeed;
        scale += scaleSpeed;
        if((xSpeed>0 && currX>finalX) || (xSpeed<0 && currX<finalX)) {
            currX = finalX;
            xSpeed = 0;
        }
        if((ySpeed>0 && currY>finalY) || (ySpeed<0 && currY<finalY)) {
            currY = finalY;
            ySpeed = 0;
        }
        if(xSpeed== 0 && ySpeed == 0) {
            float prevDir = dir;
            dir = 0;
            isAnimated = false;

            if(prevDir == -1) {
                deg = 0;
                gridToCard.cardToGrid();
            }
            else {
                deg = 360;
            }
        }
    }
    public void init(Bitmap bitmap,float cx,float cy,int w,int h) {
        this.bitmap = Bitmap.createScaledBitmap(bitmap,w,w,true);
        finalX = w;
        finalY = h;
        initX = cx;
        initY = cy;
        currX = cx;
        currY = cy;
        xSpeed = (finalX-currX)/6;
        ySpeed = (finalY-currY)/6;
        initScale = bitmap.getWidth()/(w*1.0f);
        scale = initScale;
        scaleSpeed = (1-scale)/6;
        dir = 1;
        isAnimated = true;
        backArrow = new BackArrow(w/4,w/8,w/8);
    }
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN && backArrow!=null) {
            backArrow.handleTap(event.getX(),event.getY());
        }
        return true;
    }
    private class BackArrow {
        private float x,y,w;
        public BackArrow(float x,float y,float w) {
            this.x = x;
            this.y = y;
            this.w = w;
        }
        public void draw(Canvas canvas,Paint paint) {
            paint.setStrokeWidth(w/8);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeJoin(Paint.Join.ROUND);
            canvas.drawLine(x,y,x-w,y,paint);
            for(int i=0;i<2;i++) {
                canvas.drawLine(x - w, y, x - w + w / 4, y + (i*2-1)*w / 4, paint);
            }
        }
        public void handleTap(float x,float y) {
            boolean condition = x>=this.x-w-w/4 && x<=this.x+w/4 && y>=this.y - w/2 && y<=this.y+w/2;
            if(condition) {
                gridToCard.cardToGrid();
            }
        }
    }
}
