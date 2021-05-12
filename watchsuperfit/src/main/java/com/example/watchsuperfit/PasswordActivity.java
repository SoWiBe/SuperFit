package com.example.watchsuperfit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class PasswordActivity extends AppCompatActivity {

    private GridView gridNumbers;
    private CustomAdapter customAdapter;
    private ArrayList<String> numbers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        gridNumbers = findViewById(R.id.gridNumbers);
        numbers.add("1");
        numbers.add("2");
        numbers.add("3");
        numbers.add("4");
        numbers.add("5");
        numbers.add("6");
        numbers.add("7");
        numbers.add("8");
        numbers.add("9");
        customAdapter = new CustomAdapter(numbers, this);
        gridNumbers.setAdapter(customAdapter);
    }
}