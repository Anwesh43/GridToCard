package com.anwesome.ui.gridtocard;

import android.content.Context;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anweshmishra on 20/04/17.
 */
public class GridView extends View {
    private int time = 0;
    private GridToCard gridToCard;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private List<Bitmap> bitmaps = new ArrayList<>();
    private int color = Color.parseColor("#FF7043");
    private int w = 100,h = 100;
    private List<GridBitmap> gridBitmaps = new ArrayList<>();
    public GridView(Context context,GridToCard gridToCard) {
        super(context);
        this.gridToCard = gridToCard;
    }
    public void addImage(Bitmap bitmap) {
        bitmaps.add(bitmap);
    }
    public void onDraw(Canvas canvas) {
        w = canvas.getWidth();
        h = canvas.getHeight();
        canvas.drawColor(color);
        if(time == 0) {
            int gap = w/7,x = 3*gap/2,y = 3*gap/2 ,i = 0;
            for(Bitmap bitmap:bitmaps) {
                GridBitmap gridBitmap = new GridBitmap(Bitmap.createScaledBitmap(bitmap,w/7,w/7,true),x,y);
                gridBitmaps.add(gridBitmap);
                i++;
                x+=2*gap;
                if(i == 3) {
                    x = 3*gap/2;
                    y += 2*gap;
                    i = 0;
                }
            }
        }
        canvas.drawColor(Color.parseColor("#FF7043"));
        for(GridBitmap gridBitmap:gridBitmaps) {
            gridBitmap.draw(canvas);
        }
        time++;
    }
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX(),y = event.getY();
        GridBitmap tappedBitmap = null;
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            for (GridBitmap gridBitmap : gridBitmaps) {
                if (gridBitmap.handleTap(x, y)) {
                    tappedBitmap = gridBitmap;
                    break;
                }
            }
            if(tappedBitmap!=null) {
                gridToCard.gridToCard(tappedBitmap.getBitmap(),tappedBitmap.center.x,tappedBitmap.center.y,w/2,h/2);
            }
        }
        return true;
    }
    private class GridBitmap {
        private Bitmap bitmap;
        private int w,h;
        private Point center = new Point();
        public GridBitmap(Bitmap bitmap,int x,int y) {
            this.bitmap = bitmap;
            this.center = new Point(x,y);
            w = bitmap.getWidth();
            h = bitmap.getHeight();
        }
        public Bitmap getBitmap() {
            return bitmap;
        }
        public void draw(Canvas canvas) {
            canvas.save();
            canvas.translate(center.x,center.y);
            Path path = new Path();
            path.addCircle(0,0,w/2, Path.Direction.CCW);
            paint.setStrokeWidth(w/30);
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(path,paint);
            canvas.clipPath(path);
            canvas.drawBitmap(bitmap,-w/2,-h/2,paint);
            canvas.restore();
        }
        public boolean handleTap(float x,float y) {
            return x>=this.center.x - w/2 && x<=this.center.x+w/2 && y>=this.center.y-h/2 && y<=this.center.y+h/2;
        }
        public int hashCode() {
            return center.hashCode()+bitmap.hashCode();
        }
    }
}
