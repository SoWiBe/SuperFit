package com.example.watchsuperfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PlankActivity extends AppCompatActivity {

    private TextView txtStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plank);
        txtStart = findViewById(R.id.txtStart);
        txtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtStart.setClickable(false);
                txtStart.setTextSize(15);
                new CountDownTimer(5000, 950){
                    @Override
                    public void onTick(long millisUntilFinished) {
                        txtStart.setText("Готовьтесь..." + millisUntilFinished / 1000);
                    }

                    @Override
                    public void onFinish() {
                        txtStart.setEnabled(true);
                        Intent mainIntent = new Intent(PlankActivity.this, ChronometrActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                }.start();
            }
        });
    }

}