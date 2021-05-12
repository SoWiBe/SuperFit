package com.example.watchsuperfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ExcellentActivity extends AppCompatActivity {

    private TextView txtReboot;
    private ImageButton imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excellent);
        txtReboot = findViewById(R.id.txtReboot);
        txtReboot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExcellentActivity.this, PlankActivity.class));
            }
        });
        imgBack = findViewById(R.id.backToAuthorizationEx);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExcellentActivity.this, LoginActivity.class));
            }
        });
    }

}