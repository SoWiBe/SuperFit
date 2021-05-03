package com.example.superfiit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AuthorizationActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private EditText edtName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        dbHelper = new DBHelper(this);
        edtName = findViewById(R.id.edtUserNameAuthorization);


    }


    public void onClickSignIn(View view) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String name = edtName.getText().toString();
        Intent SignInIntent = new Intent(this, SignInActivity.class);
        Cursor cursor = database.rawQuery("Select * From " + DBHelper.TABLE_USERS + " Where " + DBHelper.KEY_NAME + "='" + name+"'", null);
        if(cursor.getCount() != 0){

            if (cursor.moveToFirst()) {
                int emailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);
                int codeIndex = cursor.getColumnIndex(DBHelper.KEY_CODE);
                do {
                    SignInIntent.putExtra("email", cursor.getString(emailIndex));
                    SignInIntent.putExtra("code", cursor.getString(codeIndex));
                    Log.d("mLog",  "Email = " + cursor.getString(emailIndex) +
                            ", code = " + cursor.getString(codeIndex));
                } while (cursor.moveToNext());
            } else
                Log.d("mlog", "0 rows");
            cursor.close();
            dbHelper.close();

            startActivity(SignInIntent);
        } else {
            Toast.makeText(this, "Please, authorization!", Toast.LENGTH_SHORT).show();
        }

    }

    public void onClickSignUp(View view) {
        Intent SignUpIntent = new Intent(this, SignUpActivity.class);
        startActivity(SignUpIntent);
    }
}