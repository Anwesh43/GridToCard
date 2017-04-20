package com.anwesome.ui.gridtocard;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;

/**
 * Created by anweshmishra on 20/04/17.
 */
public class GridToCard {
    private Activity activity;
    private GridView gridView;
    private CardView cardView;
    private boolean isShown = false;
    public GridToCard(Activity activity) {
        this.activity = activity;
        gridView = new GridView(activity,this);
        cardView = new CardView(activity,this);
    }
    public void addImage(Bitmap bitmap) {
        if(!isShown) {
            gridView.addImage(bitmap);
        }
    }
    public void gridToCard(Bitmap bitmap,float x,float y,int w,int h) {
        activity.setContentView(cardView);
        cardView.init(bitmap,x,y,w,h);
    }
    public void cardToGrid() {
        activity.setContentView(gridView);
    }
    public void show() {
        if(!isShown) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            activity.setContentView(gridView);
            isShown = true;
        }
    }
}
