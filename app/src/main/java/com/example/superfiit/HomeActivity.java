package com.example.superfiit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private TextView txtSeeAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtSeeAll = findViewById(R.id.txtSeeAll);
        txtSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                LayoutInflater inflater = getLayoutInflater();

                View view = inflater.inflate(R.layout.dialog_activity, null);
                Button buttonClose = view.findViewById(R.id.btnCancel);
                Button buttonYeas = view.findViewById(R.id.btnYes);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                buttonClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                buttonYeas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });
    }

    public void onClickSignOut(View view) {
        Intent ClickSignOut = new Intent(this, AuthorizationActivity.class);
        startActivity(ClickSignOut);
    }

    public void onClickToRecipes(View view) {
        Intent ClickSignOut = new Intent(this, RecipesActivity.class);
        startActivity(ClickSignOut);
    }

    public void onClickBackRecipes(View view) {
        Intent ClickSignOut = new Intent(this, RecipesActivity.class);
        startActivity(ClickSignOut);
    }
}