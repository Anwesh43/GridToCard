package com.anwesome.ui.gridtocard;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.ScrollView;

/**
 * Created by anweshmishra on 20/04/17.
 */
public class GridToCard {
    private Activity activity;
    private GridView gridView;
    private CardView cardView;
    private boolean isShown = false;
    private int w,n = 0;
    public GridToCard(Activity activity) {
        this.activity = activity;
        gridView = new GridView(activity,this);
        cardView = new CardView(activity,this);
        initDimension();
    }
    public void initDimension() {
        DisplayManager displayManager = (DisplayManager)activity.getSystemService(Context.DISPLAY_SERVICE);
        Display display = displayManager.getDisplay(0);
        if(display!=null) {
            Point size = new Point();
            display.getRealSize(size);
            w = size.x;
        }
    }
    public void addImage(Bitmap bitmap) {
        if(!isShown) {
            gridView.addImage(bitmap);
            n++;
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
            if(activity instanceof AppCompatActivity) {
                ActionBar actionBar = ((AppCompatActivity)(activity)).getSupportActionBar();
                if(actionBar!=null) {
                    actionBar.hide();
                }
            }
            else {
                android.app.ActionBar actionBar = activity.getActionBar();
                actionBar.hide();
            }
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            activity.setContentView(gridView);
            isShown = true;
        }
    }
}
