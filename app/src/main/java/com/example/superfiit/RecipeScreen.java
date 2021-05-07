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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RecipeScreen extends AppCompatActivity {


    private TextView txtKcal, txtAllImpact, txtTitle;
    private Bundle bundle;
    private RecipesElement recipesElement;
    private ArrayList<String> ingredientslist;
    private HttpURLConnection connection;
    private String data;
    private IngredientAdapter ingredientAdapter;
    private ListView lvIngredients;
    private ImageView imgBackContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_screen);
        txtKcal = findViewById(R.id.txtKcal);
        txtTitle = findViewById(R.id.txtTitle);

        lvIngredients = findViewById(R.id.mainListViewIngredient);
        txtAllImpact = findViewById(R.id.txtProteinFatCards);
        imgBackContent = findViewById(R.id.imgBackContent);

        ingredientslist = new ArrayList<>();
        bundle = getIntent().getExtras();

        if(bundle != null){
            recipesElement = (RecipesElement) bundle.getSerializable("recipe");
            txtKcal.setText(recipesElement.getKcal());
            txtAllImpact.setText(recipesElement.getProtein() + " • " + recipesElement.getFat() + " • " + recipesElement.getCards());
            txtTitle.setText(recipesElement.getName());
            Picasso.with(this).load(recipesElement.getImage()).into(imgBackContent);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        data = GetDownloadData();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Parsing(data);
                            ingredientAdapter = new IngredientAdapter(ingredientslist, RecipeScreen.this);
                            lvIngredients.setAdapter(ingredientAdapter);
                        }
                    });
                }
            });
        }

    }

    private String GetDownloadData(){
        StringBuilder result = new StringBuilder();
        try{
            connection =
                    (HttpURLConnection) new URL("https://api.edamam.com/search?q="+ recipesElement.getQuery() +
                            "&app_id=4da5a427&app_key=6dd6f99730da1737e964379d886e607d&diet=" + recipesElement.getDiet())
                            .openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if(HttpURLConnection.HTTP_OK == connection.getResponseCode()){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while((line = reader.readLine()) != null){
                    result.append(line);
                    result.append("\n");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        if(connection != null){
            connection.disconnect();
        }
        return result.toString();
    }
    private void Parsing(String jsonData){
        try{
            Object object = new JSONParser().parse(jsonData);
            org.json.simple.JSONObject jsonObject = (JSONObject) object;
            org.json.simple.JSONArray jsonArray = (JSONArray) jsonObject.get("hits");

            for(Object objectNew : jsonArray){
                org.json.simple.JSONObject obj = (org.json.simple.JSONObject) objectNew;
                org.json.simple.JSONObject recipeObject = (org.json.simple.JSONObject) obj.get("recipe");

                String label = recipeObject.get("label").toString();

                Log.d("LABEL", "LABEL: " + txtTitle.getText());
                if(label.equals(txtTitle.getText().toString())){
                    org.json.simple.JSONArray ingredientLines = (JSONArray) recipeObject.get("ingredientLines");
                    ingredientslist.clear();
                    for(Object ing : ingredientLines){
                        ingredientslist.add(ing.toString());
                    }
                    Log.d("Size", "Ingredient list size is: " + ingredientslist.size());
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }



    public void onClickBackRecipes(View view) {
        startActivity(new Intent(this, RecipesActivity.class));
    }
}