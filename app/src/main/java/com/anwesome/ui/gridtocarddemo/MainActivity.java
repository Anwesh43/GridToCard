package com.anwesome.ui.gridtocarddemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anwesome.ui.gridtocard.GridToCard;

public class MainActivity extends AppCompatActivity {
    int resources[] = {R.drawable.back1,R.drawable.back2,R.drawable.back3,R.drawable.back4};
    Bitmap bitmaps[] = new Bitmap[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(int i=0;i<resources.length;i++) {
            bitmaps[i] = BitmapFactory.decodeResource(getResources(),resources[i]);
        }

        GridToCard gridToCard = new GridToCard(this);
        for(int i=0;i<24;i++) {
            gridToCard.addImage(bitmaps[i%4]);
        }
        gridToCard.show();
    }

}
