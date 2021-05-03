package com.example.superfiit;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import cz.msebera.android.httpclient.entity.mime.Header;
import cz.msebera.android.httpclient.protocol.HTTP;

public class RecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyRecyclerAdapter myRecyclerAdapter;
    private ArrayList<RecipesElement> recipesElements;
    private ImageView imgRecipeBackToHome;
    private SearchView searchView;
    private String productName = "chicken";
    private ArrayList<String> ingredientsList;


    private HttpURLConnection connection = null;
    private String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);


        recyclerView = findViewById(R.id.recyclerViewRecipes);
        imgRecipeBackToHome = findViewById(R.id.imgRecipeBackToHome);
        searchView = findViewById(R.id.edtSearch);


        recipesElements = new ArrayList<>();
        ingredientsList = new ArrayList<>();

        myRecyclerAdapter = new MyRecyclerAdapter(this, recipesElements);
        myRecyclerAdapter.notifyDataSetChanged();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

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
                        recyclerView.setAdapter(myRecyclerAdapter);
                    }
                });
            }
        });
        imgRecipeBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                myRecyclerAdapter.getFilter().filter(query);
                productName = query;
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
                                recyclerView.setAdapter(myRecyclerAdapter);
                            }
                        });
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }

    private String GetDownloadData(){
        StringBuilder result = new StringBuilder();
        try{
            connection =
                    (HttpURLConnection) new URL("https://api.edamam.com/search?q="+ productName + "&app_id=4da5a427&app_key=6dd6f99730da1737e964379d886e607d&diet=high-protein").openConnection();
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
                String image = recipeObject.get("image").toString();
                String calories = recipeObject.get("calories").toString();
                JSONObject totalNutrients = (JSONObject) recipeObject.get("totalNutrients");
                JSONObject proteisnObject = (JSONObject) totalNutrients.get("PROCNT");
                String proteinsValue = proteisnObject.get("quantity").toString();
                JSONObject fastObject = (JSONObject) totalNutrients.get("FAT");
                String fastValue = fastObject.get("quantity").toString();
                JSONObject cardsObject = (JSONObject) totalNutrients.get("CHOCDF");
                String cardsValue = cardsObject.get("quantity").toString();


                org.json.simple.JSONArray ingredientLines = (JSONArray) recipeObject.get("ingredientLines");
                for(Object ing : ingredientLines){
                    ingredientsList.add(ing.toString());
                }

                RecipesElement recipesElement = new RecipesElement();
                recipesElement.setName(label);
                recipesElement.setKcal(String.valueOf(Math.round(Double.parseDouble(calories))) + " kcal ");
                recipesElement.setProtein(String.valueOf(Math.round(Double.parseDouble(proteinsValue))) + "g protein");
                recipesElement.setCards(String.valueOf(Math.round(Double.parseDouble(cardsValue))) + "g carbs");
                recipesElement.setFat(String.valueOf(Math.round(Double.parseDouble(fastValue))) + "g fat");
                recipesElement.setIngredients(ingredientsList);
                Log.d("mLog", "onCreate: " +
                ingredientsList.size());
                recipesElement.setImage(image);



                recipesElements.add(recipesElement);
                ingredientsList.clear();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}