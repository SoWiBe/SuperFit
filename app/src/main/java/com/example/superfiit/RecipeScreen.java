package com.example.superfiit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RecipeScreen extends AppCompatActivity {


    private TextView txtKcal, txtAllImpact;
    private Bundle bundle;
    private RecipesElement recipesElement;
    private ArrayList<String> ingredients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_screen);
        txtKcal = findViewById(R.id.txtKcal);
        txtAllImpact = findViewById(R.id.txtProteinFatCards);
        ingredients = new ArrayList<>();
        bundle = getIntent().getExtras();
        if(bundle != null){
            recipesElement = (RecipesElement) bundle.getSerializable("recipe");
            txtKcal.setText(recipesElement.getKcal());
            txtAllImpact.setText(recipesElement.getProtein() + " • " + recipesElement.getFat() + " • " + recipesElement.getCards());
            ingredients = recipesElement.getIngredients();
            for (int i = 0; i < ingredients.size(); i++) {
                Log.d("mLog", "onCreate: " + ingredients.get(i));
            }
        }
    }



    public void onClickBackRecipes(View view) {
        startActivity(new Intent(this, RecipesActivity.class));
    }
}