package com.anwesome.ui.gridtocard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anweshmishra on 20/04/17.
 */
public class GridView extends View {
    private int time = 0;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private List<Bitmap> bitmaps = new ArrayList<>();
    private List<GridBitmap> gridBitmaps = new ArrayList<>();
    public GridView(Context context) {
        super(context);
    }
    public void addImage(Bitmap bitmap) {
        bitmaps.add(bitmap);
    }
    public void onDraw(Canvas canvas) {
        int w = canvas.getWidth(),h = canvas.getHeight();
        if(time == 0) {
            int gap = w/7,x = 2*gap,y = 2*gap;
            for(Bitmap bitmap:bitmaps) {
                bitmap = Bitmap.createScaledBitmap(bitmap,w/7,w/7,true);
            }
        }
        time++;
    }
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
    private class GridBitmap {
        private Bitmap bitmap;
        private Point center = new Point();
        public GridBitmap(Bitmap bitmap,int x,int y) {
            this.bitmap = bitmap;
            this.center = new Point(x,y);
        }
        public void draw(Canvas canvas) {
            int w = bitmap.getWidth(),h = bitmap.getHeight();
            canvas.save();
            canvas.translate(center.x,center.y);
            canvas.restore();
        }
    }
}
