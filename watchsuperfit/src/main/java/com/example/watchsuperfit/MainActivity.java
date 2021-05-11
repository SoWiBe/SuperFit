package com.example.watchsuperfit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    private TextView mTextView;
    private final int SPLASH_DISPLAY_LENGHT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
        setAmbientEnabled();
    }
}