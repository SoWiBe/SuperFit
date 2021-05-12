package com.example.watchsuperfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ChronometrActivity extends AppCompatActivity {

    private Chronometer chronometer;
    private TextView txtStop;
    private ImageButton imgBackToSingIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometr);
        chronometer = findViewById(R.id.chronometer);
        txtStop = findViewById(R.id.txtTapForStop);
        imgBackToSingIn = findViewById(R.id.backToAuthorization);

        chronometer.start();

        txtStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                startActivity(new Intent(ChronometrActivity.this, ExcellentActivity.class));
            }
        });
        imgBackToSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChronometrActivity.this, LoginActivity.class));
            }
        });
    }
}