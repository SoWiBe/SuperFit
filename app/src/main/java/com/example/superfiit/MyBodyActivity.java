package com.example.superfiit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mylibrary.DBHelper;

public class MyBodyActivity extends AppCompatActivity {

    private TextView txtEditWeight, txtEditHeight;
    private TextView txtHeight, txtWeight;
    private ImageView imgBackToHome;
    private String weight, height, id;
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_body);

        txtEditHeight = findViewById(R.id.txtEditHeight);
        txtEditWeight = findViewById(R.id.txtEditWeight);

        txtHeight = findViewById(R.id.txtHeight);
        txtWeight = findViewById(R.id.txtWeight);
        imgBackToHome = findViewById(R.id.imageBackToHome);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            id = bundle.getString("id");
            weight = bundle.getString("weight");
            height = bundle.getString("height");
            Log.d("id", "codeHeight: " + weight);
            Log.d("id", "codeWeight: " + height);
            Log.d("id", "id: " + id);
            txtWeight.setText(weight);
            txtHeight.setText(height);
        }
        imgBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyBodyActivity.this, HomeActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
        });
        txtEditWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyBodyActivity.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.dialog_change_weight, null);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                EditText edtNewValueWeight = view.findViewById(R.id.edtWeight);
                Button btnChange = view.findViewById(R.id.btnChange);

                btnChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        weight = edtNewValueWeight.getText().toString();
                        txtWeight.setText(weight);
                        dbHelper = new DBHelper(MyBodyActivity.this);
                        database = dbHelper.getWritableDatabase();
                        database.execSQL("Update " + DBHelper.TABLE_USERS + " Set " + DBHelper.KEY_WEIGHT + "=" + weight + " Where " + DBHelper.KEY_ID + "=" + id + "");

                        Cursor cursor = database.rawQuery("Select * From " + DBHelper.TABLE_USERS + " Where " + DBHelper.KEY_ID + "='" + id + "'", null);
                        if (cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                int codeWeight = cursor.getColumnIndex(DBHelper.KEY_WEIGHT);
                                int codeHeight = cursor.getColumnIndex(DBHelper.KEY_HEIGHT);
                                int codeId = cursor.getColumnIndex(DBHelper.KEY_ID);
                                do {
                                    txtWeight.setText(cursor.getString(codeWeight));
                                    Log.d("mLog", "Height = " + cursor.getString(codeHeight) +
                                            ", Weight = " + cursor.getString(codeWeight) + ", id = " + cursor.getString(codeId));
                                } while (cursor.moveToNext());
                            } else
                                Log.d("mlog", "0 rows");
                            cursor.close();
                            dbHelper.close();
                            alertDialog.cancel();
                        }
                    }
                });
                Button btnCancel = view.findViewById(R.id.btnCancel);

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });
        txtEditHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyBodyActivity.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.dialog_change_weight, null);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                EditText edtNewValueHeight= view.findViewById(R.id.edtWeight);
                TextView stTxtTitle = view.findViewById(R.id.staticTxtTitle);
                TextView stTxtWeight = view.findViewById(R.id.staticTxtWeight);
                stTxtTitle.setText("Change your height");
                stTxtWeight.setText("Height");
                Button btnChange = view.findViewById(R.id.btnChange);
                btnChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        height = edtNewValueHeight.getText().toString();
                        txtHeight.setText(height);

                        dbHelper = new DBHelper(MyBodyActivity.this);
                        database = dbHelper.getWritableDatabase();
                        database.execSQL("Update " + DBHelper.TABLE_USERS + " Set " + DBHelper.KEY_HEIGHT + "=" + height + " Where " + DBHelper.KEY_ID + "=" + id + "");

                        Cursor cursor = database.rawQuery("Select * From " + DBHelper.TABLE_USERS + " Where " + DBHelper.KEY_ID + "='" + id + "'", null);
                        if (cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                int codeWeight = cursor.getColumnIndex(DBHelper.KEY_WEIGHT);
                                int codeHeight = cursor.getColumnIndex(DBHelper.KEY_HEIGHT);
                                int codeId = cursor.getColumnIndex(DBHelper.KEY_ID);
                                do {
                                    txtWeight.setText(cursor.getString(codeWeight));
                                    Log.d("mLog", "Height = " + cursor.getString(codeHeight) +
                                            ", Weight = " + cursor.getString(codeWeight) + ", id = " + cursor.getString(codeId));
                                } while (cursor.moveToNext());
                            } else
                                Log.d("mlog", "0 rows");
                            cursor.close();
                            dbHelper.close();
                            alertDialog.cancel();
                        }
                    }
                });
                Button btnCancel = view.findViewById(R.id.btnCancel);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });


    }
}