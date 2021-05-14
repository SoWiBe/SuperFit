package com.example.superfiit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylibrary.DBHelper;

public class HomeActivity extends AppCompatActivity {

    private TextView txtSeeAll;
    private LinearLayout llDetails;
    private TextView txtHeightHome, txtWeightHome;
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private String id, weight, height;
    private TextView txtKg, txtCm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle bundle = getIntent().getExtras();

        txtSeeAll = findViewById(R.id.txtSeeAll);
        llDetails = findViewById(R.id.llDetailsClick);
        txtHeightHome = findViewById(R.id.txtHeightHome);
        txtWeightHome = findViewById(R.id.txtWeightHome);

        txtKg = findViewById(R.id.staticTxtKg);
        txtCm = findViewById(R.id.staticTxtCm);
        if(bundle!=null){
            dbHelper = new DBHelper(HomeActivity.this);
            database = dbHelper.getWritableDatabase();
            id = bundle.getString("id");


            Cursor cursor = database.rawQuery("Select * From " + DBHelper.TABLE_USERS + " Where " + DBHelper.KEY_ID + "='" + id +"'", null);
            Log.d("id", "count: " + cursor.getCount());
            if(cursor.getCount()!=0){
                if(cursor.moveToFirst()){
                    int codeWeight = cursor.getColumnIndex(DBHelper.KEY_WEIGHT);
                    int codeHeight = cursor.getColumnIndex(DBHelper.KEY_HEIGHT);
                    do{
                        weight = cursor.getString(codeWeight);
                        height = cursor.getString(codeHeight);
                        txtWeightHome.setText(weight);
                        txtHeightHome.setText(height);
                        if(weight == null){
                            weight ="Undefined";
                            txtKg.setVisibility(View.INVISIBLE);
                            txtWeightHome.setText(weight);
                        }
                        if(height == null){
                            height ="Undefined";
                            txtCm.setVisibility(View.INVISIBLE);
                            txtHeightHome.setText(height);
                        }
                    } while (cursor.moveToNext());
                }
            }
            else{
                Toast.makeText(this, "Hi", Toast.LENGTH_SHORT).show();
            }
            cursor.close();

            if(txtWeightHome == null){
                txtWeightHome.setText("Undefined");
            }
        }
        txtSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomeActivity.this, SeeAllActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        llDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MyBodyActivity.class);
                Log.d("id", "codeHeight: " + weight);
                Log.d("id", "codeWeight: " + height);
                intent.putExtra("id", id);
                intent.putExtra("weight", weight);
                intent.putExtra("height", height);
                startActivity(intent);
            }
        });
    }



    public void onClickSignOut(View view) {
        Intent ClickSignOut = new Intent(this, AuthorizationActivity.class);
        startActivity(ClickSignOut);
    }

    public void onClickToRecipes(View view) {
        Intent goToRecipes = new Intent(this, RecipesActivity.class);
        goToRecipes.putExtra("id", id);
        startActivity(goToRecipes);
    }

    public void onClickBackRecipes(View view) {
        Intent ClickSignOut = new Intent(this, RecipesActivity.class);
        startActivity(ClickSignOut);
    }
}