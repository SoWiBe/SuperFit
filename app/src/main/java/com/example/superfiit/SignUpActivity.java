package com.example.superfiit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtName, edtEmail, edtRepeatCode, edtCode;
    private AwesomeValidation awesomeValidation;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName = findViewById(R.id.edtUserName);
        edtEmail = findViewById(R.id.edtEmail);
        edtCode = findViewById(R.id.edtCode);
        edtRepeatCode = findViewById(R.id.edtRepeatCode);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.edtUserName, RegexTemplate.NOT_EMPTY, R.string.invalid_name);

        awesomeValidation.addValidation(this, R.id.edtEmail, Patterns.EMAIL_ADDRESS, R.string.invalid_email);

        awesomeValidation.addValidation(this, R.id.edtCode, ".{4,}", R.string.invalid_password);

        awesomeValidation.addValidation(this, R.id.edtCode, "[1-9][1-9][1-9][1-9]", R.string.invalid_password);

        awesomeValidation.addValidation(this, R.id.edtRepeatCode, R.id.edtCode, R.string.invalid_confirm_password);

        dbHelper = new DBHelper(this);
    }

    public void onClickBackToSignIn(View view) {
        Intent BackToSignIn = new Intent(this, AuthorizationActivity.class);
        startActivity(BackToSignIn);
    }

    public void onClickSignUpToHome(View view) {
        if(awesomeValidation.validate()){
            String name = edtName.getText().toString();
            String email = edtEmail.getText().toString();
            String code = edtCode.getText().toString();
            Intent BackToSignIn = new Intent(this, HomeActivity.class);

            SQLiteDatabase database = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBHelper.KEY_NAME, name);
            contentValues.put(DBHelper.KEY_MAIL, email);
            contentValues.put(DBHelper.KEY_CODE, code);

            database.insert(DBHelper.TABLE_USERS, null, contentValues);

            Cursor cursor = database.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                int emailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);
                int codeIndex = cursor.getColumnIndex(DBHelper.KEY_CODE);
                do {
                    Log.d("mLog", "ID = " + cursor.getInt(idIndex) + ", name - " + cursor.getString(nameIndex) + ", email = " + cursor.getString(emailIndex) +
                            ", code = " + cursor.getString(codeIndex));
                } while (cursor.moveToNext());
            } else
                Log.d("mlog", "0 rows");
            cursor.close();
            dbHelper.close();


              startActivity(BackToSignIn);
        }
//        Intent BackToSignIn = new Intent(this, HomeActivity.class);
//        startActivity(BackToSignIn);
    }
}