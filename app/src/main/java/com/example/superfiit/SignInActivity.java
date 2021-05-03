package com.example.superfiit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class SignInActivity extends AppCompatActivity {

    private TextView txtEmail;
    private GridView gridNumbers;
    private  String code;
    private ArrayList<String> numbers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        txtEmail = findViewById(R.id.txtEmail);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String email = bundle.getString("email");
            code = bundle.getString("code");
            txtEmail.setText(email);
        }
        numbers.add("1");
        numbers.add("2");
        numbers.add("3");
        numbers.add("4");
        numbers.add("5");
        numbers.add("6");
        numbers.add("7");
        numbers.add("8");
        numbers.add("9");
        gridNumbers = findViewById(R.id.gridNumbers);
//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, numbers);
        CustomAdapter customAdapter = new CustomAdapter(numbers, this, code);
        gridNumbers.setAdapter(customAdapter);
    }
}